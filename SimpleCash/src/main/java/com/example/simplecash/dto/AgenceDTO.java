package com.example.simplecash.dto;

import java.time.LocalDate;

public class AgenceDTO {
    private String id; // 5 caractères
    private LocalDate dateCreation;
    private Long managerId;

    public AgenceDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
}
