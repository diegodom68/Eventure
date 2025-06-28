package com.eventure.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime date;
    private String organizerUsername;
    private LocalDateTime createdAt;
}