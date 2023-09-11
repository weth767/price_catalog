package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.models.Domain;
import com.jpsouza.webcrawler.repositories.DomainRepository;
import com.jpsouza.webcrawler.utils.FormatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;

    public Optional<Domain> findNextNotVerifiedDomain() {
        return domainRepository.findFirstByVerifiedFalseOrderByIdAsc();
    }

    public List<Domain> findByUrlInOrderByIdAsc(Set<String> urls) {
        return domainRepository.findByUrlInOrderByIdAsc(urls);
    }

    public void upsertDomain(String url) {
        if (!domainRepository.existsByUrl(url)) {
            try {
                Domain domain = new Domain();
                domain.url = url;
                domain.name = FormatUtils.getOnlyDomainFromUrl(url);
                domain.verified = false;
                domainRepository.save(domain);
                domain.verifiedIn = LocalDateTime.now();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void upsertAll(Set<String> urls) {
        List<Domain> domains = domainRepository.findByUrlInOrderByIdAsc(urls);
        Set<String> domainsUrlSet = domains.stream()
                .map((domain) -> domain.url)
                .collect(Collectors.toSet());
        Set<String> filteredDomains = urls.stream()
                .filter(url -> !domainsUrlSet.contains(url))
                .collect(Collectors.toSet());
        List<Domain> domainsToSave = new ArrayList<>();
        for (String filteredUrl : filteredDomains) {
            try {
                Domain domain = new Domain();
                domain.name = FormatUtils.getOnlyDomainFromUrl(filteredUrl);
                domain.url = filteredUrl;
                domain.verified = false;
                domain.verifiedIn = LocalDateTime.now();
                domainsToSave.add(domain);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        }
        domainRepository.saveAll(domainsToSave);
    }
}
