package com.francisconicolau.product.repository;

import com.francisconicolau.product.model.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("productRepository")
public interface ProductRepository extends PagingAndSortingRepository<Products, Integer> {

}
