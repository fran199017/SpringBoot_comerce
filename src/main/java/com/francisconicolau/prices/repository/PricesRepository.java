package com.francisconicolau.prices.repository;

import com.francisconicolau.prices.model.Prices;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository("pricesRepository")
public interface PricesRepository extends PagingAndSortingRepository<Prices, Integer>, JpaSpecificationExecutor<Prices> {

    @Query(value = "select p FROM Prices p where :date between p.startDate " +
            "AND p.endDate and p.productId = :productId AND p.brandId = :brandId")
    Optional<List<Prices>> findByParameters(LocalDateTime date, @Param("productId")int productId, @Param("brandId")int brandId);
}
