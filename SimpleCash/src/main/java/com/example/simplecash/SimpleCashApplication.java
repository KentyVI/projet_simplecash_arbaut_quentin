package com.example.simplecash;

import com.example.simplecash.entity.*;
import com.example.simplecash.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SimpleCashApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCashApplication.class, args);
	}

    @Bean
    CommandLineRunner seedData(ManagerRepository managerRepository,
                               AgenceRepository agenceRepository,
                               ConseillerRepository conseillerRepository,
                               ClientRepository clientRepository) {
        return args -> {
            if (managerRepository.count() > 0) return; // déjà rempli

            // Managers
            Manager g1 = new Manager();
            g1.setNom("Durand"); g1.setPrenom("Alice"); g1.setEmail("alice@sc.fr"); g1.setTelephone("0600000001");
            g1 = managerRepository.save(g1);

            Manager g2 = new Manager();
            g2.setNom("Martin"); g2.setPrenom("Paul"); g2.setEmail("paul@sc.fr"); g2.setTelephone("0600000002");
            g2 = managerRepository.save(g2);

            // Agences
            Agence a1 = new Agence();
            a1.setId("AG001");
            a1.setDateCreation(LocalDate.now().minusYears(2));
            a1.setManager(g1);
            agenceRepository.save(a1);

            Agence a2 = new Agence();
            a2.setId("AG002");
            a2.setDateCreation(LocalDate.now().minusYears(1));
            a2.setManager(g2);
            agenceRepository.save(a2);

            // Conseillers
            Conseiller c11 = new Conseiller();
            c11.setNom("Bernard"); c11.setPrenom("Luc"); c11.setEmail("luc@sc.fr"); c11.setTelephone("0700000001"); c11.setManager(g1);
            c11 = conseillerRepository.save(c11);

            Conseiller c12 = new Conseiller();
            c12.setNom("Leroy"); c12.setPrenom("Chloe"); c12.setEmail("chloe@sc.fr"); c12.setTelephone("0700000002"); c12.setManager(g1);
            c12 = conseillerRepository.save(c12);

            Conseiller c21 = new Conseiller();
            c21.setNom("Petit"); c21.setPrenom("Marc"); c21.setEmail("marc@sc.fr"); c21.setTelephone("0700000003"); c21.setManager(g2);
            c21 = conseillerRepository.save(c21);

            // Clients + comptes + cartes
            createClient(clientRepository, c11, "Dupont", "Jean", TypeClient.PARTICULIER,
                    1500d, 1000d, 500d, 0.03, List.of(TypeCard.VISA_ELECTRON));
            createClient(clientRepository, c11, "Durand", "Emma", TypeClient.ENTREPRISES,
                    10000d, 5000d, 0d, null, List.of(TypeCard.VISA_PREMIER));
            createClient(clientRepository, c12, "Martin", "Sofia", TypeClient.PARTICULIER,
                    800d, 1000d, 300d, 0.03, List.of(TypeCard.VISA_ELECTRON, TypeCard.VISA_PREMIER));
            createClient(clientRepository, c21, "Moreau", "Hugo", TypeClient.PARTICULIER,
                    1200d, 500d, null, null, List.of());
        };
    }

    private static void createClient(ClientRepository clientRepository,
                                     Conseiller conseiller,
                                     String nom, String prenom,
                                     TypeClient type,
                                     Double soldeCc, Double decouvert,
                                     Double soldeCe, Double taux,
                                     List<TypeCard> cartes) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setAdresse("1 rue "+nom);
        client.setCodePostal("75000");
        client.setVille("Paris");
        client.setTelephone("0600000000");
        client.setTypeClient(type);
        client.setConseiller(conseiller);

        // Compte courant
        CompteCourant cc = new CompteCourant();
        cc.setNumeroCompte("CC-"+nom.substring(0,2).toUpperCase()+System.currentTimeMillis()%10000);
        cc.setDateOuverture(LocalDate.now().minusMonths(3));
        cc.setSolde(soldeCc != null ? soldeCc : 0d);
        if (decouvert != null) cc.setDecouvertAutorise(decouvert);
        client.setCompteCourant(cc);

        // Compte épargne (optionnel)
        if (soldeCe != null) {
            CompteEpargne ce = new CompteEpargne();
            ce.setNumeroCompte("CE-"+nom.substring(0,2).toUpperCase()+System.currentTimeMillis()%10000);
            ce.setDateOuverture(LocalDate.now().minusMonths(2));
            ce.setSolde(soldeCe);
            if (taux != null) ce.setTauxRemuneration(taux);
            client.setCompteEpargne(ce);
        }

        // Cartes
        for (TypeCard typeCard : cartes) {
            Card card = new Card();
            card.setType(typeCard);
            card.setNumeroCarte("4" + (long)(Math.random()*1_000_000));
            card.setDateExpiration(LocalDate.now().plusYears(4));
            client.ajouterCarte(card);
        }

        clientRepository.save(client);
    }
}
