package com.jpsouza.webcrawler.core.services;

import com.jpsouza.webcrawler.core.models.Domain;
import com.jpsouza.webcrawler.core.models.Link;
import com.jpsouza.webcrawler.core.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

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
