package com.jpsouza.webcrawler.dtos;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class ExplorationDataDTO {
    public Set<String> links = new HashSet<>();
    public int crawlers = 0;
    public boolean reset = false;
}
