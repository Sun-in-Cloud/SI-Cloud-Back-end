package com.shinhan.sunInCloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
