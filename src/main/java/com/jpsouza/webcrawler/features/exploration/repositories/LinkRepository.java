package com.jpsouza.webcrawler.features.exploration.repositories;

import com.jpsouza.webcrawler.features.exploration.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
}
