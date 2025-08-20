// src/main/java/com/filcon/electronics/repository/ContactInquiryRepository.java
package com.filcon.electronics.repository;

import com.filcon.electronics.entity.ContactInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {

    List<ContactInquiry> findByEmailOrderByCreatedAtDesc(String email);

    List<ContactInquiry> findByStatusOrderByCreatedAtDesc(ContactInquiry.InquiryStatus status);

    @Query("SELECT c FROM ContactInquiry c WHERE c.createdAt >= :startDate ORDER BY c.createdAt DESC")
    List<ContactInquiry> findRecentInquiries(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(c) FROM ContactInquiry c WHERE c.email = :email AND c.createdAt >= :since")
    long countByEmailSince(@Param("email") String email, @Param("since") LocalDateTime since);
}