package com.example.simplecash.dto;

import java.time.LocalDate;

public class AgenceDTO {
    private String id; // 5 caractères
    private LocalDate dateCreation;
    private Long banqueId;
    private Long gerantId;

    public AgenceDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public Long getBanqueId() { return banqueId; }
    public void setBanqueId(Long banqueId) { this.banqueId = banqueId; }
    public Long getGerantId() { return gerantId; }
    public void setGerantId(Long gerantId) { this.gerantId = gerantId; }
}

