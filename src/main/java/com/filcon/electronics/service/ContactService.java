// src/main/java/com/filcon/electronics/service/ContactService.java
package com.filcon.electronics.service;

import com.filcon.electronics.dto.ContactFormDTO;
import com.filcon.electronics.entity.ContactInquiry;
import com.filcon.electronics.exception.ContactSubmissionException;
import com.filcon.electronics.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactInquiryRepository contactRepository;
    private final EmailService emailService;
    private final ValidationService validationService;

    @Transactional
    public ContactInquiry submitContactForm(ContactFormDTO contactForm, String ipAddress) {
        try {
            // Rate limiting check
            validationService.checkRateLimit(contactForm.getEmail());

            // Create entity from DTO
            ContactInquiry inquiry = createInquiryFromDTO(contactForm, ipAddress);

            // Save to database
            ContactInquiry savedInquiry = contactRepository.save(inquiry);
            log.info("Contact inquiry saved with ID: {}", savedInquiry.getId());

            // Send emails asynchronously
            emailService.sendConfirmationEmail(savedInquiry);
            emailService.sendNotificationEmail(savedInquiry);

            return savedInquiry;

        } catch (Exception e) {
            log.error("Error submitting contact form: ", e);
            throw new ContactSubmissionException("Failed to submit contact form: " + e.getMessage());
        }
    }

    private ContactInquiry createInquiryFromDTO(ContactFormDTO dto, String ipAddress) {
        ContactInquiry inquiry = new ContactInquiry();
        inquiry.setFirstName(dto.getFirstName().trim());
        inquiry.setLastName(dto.getLastName().trim());
        inquiry.setEmail(dto.getEmail().trim().toLowerCase());
        inquiry.setPhone(dto.getPhone().trim());
        inquiry.setCompanyName(dto.getCompanyName() != null ? dto.getCompanyName().trim() : null);
        inquiry.setProductInterest(dto.getProductInterest());
        inquiry.setMessage(dto.getMessage().trim());
        inquiry.setIpAddress(ipAddress);
        inquiry.setStatus(ContactInquiry.InquiryStatus.NEW);

        return inquiry;
    }
}