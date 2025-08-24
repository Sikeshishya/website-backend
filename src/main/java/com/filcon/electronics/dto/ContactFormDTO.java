// src/main/java/com/filcon/electronics/dto/ContactFormDTO.java
package com.filcon.electronics.dto;

import com.filcon.electronics.entity.ContactInquiry;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContactFormDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please provide a valid phone number")
    private String phone;

    @Size(max = 255, message = "Company name cannot exceed 255 characters")
    private String companyName;


}