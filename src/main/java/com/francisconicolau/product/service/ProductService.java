package com.francisconicolau.product.service;

import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.repository.PricesRepository;
import com.francisconicolau.product.model.Products;
import com.francisconicolau.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("productService")
public class ProductService {

    private PricesRepository pricesRepository;

    private ProductRepository productRepository;

    @Autowired
    public ProductService(PricesRepository pricesRepository, ProductRepository productRepository) {
        this.pricesRepository = pricesRepository;
        this.productRepository = productRepository;
    }


    public Iterable<Products> getProducts() {
        Iterable<Products> products = productRepository.findAll();
        for (Products product : products){
            int id = product.getId();
            float price = updatePricesForProducts(id);
            if (price > 0){
                product.setPrice(price);
            }
            productRepository.save(product);
        }
        return products;

    }

    public float updatePricesForProducts(int productId){
        Optional<Prices> priceOpt = pricesRepository.findByProductIdAndMaxPriority(productId);
        if (priceOpt.isPresent()){
            Prices prices = priceOpt.get();
            if (prices != null){
                return  prices.getPrice();
            }
        }
        return 0;
    }

    public Products getProductById(int id) {
        Optional<Products> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        return null;
    }

    public void deleteProduct(int id) {
        Products product = getProductById(id);
        if (product != null){
            productRepository.delete(product);
        }

    }

    public void updateProduct(Products products) {
        productRepository.save(products);
    }

    public void createProduct(Products product) {
        productRepository.save(product);
    }
}
