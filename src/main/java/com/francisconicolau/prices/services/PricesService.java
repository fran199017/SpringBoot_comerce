package com.francisconicolau.prices.services;

import com.francisconicolau.prices.model.PriceSpecification;
import com.francisconicolau.prices.model.Prices;
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

        LocalDateTime start = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(start, productId, brandId);

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
}
