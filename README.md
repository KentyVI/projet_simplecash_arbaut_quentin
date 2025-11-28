<div align="center">

# SimpleCash — Backend Spring Boot complet

Gestion des managers, agences, conseillers, clients, comptes et cartes avec audit, virements, simulation de crédit, logging AOP et documentation Swagger.

<a href="https://www.java.com/"><img alt="Java" src="https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white"></a>
<a href="https://spring.io/projects/spring-boot"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white"></a>
<a href="#documentation"><img alt="OpenAPI" src="https://img.shields.io/badge/OpenAPI-3.0-85EA2D?logo=openapiinitiative&logoColor=black"></a>
<a href="#tests"><img alt="Tests" src="https://img.shields.io/badge/Tests-MockMvc-blue"></a>

![Diagramme](diagram_simplecash.png)

</div>

## Sommaire

- [Liens rapides](#liens-rapides)
- [Fonctionnalités](#fonctionnalités)
- [Stack technique](#stack-technique)
- [Démarrage rapide](#démarrage-rapide)
  - [Mode MySQL (par défaut)](#démarrage-rapide)
  - [Mode H2 (mémoire, sans MySQL)](#mode-h2-mémoire-sans-mysql)
- [Principaux endpoints](#principaux-endpoints)
  - [Exemples rapides](#exemples-rapides)
- [Logging AOP](#logging-aop)
- [Seeds de données](#seeds-de-données)
- [Tests](#tests)
- [Structure du projet](#structure-du-projet)
- [Notes](#notes)
- [Difficultés Rencontrées](#difficultés-rencontrées-et-solutions)
- [Dépannage](#dépannage)

## Liens rapides

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Endpoint Audit: `GET /api/managers/audit`

## Fonctionnalités

- Managers, Agences, Conseillers, Clients, Comptes (courant/épargne), Cartes
- Virements compte à compte avec règles de découvert par type de client
  - Particulier: solde ≥ -5000
  - Entreprises: solde ≥ -50000
- Simulation de crédit (mensualité et coût total)
- Audit global (via Manager) des comptes non conformes
- Logging AOP lisible (reqId, méthode, path, durée) + logs fichiers tournants
- Seeds de données au démarrage (idempotent)
- Documentation interactive Swagger/OpenAPI

## Stack technique

- Java 17, Spring Boot 3
- Spring Web, Spring Data JPA (Hibernate)
- Base de données: MySQL (par défaut) ou H2 mémoire pour tests
- Springdoc OpenAPI (Swagger UI)

## Démarrage rapide

1) Prérequis
- Java 17
- MySQL local (ou adaptez la configuration pour H2)

2) Configuration BDD (par défaut MySQL)
Fichier: `SimpleCash/src/main/resources/application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/<nom_de_votre_db>?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=<le_votre>
spring.datasource.password=<le_votre>
spring.jpa.hibernate.ddl-auto=create-drop
app.initdb=true
```

3) Lancer l'application

```
cd SimpleCash
./gradlew bootRun
```

4) Ouvrir la documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Mode H2 (mémoire, sans MySQL)

Lancer rapidement l'appli en H2, sans installer de base de données:

```
cd SimpleCash
./gradlew bootRun --args='--spring.datasource.url=jdbc:h2:mem:sc;DB_CLOSE_DELAY=-1 \
  --spring.datasource.driverClassName=org.h2.Driver \
  --spring.datasource.username=sa \
  --spring.datasource.password= \
  --spring.jpa.hibernate.ddl-auto=create-drop'
```

## Principaux endpoints

- Managers: `GET/POST/PUT/DELETE /api/managers`, `POST /api/managers/{id}/conseillers`, `GET /api/managers/audit`
- Agences: `GET/POST/PUT/DELETE /api/agences`
- Conseillers: `GET/POST/PUT/DELETE /api/conseillers`
  - Clients d’un conseiller: `POST /api/conseillers/{id}/clients`, `PUT/DELETE /api/conseillers/clients/{clientId}`
  - Virements: `POST /api/conseillers/virements`
  - Simulation crédit: `GET /api/conseillers/simuler-credit`
- Clients: `GET /api/clients`, `GET /api/clients/{id}`
  - Comptes: `POST /api/clients/{id}/compte-courant`, `POST /api/clients/{id}/compte-epargne`
- Cartes: `GET /api/cartes`, `GET /api/cartes/{id}`, `POST /api/cartes/client/{clientId}`, `DELETE /api/cartes/{id}`

### Exemples rapides

- Création d’un conseiller
```
POST /api/conseillers
{
  "nom":"Martin", "prenom":"Bob", "email":"b@b.fr", "telephone":"0700000000", "managerId":1
}
```

- Virement
```
POST /api/conseillers/virements
{
  "clientSourceId": 1,
  "clientDestId": 2,
  "montant": 500.0
}
```

- Audit global
```
GET /api/managers/audit
```

## Logging AOP

- Filtre par requête + Aspect autour des contrôleurs/services.
- Sortie console et fichier `SimpleCash/logs/simplecash.log`.
- Exemple: `-> GET /api/clients ClientController.list(..) ...` puis `<- ... status=200 took=8ms`.

## Seeds de données

- Le démarrage remplit la base si nécessaire (managers/agences/conseillers/clients) sans duplication.
- Pour repartir de zéro: drop la base MySQL ou passez temporairement `spring.jpa.hibernate.ddl-auto=create`.

## Tests

```
cd SimpleCash
./gradlew test
```

- Intégration: `RoutesIntegrationTest` (CRUD, virements, transfert, simulation, suppression…)
- Cas d’échec: virement avec solde insuffisant (status 4xx/5xx).

## Structure du projet

```
SimpleCash/
 ├─ src/main/java/com/example/simplecash
 │   ├─ controller/    # API REST (Managers, Agences, Conseillers, Clients, Cartes)
 │   ├─ service/       # Règles métier (audit, virements, création comptes…)
 │   ├─ repository/    # Spring Data JPA
 │   ├─ entity/        # Entités JPA (Client, Manager, Agence, Compte*, Card…)
 │   ├─ dto/           # DTO d’échange (dont VirementDTO annoté Swagger)
 │   └─ aop/           # Logging AOP
 ├─ src/test/java/...  # Tests d’intégration
 ├─ build.gradle       # Dépendances & plugins
 └─ diagram_simplecash.png
```

## Notes

- Les règles de découvert sont implémentées dans `ConseillerService.effectuerVirement`.
- L’audit global est exposé via `GET /api/managers/audit` (voir `ManagerService.auditerComptes`).


## Difficultés Rencontrées et Solutions

Ce projet a représenté un défi technique stimulant, permettant une montée en compétence sur des concepts avancés de l'écosystème Spring :

- **Tests d'Intégration (MockMvc) :** La difficulté principale a été d'orchestrer des scénarios de tests séquentiels et dépendants (création Manager → Agence → Conseiller → Client). Il a fallu structurer rigoureusement les tests pour garantir la cohérence des données lors de l'exécution automatique.

- **Programmation Orientée Aspect (AOP) & Logs :** L'implémentation du logging via AOP a nécessité l'apprentissage du fonctionnement des *Proxies* Spring. Le défi était d'intercepter proprement les méthodes de service pour tracer les opérations sensibles (virements) sans polluer le code métier avec du code technique répétitif.

- **Documentation OpenAPI (Swagger) :** L'intégration de Swagger pour une documentation "propre" était une nouveauté. Il a fallu maîtriser les annotations spécifiques (`@Operation`, `@Schema`, `@ApiResponses`) pour que l'interface Swagger UI serve de véritable documentation contractuelle pour le client, et non pas seulement d'outil de debug.

---

Bon build, bons tests, et amusez-vous bien avec l’API SimpleCash ! 🚀

## Dépannage

- 404 Whitelabel sur `/`:
  - Normal si aucune redirection n’est configurée. Allez directement sur `/swagger-ui.html` ou utilisez les routes `/api/...`.
- DB vide après lancement:
  - Le seed est idempotent et se lance si nécessaire. Si vous venez d’un ancien schéma, faites un `DROP/CREATE` de la base ou lancez une fois avec `--spring.jpa.hibernate.ddl-auto=create`.
- Erreur `com.mysql.cj.jdbc.Driver` introuvable:
  - Lancez via `./gradlew bootRun` (classpath géré) et vérifiez vos identifiants MySQL dans `application.properties`.
- Pas de logs:
  - Les logs AOP apparaissent lors des appels HTTP; appelez un endpoint `/api/...`. Les fichiers sont dans `SimpleCash/logs`.
