package com.francisconicolau.product.controller;

import com.francisconicolau.prices.model.PriceSpecification;
import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.model.PricesSearch;
import com.francisconicolau.product.model.Products;
import com.francisconicolau.product.model.dto.ProductDTO;
import com.francisconicolau.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


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

    @ApiOperation(value = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server Exception"),
            @ApiResponse(code = 200, message = "OK", response = Products.class)
    })
    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Object> productsById(@PathVariable @ApiParam(value = "The product id", required = true) int id) {
        try {
            Products product = productService.getProductById(id);
            if (product != null){
                return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server Exception"),
            @ApiResponse(code = 200, message = "OK", response = Products.class)
    })
    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDTO productDTO,
                                                @PathVariable @ApiParam(value = "The product id", required = true) int id) {
        try {
            Products updated = productService.updateProduct(id , productDTO);
            if (updated != null){
                return new ResponseEntity<>(updated, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server Exception"),
            @ApiResponse(code = 200, message = "OK", response = Products.class)
    })
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable @ApiParam(value = "The product id", required = true) int id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
