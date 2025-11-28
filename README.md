# projet_simplecash_arbaut_quentin
Examen de JavaSpringBoot

Besoin/Information Client : 

- Besoin d'une application afin de gerer les client
- Banque organise en reseau d'agence
- Chaque agence possede un id unique de 5 caractere alpha-numerique et une date de creation
- Chaque agence possede un gerant unique
- Chaque gerant va gerer les conseillers, et les conseillers auront au plus de 10 clients.
- Un client possede un : Nom, Prenom, Adresse, Code Postal, Ville, Telephone.
- Chaque client peut disposer d'un compte courant et d'un compte epargne.
- Chaque compte bancaire (courant/epargne) est caracterise par un numero de compte (id), un 
  solde, et une date d'ouverture du compte.

- Le compte courant possede une autorisation de decouvert de 1000$ par defaut.

- Le compte epargne est caracterise par le taux de remuneration, 3% par defaut.

- La banque propose 2 cartes bancaire : Visa Electron et Visa Premier.

- Supprimer un client -> Supprimer tout les comptes associe au client -> Supprimer toute les cartes bancaires du client.

- le SI de simpleCash s'appelle SimpleCashSI, il permet au conseiller de creer un client, modifier les informations du clients, lire les informations du clients et supprimer un client.

- Le conseiller clientele peut effecter des virement de compte a compte ainsi que des simulation de credit (plafond du pret, la duree du pret, les taux d'interet et d'assurance).


- Un compte particulier ne doit pas etre debiteur de plus de 5000$
- Un compte entreprise ne doit pas etre debiteur de plus de 50 000$

-> 2 type de clients : Particuliers et Entreprises.



