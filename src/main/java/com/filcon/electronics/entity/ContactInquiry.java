// src/main/java/com/filcon/electronics/entity/ContactInquiry.java
package com.filcon.electronics.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_inquiries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "company_name", length = 255)
    private String companyName;



    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InquiryStatus status = InquiryStatus.NEW;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;



    public enum InquiryStatus {
        NEW, IN_PROGRESS, RESPONDED, CLOSED
    }
}