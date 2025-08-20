package com.filcon.electronics.util;

import com.filcon.electronics.entity.ContactInquiry;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class EmailTemplateUtil {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy 'at' HH:mm");

    public String formatDateTime(ContactInquiry inquiry) {
        return inquiry.getCreatedAt().format(DATE_FORMATTER);
    }

    public String getProductInterestDisplay(ContactInquiry.ProductInterest productInterest) {
        return productInterest != null ? productInterest.getDisplayName() : "Not specified";
    }

    public String formatPhoneNumber(String phone) {
        // Simple formatting for Indian phone numbers
        if (phone != null && phone.length() == 10) {
            return String.format("%s-%s-%s",
                    phone.substring(0, 3),
                    phone.substring(3, 6),
                    phone.substring(6));
        }
        return phone;
    }
}