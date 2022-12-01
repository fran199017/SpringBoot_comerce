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

    @Query(nativeQuery = true,value= "SELECT * from Prices where :date BETWEEN start_date AND end_date " +
            "AND product_id = :productId AND brand_id = :brandId order by priority DESC LIMIT 1")
    Optional<List<Prices>> findByParameters(LocalDateTime date, @Param("productId")int productId, @Param("brandId")int brandId);

    @Query(nativeQuery = true,value= "SELECT * from Prices where product_id = :productId order by priority DESC LIMIT 1")
    Optional<Prices> findByProductIdAndMaxPriority(@Param("productId")int productId);
}
