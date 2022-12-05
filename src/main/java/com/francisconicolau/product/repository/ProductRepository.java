package com.francisconicolau.product.repository;

import com.francisconicolau.product.model.Products;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("productRepository")
public interface ProductRepository extends PagingAndSortingRepository<Products, Integer> {

}
