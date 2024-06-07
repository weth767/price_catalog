package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.projections.BrandCountProjection;
import com.jpsouza.webcrawler.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public Page<BrandCountProjection> getTopProductBrandPaged(Pageable pageable) {
        return brandRepository.getTopProductBrandPaged(pageable);
    }
}
