package com.francisconicolau.prices.controller;

import com.francisconicolau.prices.model.PriceSpecification;
import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.model.PricesSearch;
import com.francisconicolau.prices.model.dto.CreatePriceDTO;
import com.francisconicolau.prices.model.dto.PricesDTO;
import com.francisconicolau.prices.services.PricesService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class PricesController {

    private static final String NOT_FOUND_PRICES = "Not found prices";
    private static final String NOT_CREATED_PRICE = "Not created price";

    private static final Logger log = LoggerFactory.getLogger(PricesController.class);

    PricesService pricesService;

    @Autowired
    public PricesController(PricesService pricesService) {
        this.pricesService = pricesService;
    }

    @PostMapping(value = "prices/create")
    @ApiOperation(value = "Create price",
            notes = "brandId: numeric 1 by default ZARA \n " +
                    "date: String pattern Example  - >  2020-06-14 00:00:00 \n " +
                    "productId: numeric \n ")

    public ResponseEntity<Object> createPrice(@RequestBody(required = true) CreatePriceDTO createPriceDTO) {
        try{
            Prices prices = pricesService.createNewPrice(createPriceDTO);
            if (prices != null){
                return new ResponseEntity<>(prices, HttpStatus.OK);
            }
            return new ResponseEntity<>(NOT_CREATED_PRICE, HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "prices")
    @ApiOperation(value = "Get prices by PricesDTO filter",
            notes = "brandId: numeric 1 by default ZARA \n " +
            "date: String pattern Example  - >  2020-06-14 00:00:00 \n " +
            "productId: numeric \n ")

    public ResponseEntity<Object> getPricesByParameters(@RequestBody(required = true) PricesDTO pricesDTO) {
        try{
            List<Prices> prices = pricesService.getPricesByParameters(pricesDTO);
            if (!prices.isEmpty()){
                return new ResponseEntity<>(prices, HttpStatus.OK);
            }
            return new ResponseEntity<>(NOT_FOUND_PRICES, HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "prices/{id}")
    @ApiOperation(value = "Get price by ID")
    public ResponseEntity<Object> getPricesById(@PathVariable @ApiParam(value = "The id", required = true) int id) {
        try{
            Prices prices = pricesService.getById(id);
            if (prices != null){
                return new ResponseEntity<>(prices, HttpStatus.OK);
            }
            return new ResponseEntity<>(NOT_FOUND_PRICES, HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Optional endpoint -> Getting prices with spec between two dates")
    @GetMapping(value="/prices/filter")
    @ApiResponses( value = {
            @ApiResponse(code = 500, message = "Server Exception"),
            @ApiResponse(code = 200, message="OK", response=Prices.class, responseContainer="List")
    })
    public ResponseEntity<Object> getPricesBetweenDates(@ApiParam(value = "Query param for 'Date Start' filter") @Valid @RequestParam(value = "startDate", required = false) String startDate,
                                                        @ApiParam(value = "Query param for 'Date End' filter") @Valid @RequestParam(value = "endDate", required = false) String endDate) {
        try {
                PricesSearch pricesSearch = new PricesSearch();
                log.info("Las fechas son start:{},end:{}",startDate,endDate);
                pricesSearch.setDateStart(startDate);
                pricesSearch.setDateEnd(endDate);
                PriceSpecification spec = new PriceSpecification(pricesSearch, true);
                List<Prices> prices = pricesService.findAll(spec);
                return new ResponseEntity<>(prices,HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
