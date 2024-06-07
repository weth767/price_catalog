package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.models.Brand;
import com.jpsouza.webcrawler.projections.BrandCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "SELECT code FROM brand WHERE code = (SELECT MAX(code) FROM brand)", nativeQuery = true)
    Long getLastCode();

    @Query(value = "select b.id as id, b.description as description, count(p.brand_id) as amountReferences from brand b " +
            "inner join product p ON p.brand_id = b.id " +
            "group by b.id " +
            "order by amountReferences desc", nativeQuery = true)
    Page<BrandCountProjection> getTopProductBrandPaged(Pageable pageable);
}
