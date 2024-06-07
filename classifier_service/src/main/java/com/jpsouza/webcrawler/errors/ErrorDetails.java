package com.jpsouza.webcrawler.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private List<String> messages;
    private String details;
    private Throwable exception;
    private Integer status;
}

