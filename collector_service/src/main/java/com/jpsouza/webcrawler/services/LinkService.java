package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.dtos.DomainFilterDTO;
import com.jpsouza.webcrawler.dtos.LinkDTO;
import com.jpsouza.webcrawler.dtos.LinkFilterDTO;
import com.jpsouza.webcrawler.mappers.LinkMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.models.Domain;
import com.jpsouza.webcrawler.models.Link;
import com.jpsouza.webcrawler.repositories.LinkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

    public Page<LinkDTO> findAllPageable(LinkFilterDTO linkFilter, Pageable pageable) {
        if (Objects.nonNull(linkFilter.getDomain())) {
            return linkRepository.findByDomain_NameLikeIgnoreCase(linkFilter.getDomain(), pageable).map(linkMapper::toDTO);
        }
        return linkRepository.findAll(pageable).map(linkMapper::toDTO);
    }

    public boolean existsByUrlAndVerifiedTrue(String url) {
        return linkRepository.existsByUrlAndVerifiedTrue(url);
    }

    public Optional<Link> findByUrl(String url) {
        return linkRepository.findByUrl(url);
    }

    public void upsertLink(String url, Domain domain, boolean verified, LocalDateTime verifiedIn) {
        if (!linkRepository.existsByUrl(url)) {
            Link link = new Link();
            link.url = url;
            link.domain = domain;
            link.verified = verified;
            link.verifiedIn = verifiedIn;
            linkRepository.save(link);
        }
    }

    public void resetAllLinks() {
        linkRepository.resetAllLinks();
    }

    public void upsertLinks(Set<String> urls, Domain domain) {
        List<Link> links = linkRepository.findByUrlInOrderByIdAsc(urls);
        Set<String> linksUrlsSet = links.stream()
                .map((link) -> link.url)
                .collect(Collectors.toSet());
        Set<String> filteredLinks = urls.stream()
                .filter(url -> !linksUrlsSet.contains(url))
                .collect(Collectors.toSet());
        List<Link> linksToSave = new ArrayList<>();
        for (String filteredUrl : filteredLinks) {
            Link link = new Link();
            link.url = filteredUrl;
            link.verified = false;
            link.domain = domain;
            linksToSave.add(link);
        }
        linkRepository.saveAll(linksToSave);
    }

    public void updateLink(Link link) {
        if (link.id == null) {
            return;
        }
        linkRepository.saveAndFlush(link);
    }
}
