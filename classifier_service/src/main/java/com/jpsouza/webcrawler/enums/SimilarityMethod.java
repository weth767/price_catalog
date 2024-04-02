package com.jpsouza.webcrawler.enums;

public enum SimilarityMethod {
    JAROWINKLER("JAROWINKLER"),
    JACCARD("JACCARD"),
    LEVENSHTEIN("LEVENSHTEIN"),
    DAMERAULEVENSHTEIN("DAMERAULEVENSHTEIN"),
    SMITHWATERMAN("SMITHWATERMAN"),
    SORENSENDICE("SORENSENDICE"),
    NGRAM("NGRAM"),
    NEEDLEMANWUNCH("NEEDLEMANWUNCH"),
    COSINE("COSINE"),
    MONGOSCORE("MONGOSCORE"),
    ALL("ALL");

    private final String value;

    private SimilarityMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
