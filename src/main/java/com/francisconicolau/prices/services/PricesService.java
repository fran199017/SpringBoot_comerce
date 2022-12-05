package com.francisconicolau.prices.services;

import com.francisconicolau.prices.model.PriceSpecification;
import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.model.dto.CreatePriceDTO;
import com.francisconicolau.prices.model.dto.PricesDTO;
import com.francisconicolau.prices.repository.PricesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("pricesService")
public class PricesService {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private PricesRepository pricesRepository;

    private static final Logger log = LoggerFactory.getLogger(PricesService.class);

    @Autowired
    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    public List<Prices> getPricesByParameters(PricesDTO pricesDTO) {
        String date = pricesDTO.getDate();
        int productId = pricesDTO.getProductId();
        int brandId = pricesDTO.getBrandId();

        LocalDateTime dateLocal = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateLocal, productId, brandId);

        if (pricesOpt.isEmpty()){
            return Collections.emptyList();
        }
        return pricesOpt.get();
    }

    public LocalDateTime getDateformatted(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            return format.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            log.error("Error {}", e.getMessage());
        }
        return null;
    }

    public Prices getById(int id) {
        Optional<Prices> pricesOpt = pricesRepository.findById(id);
        if (!pricesOpt.isEmpty()){
            return pricesOpt.get();
        }
        return null;
    }

    public List<Prices> findAll(PriceSpecification spec) {
        return pricesRepository.findAll(spec);
    }

    public Prices createNewPrice(CreatePriceDTO pricesDTO) {
        LocalDateTime startDate = getDateformatted(pricesDTO.getStartDate());
        LocalDateTime endDate = getDateformatted(pricesDTO.getEndDate());

        Prices prices = new Prices();
        prices.setBrandId(pricesDTO.getBrandId());

        if (startDate != null){
            prices.setStartDate(startDate);
        }
        else{
            prices.setStartDate(LocalDateTime.now());
        }

        if (endDate != null){
            prices.setEndDate(endDate);
        }
        else{
            prices.setEndDate(LocalDateTime.now());
        }

        prices.setPriceList(pricesDTO.getPriceList());
        prices.setProductId(pricesDTO.getProductId());
        prices.setPrice(pricesDTO.getPrice());
        prices.setCurr(pricesDTO.getCurr());
        prices.setPriority(pricesDTO.getPriority());
        Prices save = pricesRepository.save(prices);
        return save;
    }

    public void deletePrice(int id) {
        Prices price = getById(id);
        if (price != null){
            pricesRepository.delete(price);
        }
    }

    public Prices updatePrice(int id, CreatePriceDTO priceDTO) {
        Prices prices = getById(id);
        if (prices != null){
            float price = priceDTO.getPrice();
            int priceList = priceDTO.getPriceList();
            int priority = priceDTO.getPriority();
            String curr = priceDTO.getCurr();
            int brandId = priceDTO.getBrandId();
            String startDate = priceDTO.getStartDate();
            String endDate = priceDTO.getEndDate();
            int productId = priceDTO.getProductId();

            if (price > 0){
                prices.setPrice(price);
            }
            if (priceList > 0 ){
                prices.setPriceList(priceList);
            }
            if (priority >= 0){
                prices.setPriority(priority);
            }
            if (curr != null){
                prices.setCurr(curr);
            }
            if (brandId > 0){
                prices.setBrandId(brandId);
            }
            if (startDate != null){
                LocalDateTime dateformatted = getDateformatted(startDate);
                prices.setStartDate(dateformatted);
            }

            if (endDate != null){
                LocalDateTime dateformatted = getDateformatted(endDate);
                prices.setEndDate(dateformatted);
            }
            if (productId > 0){
                prices.setProductId(productId);
            }
            Prices saved = pricesRepository.save(prices);
            if (saved != null){
                return saved;
            }
        }
        return null;
    }

    public List<Float> getListOfPricesWithooutPriority(int productId) {
        Optional<List<Prices>> pricesOpt = pricesRepository.findByProductId(productId);
        if (!pricesOpt.isPresent()){
            return Collections.emptyList();
        }
        List<Float> pricesFloat = new ArrayList<>();
        for (Prices price: pricesOpt.get()){
            float priceFloat = price.getPrice();
            pricesFloat.add(priceFloat);
        }
        return pricesFloat;
    }
}
