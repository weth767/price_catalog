package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.dtos.DomainDTO;
import com.jpsouza.webcrawler.dtos.DomainFilterDTO;
import com.jpsouza.webcrawler.mappers.DomainMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.models.Domain;
import com.jpsouza.webcrawler.repositories.DomainRepository;
import com.jpsouza.webcrawler.utils.UrlUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;

    public Page<DomainDTO> findAllPageable(DomainFilterDTO domainFilter, Pageable pageable) {
        return domainRepository.findAll(pageable).map(domainMapper::toDTO);
    }

    public List<Domain> findByUrlInOrderByIdAsc(Set<String> urls) {
        return domainRepository
                .findByUrlInOrderByIdAsc(urls.stream().map(UrlUtils::getOnlyDomainFromUrl).collect(Collectors.toSet()));
    }

    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    public void upsertAll(Set<String> urls) {
        Set<String> formattedUrls = urls.stream()
                .map(UrlUtils::getOnlyDomainFromUrl).collect(Collectors.toSet());
        Set<String> domains = domainRepository.findByUrlInOrderByIdAsc(formattedUrls)
                .stream()
                .map((domain) -> domain.url)
                .collect(Collectors.toSet());
        Set<String> filteredDomains = formattedUrls.stream()
                .filter(url -> !domains.contains(url)).collect(Collectors.toSet());
        List<Domain> domainsToSave = new ArrayList<>();
        for (String filteredUrl : filteredDomains) {
            Domain domain = new Domain();
            domain.name = UrlUtils.getDomainName(filteredUrl);
            String url = UrlUtils.getOnlyDomainFromUrl(filteredUrl);
            domain.url = Objects.nonNull(url) ? url : filteredUrl;
            domain.verified = false;
            domainsToSave.add(domain);
        }
        domainRepository.saveAll(domainsToSave);
    }
}
