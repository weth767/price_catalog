package com.jpsouza.webcrawler.consumer_service.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class FormatUtils {
    public static BigDecimal convertFormattedStringToBigDecimal(final String amount, final Locale locale) {
        try {
            final NumberFormat format = NumberFormat.getNumberInstance(locale);
            if (format instanceof DecimalFormat) {
                ((DecimalFormat) format).setParseBigDecimal(true);
            }
            return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
        } catch (ParseException parseException) {
            return BigDecimal.ZERO;
        }
    }
}
