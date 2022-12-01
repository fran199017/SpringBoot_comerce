package com.francisconicolau.comerce;


import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.repository.PricesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration(exclude={AutoConfigureTestDatabase.class,
        FlywayAutoConfiguration.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@EnableJpaRepositories(basePackageClasses = PricesRepository.class)
@EntityScan(basePackageClasses = Prices.class)
public class H2DatabaseTests {

    private static final Logger log = LoggerFactory.getLogger(H2DatabaseTests.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    PricesRepository pricesRepository;


    /*"Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)"*/
    @Test
    public void test1_on_getPricesByParameters() {
        String date = "2020-06-14 10:00:00";
        int productId = 35455;
        int brandId = 1;
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, productId, brandId);
        List<Prices> prices = pricesOpt.get();

        LocalDateTime start = getDateformatted("2020-06-14 00:00:00");
        LocalDateTime end = getDateformatted("2020-12-31 23:59:59");

        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 1);
        assertEquals(prices.get(0).getStartDate(), start);
        assertEquals(prices.get(0).getEndDate(), end);
        assertEquals(prices.get(0).getPriceList(), 1);
        assertEquals(prices.get(0).getPriority(), 0);
        assertEquals(prices.get(0).getPrice(),35.5, 0.01);
    }


    /*   "Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)"*/
    @Test
    public void test2_on_getPricesByParameters() {
        String date = "2020-06-14 16:00:00";
        int productId = 35455;
        int brandId = 1;
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, productId, brandId);
        List<Prices> prices = pricesOpt.get();

        LocalDateTime start = getDateformatted("2020-06-14 15:00:00");
        LocalDateTime end = getDateformatted("2020-06-14 18:30:00");

        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 2);
        assertEquals(prices.get(0).getStartDate(), start);
        assertEquals(prices.get(0).getEndDate(), end);
        assertEquals(prices.get(0).getPriceList(), 2);
        assertEquals(prices.get(0).getPrice(),25.45, 0.01);
        assertEquals(prices.get(0).getPriority(), 1);
    }

    /*Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)*/
    @Test
    public void test3_on_getPricesByParameters() {
        String date = "2020-06-14 21:00:00";
        int productId = 35455;
        int brandId = 1;
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, productId, brandId);
        List<Prices> prices = pricesOpt.get();

        LocalDateTime start = getDateformatted("2020-06-14 00:00:00");
        LocalDateTime end = getDateformatted("2020-12-31 23:59:59");

        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 1);
        assertEquals(prices.get(0).getStartDate(), start);
        assertEquals(prices.get(0).getEndDate(), end);
        assertEquals(prices.get(0).getPriceList(), 1);
        assertEquals(prices.get(0).getPriority(), 0);
        assertEquals(prices.get(0).getPrice(),35.5, 0.01);
    }

    /*Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)*/
    @Test
    public void test4_on_getPricesByParameters() {
        String date = "2020-06-15 10:00:00";
        int productId = 35455;
        int brandId = 1;
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, productId, brandId);
        List<Prices> prices = pricesOpt.get();

        LocalDateTime start = getDateformatted("2020-06-15 00:00:00");
        LocalDateTime end = getDateformatted("2020-06-15 11:00:00");

        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 3);
        assertEquals(prices.get(0).getStartDate(), start);
        assertEquals(prices.get(0).getEndDate(), end);
        assertEquals(prices.get(0).getPriceList(), 3);
        assertEquals(prices.get(0).getPriority(), 1);
        assertEquals(prices.get(0).getPrice(),30.5, 0.01);
    }

    /*Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)*/
    @Test
    public void test5_on_getPricesByParameters() {
        String date = "2020-06-16 21:00:00";
        int productId = 35455;
        int brandId = 1;
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, productId, brandId);
        List<Prices> prices = pricesOpt.get();

        LocalDateTime start = getDateformatted("2020-06-15 16:00:00");
        LocalDateTime end = getDateformatted("2020-12-31 23:59:59");

        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 4);
        assertEquals(prices.get(0).getStartDate(), start);
        assertEquals(prices.get(0).getEndDate(), end);
        assertEquals(prices.get(0).getPriceList(), 4);
        assertEquals(prices.get(0).getPriority(), 1);
        assertEquals(prices.get(0).getPrice(),38.95, 0.01);
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
}
