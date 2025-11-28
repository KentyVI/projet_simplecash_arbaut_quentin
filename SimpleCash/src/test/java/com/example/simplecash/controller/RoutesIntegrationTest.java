package com.example.simplecash.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoutesIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    private static Long managerId;
    private static String agenceId = "A1234";
    private static Long conseillerId;
    private static Long conseillerId2;
    private static Long client1Id;
    private static Long client2Id;
    private static Long cardId;

    @Test @Order(1)
    void createManager() throws Exception {
        String body = "{" +
                "\"nom\":\"Durand\",\"prenom\":\"Alice\",\"email\":\"a@b.fr\",\"telephone\":\"0600000000\"}";
        MvcResult res = mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode json = mapper.readTree(res.getResponse().getContentAsString());
        managerId = json.get("id").asLong();
        assertThat(managerId).isNotNull();
        mockMvc.perform(get("/api/managers")).andExpect(status().isOk());
        mockMvc.perform(get("/api/managers/"+managerId)).andExpect(status().isOk());
    }

    @Test @Order(2)
    void updateManager() throws Exception {
        String body = "{" +
                "\"nom\":\"Durand\",\"prenom\":\"Alice\",\"email\":\"alice@bank.fr\",\"telephone\":\"0600000001\"}";
        mockMvc.perform(put("/api/managers/"+managerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice@bank.fr"));
    }

    @Test @Order(3)
    void createAgence() throws Exception {
        String body = "{" +
                "\"id\":\""+agenceId+"\",\"dateCreation\":\"2024-01-01\",\"managerId\":"+managerId+"}";
        mockMvc.perform(post("/api/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(agenceId));
        mockMvc.perform(get("/api/agences")).andExpect(status().isOk());
        mockMvc.perform(get("/api/agences/"+agenceId)).andExpect(status().isOk());
    }

    @Test @Order(4)
    void updateAgence() throws Exception {
        String body = "{" +
                "\"dateCreation\":\"2024-05-10\"}";
        mockMvc.perform(put("/api/agences/"+agenceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateCreation").value("2024-05-10"));
    }

    @Test @Order(5)
    void createConseillerViaManager() throws Exception {
        String body = "{" +
                "\"nom\":\"Martin\",\"prenom\":\"Bob\",\"email\":\"b@b.fr\",\"telephone\":\"0700000000\"}";
        MvcResult res = mockMvc.perform(post("/api/managers/"+managerId+"/conseillers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode json = mapper.readTree(res.getResponse().getContentAsString());
        conseillerId = json.get("id").asLong();
        assertThat(conseillerId).isNotNull();
    }

    @Test @Order(6)
    void createDeuxiemeConseiller() throws Exception {
        String body = "{" +
                "\"nom\":\"Leroy\",\"prenom\":\"Chloe\",\"email\":\"c@b.fr\",\"telephone\":\"0700000001\",\"managerId\":"+managerId+"}";
        MvcResult res = mockMvc.perform(post("/api/conseillers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        conseillerId2 = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();
        assertThat(conseillerId2).isNotNull();
        mockMvc.perform(get("/api/conseillers")).andExpect(status().isOk());
        mockMvc.perform(get("/api/conseillers/"+conseillerId2)).andExpect(status().isOk());
    }

    @Test @Order(10)
    void updateConseiller() throws Exception {
        String body = "{" +
                "\"nom\":\"Leroy\",\"prenom\":\"Chloe\",\"email\":\"chloe@bank.fr\",\"telephone\":\"0700000011\"}";
        mockMvc.perform(put("/api/conseillers/"+conseillerId2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("chloe@bank.fr"));
    }

    @Test @Order(11)
    void creerClientPourConseiller() throws Exception {
        String body = "{" +
                "\"nom\":\"Dupont\",\"prenom\":\"Jean\",\"adresse\":\"1 rue A\",\"codePostal\":\"75000\",\"ville\":\"Paris\",\"telephone\":\"0606060606\",\"typeClient\":\"PARTICULIER\"}";
        MvcResult res = mockMvc.perform(post("/api/conseillers/"+conseillerId+"/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        client1Id = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();
        assertThat(client1Id).isNotNull();
    }

    @Test @Order(12)
    void clientsListEtGet() throws Exception {
        mockMvc.perform(get("/api/clients")).andExpect(status().isOk());
        mockMvc.perform(get("/api/clients/"+client1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Dupont"));
    }

    @Test @Order(13)
    void modifierClient() throws Exception {
        String body = "{" +
                "\"adresse\":\"2 avenue B\"}";
        mockMvc.perform(put("/api/conseillers/clients/"+client1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adresse").value("2 avenue B"));
    }

    @Test @Order(14)
    void ajouterComptesAuClient() throws Exception {
        String cc = "{" +
                "\"numeroCompte\":\"CC-1\",\"soldeInitial\":1000,\"decouvertAutorise\":1000}";
        mockMvc.perform(post("/api/clients/"+client1Id+"/compte-courant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cc))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.compteCourantId").exists());

        String ce = "{" +
                "\"numeroCompte\":\"CE-1\",\"soldeInitial\":500,\"tauxRemuneration\":0.03}";
        mockMvc.perform(post("/api/clients/"+client1Id+"/compte-epargne")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ce))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.compteEpargneId").exists());
    }

    @Test @Order(15)
    void gererCartes() throws Exception {
        String body = "{" +
                "\"type\":\"VISA_ELECTRON\",\"numeroCarte\":\"411111\",\"dateExpiration\":\"2030-12-31\"}";
        MvcResult res = mockMvc.perform(post("/api/cartes/client/"+client1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        cardId = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/cartes")).andExpect(status().isOk());
        mockMvc.perform(get("/api/cartes/"+cardId)).andExpect(status().isOk());
    }

    @Test @Order(16)
    void transfererClient() throws Exception {
        String body = "{" +
                "\"clientId\":"+client1Id+",\"nouveauConseillerId\":"+conseillerId2+"}";
        mockMvc.perform(post("/api/conseillers/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test @Order(17)
    void creerClientPourVirementEtEffectuerVirement() throws Exception {
        String bodyC = "{" +
                "\"nom\":\"Petit\",\"prenom\":\"Marc\",\"adresse\":\"3 rue C\",\"codePostal\":\"31000\",\"ville\":\"Toulouse\",\"telephone\":\"0600000002\",\"typeClient\":\"PARTICULIER\"}";
        MvcResult res = mockMvc.perform(post("/api/conseillers/"+conseillerId+"/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyC))
                .andExpect(status().isCreated())
                .andReturn();
        client2Id = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();

        String cc2 = "{" +
                "\"numeroCompte\":\"CC-2\",\"soldeInitial\":100,\"decouvertAutorise\":1000}";
        mockMvc.perform(post("/api/clients/"+client2Id+"/compte-courant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cc2))
                .andExpect(status().isOk());

        String v = "{" +
                "\"clientSourceId\":"+client1Id+",\"clientDestId\":"+client2Id+",\"montant\":200}";
        mockMvc.perform(post("/api/conseillers/virements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(v))
                .andExpect(status().isOk());
    }

    @Test @Order(18)
    void simulerCredit() throws Exception {
        mockMvc.perform(get("/api/conseillers/simuler-credit")
                        .param("montant","10000")
                        .param("dureeMois","24")
                        .param("tauxAnnuel","3")
                        .param("assuranceAnnuel","0.3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensualite").exists())
                .andExpect(jsonPath("$.coutTotal").exists());
    }

    @Test @Order(19)
    void deleteCardThenClient() throws Exception {
        mockMvc.perform(delete("/api/cartes/"+cardId)).andExpect(status().isOk());
        mockMvc.perform(delete("/api/conseillers/clients/"+client1Id)).andExpect(status().isOk());
    }

    @Test @Order(20)
    void deleteConseillerSansClients() throws Exception {
        String body = "{" +
                "\"nom\":\"Zed\",\"prenom\":\"Zoe\",\"email\":\"z@z.fr\",\"telephone\":\"0700000009\",\"managerId\":"+managerId+"}";
        MvcResult res = mockMvc.perform(post("/api/conseillers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        Long toDeleteId = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/conseillers/"+toDeleteId)).andExpect(status().isOk());
    }

    @Test @Order(21)
    void deleteAgenceEtManagers() throws Exception {
        mockMvc.perform(delete("/api/agences/"+agenceId)).andExpect(status().isOk());
        String g2 = "{" +
                "\"nom\":\"John\",\"prenom\":\"Doe\",\"email\":\"j@d.fr\",\"telephone\":\"0500000000\"}";
        MvcResult res = mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(g2))
                .andExpect(status().isCreated())
                .andReturn();
        Long managerToDelete = mapper.readTree(res.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/managers/"+managerToDelete)).andExpect(status().isOk());
    }
}
