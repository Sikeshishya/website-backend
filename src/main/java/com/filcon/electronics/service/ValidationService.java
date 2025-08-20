package com.filcon.electronics.service;

import com.filcon.electronics.exception.ContactSubmissionException;
import com.filcon.electronics.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationService {

    private final ContactInquiryRepository contactRepository;

    @Value("${filcon.email.rate-limit.max-submissions:3}")
    private int maxSubmissionsPerWindow;

    @Value("${filcon.email.rate-limit.time-window-hours:24}")
    private int timeWindowHours;

    public void checkRateLimit(String email) {
        LocalDateTime windowStart = LocalDateTime.now().minusHours(timeWindowHours);
        long submissionCount = contactRepository.countByEmailSince(email, windowStart);

        if (submissionCount >= maxSubmissionsPerWindow) {
            log.warn("Rate limit exceeded for email: {} ({} submissions in last {} hours)",
                    email, submissionCount, timeWindowHours);
            throw new ContactSubmissionException(
                    "Too many submissions. Please wait before submitting another inquiry.");
        }

        log.debug("Rate limit check passed for email: {} ({}/{} submissions)",
                email, submissionCount, maxSubmissionsPerWindow);
    }
}