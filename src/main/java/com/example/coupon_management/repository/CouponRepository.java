package com.example.coupon_management.repository;

import com.example.coupon_management.model.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {
}
