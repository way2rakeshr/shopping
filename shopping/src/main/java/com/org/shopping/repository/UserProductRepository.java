package com.org.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.shopping.entity.UserProduct;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Long> { 

}
