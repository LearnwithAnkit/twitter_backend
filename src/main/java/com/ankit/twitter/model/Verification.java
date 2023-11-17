package com.ankit.twitter.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Verification {
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private String planType;
}
