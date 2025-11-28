package com.example.simplecash.dto;

public class TransferClientDTO {
    private Long clientId;
    private Long nouveauConseillerId;

    public TransferClientDTO() {}

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Long getNouveauConseillerId() { return nouveauConseillerId; }
    public void setNouveauConseillerId(Long nouveauConseillerId) { this.nouveauConseillerId = nouveauConseillerId; }
}

