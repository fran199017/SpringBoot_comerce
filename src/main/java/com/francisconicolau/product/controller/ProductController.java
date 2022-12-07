package com.francisconicolau.product.controller;


import com.francisconicolau.prices.services.PricesService;
import com.francisconicolau.product.model.Products;
import com.francisconicolau.product.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RequestMapping("/public")
@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;
    private PricesService pricesService;

    @Autowired
    public ProductController(ProductService productService, PricesService pricesService) {
        this.productService = productService;
        this.pricesService = pricesService;
    }

    @GetMapping(value = "/index")
    public ModelAndView index() {
        try {
            ModelAndView model = new ModelAndView();
            model.setViewName("index");
            return model;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    @GetMapping(value = "/products")
    public ModelAndView products() {
        try {
            Iterable<Products> products = productService.getProducts();
            ModelAndView model = new ModelAndView();
            model.setViewName("products");
            model.addObject("products", products);
            return model;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping(value = "/product")
    public ModelAndView productsById(int id) {
        try {
            Products product = productService.getProductById(id);
            if (product != null){
                ModelAndView model = new ModelAndView();
                model.setViewName("product");
                model.addObject("product", product);
                return model;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    @DeleteMapping(value = "/product")
    public ModelAndView deleteProduct(int id) {
        try {
            productService.deleteProduct(id);
            ModelAndView model = new ModelAndView();
            model.setViewName("index");
            return model;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping(value = "/modifyproduct")
    public ModelAndView goUpdate(int id) {
        try {
            ModelAndView model = new ModelAndView();
            model.setViewName("modifyproduct");
            Products product = productService.getProductById(id);
            List<Float> prices = pricesService.getListOfPricesWithooutPriority(product.getId());
            if (product != null){
                model.addObject("product", product);
                if (!prices.isEmpty()){
                    model.addObject("prices", prices);
                }
                return model;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/process_update")
    public ModelAndView processUpdate(Products product) {
        try {
            log.info("Product {}", product);
            productService.updateProduct(product);
            ModelAndView model = new ModelAndView("redirect:index");
            return model;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping(value = "/process_create")
    public ModelAndView processCreateProduct() {
        try {
            ModelAndView model = new ModelAndView("process_create");
            model.addObject("product", new Products());
            return model;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/create_product")
    public ModelAndView createProduct(Products product) {
        try {
            log.info("Product {}", product);
            productService.createProduct(product);
            ModelAndView model = new ModelAndView("redirect:index");
            return model;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
