# Coupon Management System

## Overview

The Coupon Management System is designed to manage and apply various types of discount coupons to shopping carts in an e-commerce platform. It provides functionalities to create, apply, and validate different kinds of coupons including cart-wide discounts, product-specific discounts, and Buy X Get Y (BxGy) offers. The system ensures that only valid and applicable coupons are used by validating conditions such as expiration dates, minimum cart values, and applicable products.

## Features

### Implemented Features
1. **Coupon Types**
   - **Cart-Wise Coupons**: Discounts applied to the total cart value.
   - **Product-Wise Coupons**: Discounts applied to specific products in the cart.
   - **Buy X Get Y Coupons (BxGy)**: Offers where customers receive free or discounted products when buying a specific quantity.

2. **Constraints/Validations**
   - **Expiration Dates**: Coupons have expiration dates that are checked before applying the discount.
   - **Minimum Cart Value**: Some coupons require a minimum cart value for the discount to be applied.
   - **Applicable Products**: Product-specific discounts are only applied to products listed in the coupon conditions.

3. **Coupon Application Process**
   - **Cart-Wise Discount Calculation**: Applies discounts to the entire cart based on a percentage or fixed amount.
   - **Product-Wise Discount Calculation**: Applies discounts only to specific eligible products in the cart.
   - **BxGy Coupon Implementation**: Provides "Buy X, Get Y" offers, where qualifying products lead to free or discounted additional products.

4. **REST API Endpoints**
   - **Create Coupon**: Adds a new coupon to the system.
   - **Apply Coupon**: Applies a coupon to a cart and returns the updated cart with applied discounts.
   - **Get All Coupons**: Fetches all available coupons.
   - **Get Coupon by ID**: Fetches a specific coupon by its ID.
   - **Update Coupon**: Updates an existing coupon's details.
   - **Delete Coupon**: Deletes a coupon from the system.

### Unimplemented Use Cases (Future Enhancements)
1. **Coupon Combinations**: Allow multiple coupons to be applied together.
2. **Loyalty/First-Time Buyer Coupons**: Coupons targeted to specific customer types (loyal customers, first-time buyers).
3. **Payment Method-Based Coupons**: Coupons that are only applicable if a specific payment method is used.
4. **Seasonal/Event-Based Coupons**: Coupons available only during special events like Black Friday or Christmas.
5. **Product Category-Based Coupons**: Coupons that apply to specific product categories.
6. **Multi-Tier Discounts**: Coupons with different discounts based on the total cart value or the number of products purchased.
7. **Redemption Limits**: Coupons that have a maximum number of allowed redemptions.


**# Coupon Management API**

## 1. Get All Coupons
- **Endpoint:** `GET /coupons`
- **Description:** Retrieve all available coupons.

## 2. Get Coupon by ID
- **Endpoint:** `GET /coupons/{id}`
- **Description:** Retrieve a specific coupon by its ID.

## 3. Update Coupon
- **Endpoint:** `PUT /coupons/{id}`
- **Description:** Update the details of a specific coupon.

## 4. Save Coupon
- **Endpoint:** `POST /coupons/`
- **Description:** Save the details of a coupon.

## 5. Delete Coupon
- **Endpoint:** `DELETE /coupons/{id}`
- **Description:** Delete the record of a coupon.

## 6. Applicable Coupons
- **Endpoint:** `POST /coupons/applicable-coupons`
- **Description:**Fetch all the applicable coupons.

## 7. Apply Coupon
- **Endpoint:** `POST /coupons/apply-coupon/{id}`
- **Description:** Apply coupon to the cart and check the result .

## Future Enhancements

- **Coupon Combinations:** Support for applying multiple coupons at once (stacking or selecting the best coupon based on conditions).
- **Loyalty Coupons:** Allow coupons that apply only to loyalty members or customers with a specific purchase history.
- **First-Time Buyer Discounts:** Provide first-time buyer discounts to encourage new customers.
- **Payment Method-Based Coupons:** Allow coupons that apply only when certain payment methods are used (e.g., credit card, PayPal).
- **Holiday/Event-Specific Coupons:** Introduce seasonal or event-specific coupons (e.g., Black Friday, Christmas) that are available for a limited time.
- **Category-Based Coupons:** Allow coupons to apply only to specific product categories like electronics, clothing, or groceries.
- **Multi-Tiered Discounts:** Provide discounts that increase based on the total cart value (e.g., 5% off for $100, 10% off for $200).
- **Redemption Limits:** Implement coupons with usage limits (e.g., 100 redemptions per coupon or 1 per customer).
- **User Profiles Integration:** Integrate user profiles to offer personalized coupons based on shopping behavior or location.
- **Analytics & Reporting:** Provide coupon performance reports, including redemption rates, customer engagement, and overall effectiveness of promotions.
- **Notification System:** Implement notifications to inform customers when their coupons are close to expiration.
- **Internationalization:** Support multi-currency and region-based coupons to handle global customer bases.

### Sample Request Body (Coupons):
```json
{
    "_id": {
        "$oid": "66f91c3ab3be6724123d1e0b"
    },
    "type": "cart-wise",
    "description": "10% off on total cart value for orders above $100",
    "expirationDate": {
        "$date": "2024-12-31T23:59:59.999Z"
    },
    "discount": {
        "percentage": 10.0
    },
    "conditions": {
        "cartTotalMinimum": 100.0,
        "applicableProducts": [],
        "buyProducts": ["product-1", "product-2"],
        "getProducts": ["free-product-1"],
        "buyQuantity": 2,
        "getQuantity": 1,
        "repetitionLimit": 5
    },
    "_class": "com.example.coupon_management.model.Coupon"
}
