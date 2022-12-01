package com.francisconicolau.product.controller;

import com.francisconicolau.product.model.Products;
import com.francisconicolau.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/public")
@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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


}
