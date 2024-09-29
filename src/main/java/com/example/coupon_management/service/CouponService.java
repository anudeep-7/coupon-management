package com.example.coupon_management.service;

import com.example.coupon_management.model.Cart;
import com.example.coupon_management.model.CartItem;
import com.example.coupon_management.model.Conditions;
import com.example.coupon_management.model.Coupon;
import com.example.coupon_management.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(String id) {
        return couponRepository.findById(id);
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(String id, Coupon updatedCoupon) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setType(updatedCoupon.getType());
            coupon.setDescription(updatedCoupon.getDescription());
            coupon.setExpirationDate(updatedCoupon.getExpirationDate());
            coupon.setDiscount(updatedCoupon.getDiscount());
            coupon.setConditions(updatedCoupon.getConditions());
            return couponRepository.save(coupon);
        } else {
            throw new RuntimeException("Coupon not found");
        }
    }

    public void deleteCoupon(String id) {
        couponRepository.deleteById(id);
    }

    public Cart applyCoupon(String couponId, Cart cart) {
        // Fetch the coupon based on the couponId
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        // Check if the coupon is expired
        if (coupon.getExpirationDate() != null && coupon.getExpirationDate().before(new Date())) {
            throw new RuntimeException("Coupon has expired");
        }

        // Calculate discount based on the coupon type
        return switch (coupon.getType()) {
            case "cart-wise" -> applyCartWiseCoupon(coupon, cart);
            case "product-wise" -> applyProductWiseCoupon(coupon, cart);
            case "bxgy" -> applyBxGyCoupon(coupon, cart);
            default -> throw new RuntimeException("Unknown coupon type");
        };
    }

    private Cart applyCartWiseCoupon(Coupon coupon, Cart cart) {
        // Check the minimum cart value condition
        if (coupon.getConditions().getCartTotalMinimum() != null &&
                cart.getTotal() < coupon.getConditions().getCartTotalMinimum()) {
            throw new RuntimeException("Cart value does not meet the minimum requirement for this coupon.");
        }

        // Calculate discount (assuming percentage for simplicity)
        double discountAmount = (cart.getTotal() * (coupon.getDiscount().getPercentage() / 100));
        double updatedTotal = cart.getTotal() - discountAmount;

        // Update cart
        cart.setTotal(updatedTotal);
        return cart;
    }

    private Cart applyProductWiseCoupon(Coupon coupon, Cart cart) {
        boolean productApplicable = false;

        // Check if the coupon has applicable products defined
        List<String> applicableProducts = coupon.getConditions().getApplicableProducts();
        for (CartItem item : cart.getItems()) {
            // Check if the item's productId is in the list of applicable products
            if (applicableProducts != null && applicableProducts.contains(item.getProductId())) {
                productApplicable = true;
                double discountAmount = item.getPrice() * (coupon.getDiscount().getPercentage() / 100);
                item.setPrice(item.getPrice() - discountAmount); // Update the price of the item
            }
        }

        if (!productApplicable) {
            throw new RuntimeException("No applicable products found in the cart for this coupon.");
        }

        // Recalculate the total
        double newTotal = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        cart.setTotal(newTotal);
        return cart;
    }

    private Cart applyBxGyCoupon(Coupon coupon, Cart cart) {
        Conditions conditions = coupon.getConditions();
        List<String> buyProducts = conditions.getBuyProducts();
        List<String> getProducts = conditions.getGetProducts();
        int buyQuantity = conditions.getBuyQuantity();
        int getQuantity = conditions.getGetQuantity();

        // Check if buyProducts and getProducts are specified in the coupon
        if (buyProducts == null || getProducts == null || buyQuantity <= 0 || getQuantity <= 0) {
            throw new RuntimeException("Invalid BxGy coupon conditions.");
        }

        // Track the total number of eligible items bought
        int totalBought = 0;

        // Track the items to discount for the get part
        List<CartItem> eligibleGetItems = new ArrayList<>();

        // Check the cart for the buy products
        for (CartItem item : cart.getItems()) {
            if (buyProducts.contains(item.getProductId())) {
                totalBought += item.getQuantity();
            }

            // Check if the product is eligible for the "get" part
            if (getProducts.contains(item.getProductId())) {
                eligibleGetItems.add(item);
            }
        }

        // Ensure enough items were bought to apply the promotion
        if (totalBought >= buyQuantity) {
            // Calculate the number of free items based on the number of sets of the offer that are applicable
            int applicableSets = totalBought / buyQuantity;
            int freeItemsToApply = applicableSets * getQuantity;

            // Apply the discount to the "get" products
            for (CartItem getItem : eligibleGetItems) {
                if (freeItemsToApply <= 0) {
                    break;
                }

                // Reduce the price of the item to 0 for the applicable quantity
                int freeItemsForThisProduct = Math.min(freeItemsToApply, getItem.getQuantity());

                // Set the price of the "get" products to 0 for the applicable quantity
                double originalPrice = getItem.getPrice();
                double discountAmount = originalPrice * (freeItemsForThisProduct / (double) getItem.getQuantity());
                getItem.setPrice(getItem.getPrice() - discountAmount);

                // Decrease the number of free items left to apply
                freeItemsToApply -= freeItemsForThisProduct;
            }
        } else {
            throw new RuntimeException("Insufficient quantity of buy products to apply the BxGy coupon.");
        }

        // Recalculate the total
        double newTotal = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        cart.setTotal(newTotal);
        return cart;
    }

}
