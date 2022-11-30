package com.francisconicolau.comerce;


import com.francisconicolau.prices.controller.PricesController;
import com.francisconicolau.prices.model.Prices;
import com.francisconicolau.prices.model.dto.PricesDTO;
import com.francisconicolau.prices.repository.PricesRepository;
import com.francisconicolau.prices.services.PricesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration(exclude={AutoConfigureTestDatabase.class,
        FlywayAutoConfiguration.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@EnableJpaRepositories(basePackageClasses = PricesRepository.class)
@EntityScan(basePackageClasses = Prices.class)
/*@ContextConfiguration(classes = {PricesService.class, PricesRepository.class})*/
public class H2DatabaseTests {

    private static final Logger log = LoggerFactory.getLogger(H2DatabaseTests.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    PricesRepository pricesRepository;

/*    @Before
    public void setUp()  {
        pricesService = new PricesService(pricesRepository);
    }*/

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void test1_on_getPricesByParameters() {
        PricesDTO pricesDTO = new PricesDTO();
        pricesDTO.setBrandId(1);
        pricesDTO.setDate("2020-06-14 10:00:00");
        pricesDTO.setProductId(354555);
       /* List<Prices> prices = pricesService.getPricesByParameters(pricesDTO);*/
        String date= "2020-06-14 10:00:00";
        LocalDateTime dateformatted = getDateformatted(date);

        Optional<List<Prices>> pricesOpt = pricesRepository.findByParameters(dateformatted, 35455, 1);
        List<Prices> prices = pricesOpt.get();
        assertEquals(prices.get(0).getId(), 1);
        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getId(), 1);
        assertEquals(prices.get(0).getPriority(), 0);

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
