// src/main/java/com/filcon/electronics/controller/ContactController.java
package com.filcon.electronics.controller;

import com.filcon.electronics.dto.ApiResponseDTO;
import com.filcon.electronics.dto.ContactFormDTO;
import com.filcon.electronics.entity.ContactInquiry;
import com.filcon.electronics.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponseDTO<Long>> submitContactForm(
            @Valid @RequestBody ContactFormDTO contactForm,
            HttpServletRequest request) {

        try {
            String ipAddress = getClientIpAddress(request);
            log.info("Contact form submission from IP: {} for email: {}", ipAddress, contactForm.getEmail());

            ContactInquiry savedInquiry = contactService.submitContactForm(contactForm, ipAddress);

            return ResponseEntity.ok(ApiResponseDTO.success(
                    "Thank you for your inquiry! We'll get back to you within 24 hours.",
                    savedInquiry.getId()
            ));

        } catch (Exception e) {
            log.error("Error processing contact form submission", e);
            return ResponseEntity.badRequest().body(
                    ApiResponseDTO.error("Unable to process your request. Please try again later.")
            );
        }
    }

    @GetMapping("/product-interests")
    public ResponseEntity<ApiResponseDTO<ContactInquiry.ProductInterest[]>> getProductInterests() {
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Product interests retrieved successfully",
                ContactInquiry.ProductInterest.values()
        ));
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }

        return request.getRemoteAddr();
    }
}