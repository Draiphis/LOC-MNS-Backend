# MNS Loc — Back-end

API REST de l’application **MNS Loc**, dédiée à la consultation et à la
réservation de matériel.

Le back-end gère notamment :

- l’authentification par jeton JWT ;
- les utilisateurs et leurs rôles ;
- le catalogue : modèles, matériels, marques et types ;
- les demandes d’emprunt et leur validation ;
- le calcul des disponibilités sur une période ;
- la documentation de l’API avec Swagger/OpenAPI.

## Technologies

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA / Hibernate
- Spring Security
- PostgreSQL
- H2 pour les tests d’intégration
- Maven
- Lombok
- Springdoc OpenAPI / Swagger UI
- JUnit 5 et Mockito

## Prérequis

Pour une exécution locale :

- Java 17 ou une version compatible ;
- PostgreSQL 15 ou supérieur ;
- Docker Desktop, facultatif, pour lancer PostgreSQL plus facilement.

Le Maven Wrapper est fourni avec le projet. Une installation globale de Maven
n’est donc pas obligatoire.

## Configuration

L’application charge les variables contenues dans un fichier `.env` situé à la
racine du projet.

Dupliquer le fichier d’exemple :

```powershell
Copy-Item .example.env .env
```

Sous Linux ou macOS :

```bash
cp .example.env .env
```

Variables disponibles :

| Variable | Description | Exemple local |
|---|---|---|
| `DB_HOST` | Adresse du serveur PostgreSQL | `localhost` |
| `DB_PORT` | Port PostgreSQL | `5432` |
| `DB_USER` | Utilisateur de la base | `root` |
| `DB_PASSWORD` | Mot de passe de la base | `root` |
| `DB_NAME` | Nom de la base | `locmns_db` |
| `JWT_SECRET` | Clé utilisée pour signer les JWT | valeur secrète |
| `DDL_AUTO` | Stratégie Hibernate | `update` |
| `SQL_FILE` | Plateforme des scripts SQL | `init` |
| `SQL_INIT_MODE` | Exécution des données initiales | `never` |

Le fichier `.env` ne doit pas être versionné. En production, utiliser des
valeurs robustes et injecter les secrets depuis l’environnement de déploiement.

## Démarrage local

### 1. Lancer PostgreSQL

Avec Docker :

```bash
docker compose up -d postgres
```

Cette commande crée une base `locmns_db` accessible sur le port `5432`, avec
les valeurs définies dans `compose.yml`.

Il est également possible d’utiliser une installation PostgreSQL existante en
adaptant le fichier `.env`.

### 2. Lancer l’API

Sous Windows :

```powershell
.\mvnw.cmd spring-boot:run
```

Sous Linux ou macOS :

```bash
./mvnw spring-boot:run
```

L’API est ensuite disponible sur :

```text
http://localhost:8080
```

## Documentation Swagger

Une fois l’application démarrée :

- interface Swagger UI :
  `http://localhost:8080/swagger-ui/index.html`
- description OpenAPI JSON :
  `http://localhost:8080/v3/api-docs`

Pour essayer une route protégée :

1. appeler `POST /connexion` avec une adresse e-mail et un mot de passe ;
2. copier le JWT retourné ;
3. cliquer sur **Authorize** dans Swagger ;
4. saisir le jeton.

Swagger ajoute automatiquement le préfixe `Bearer` grâce au schéma de sécurité
configuré.

## Principales routes

| Domaine | Méthode et route | Description |
|---|---|---|
| Authentification | `POST /connexion` | Obtenir un JWT |
| Authentification | `POST /inscription` | Créer un compte utilisateur |
| Administration | `POST /admin/utilisateurs` | Créer un utilisateur en tant qu’administrateur |
| Catalogue | `GET /modele/list` | Consulter et filtrer les modèles |
| Matériel | `GET /materiel/list` | Lister les exemplaires physiques |
| Disponibilité | `GET /materiel/disponibilite-modele/{modeleId}` | Obtenir les dates indisponibles d’un modèle |
| Emprunts | `POST /emprunt/create` | Créer une demande d’emprunt |
| Emprunts | `GET /emprunt/mes-emprunts` | Consulter ses demandes et réservations |
| Administration | `GET /emprunt/list` | Lister les emprunts |
| Administration | `PUT /emprunt/{id}/valider` | Approuver une demande |
| Administration | `PUT /emprunt/{id}/refuser` | Refuser une demande |

Les autres opérations CRUD concernant les utilisateurs, rôles, types, marques,
modèles, matériels, états et documentations sont consultables dans Swagger.

## Règles principales

- Un mot de passe est chiffré avec BCrypt avant son enregistrement.
- Une adresse e-mail ne peut appartenir qu’à un seul utilisateur.
- Un compte créé depuis l’administration reçoit le rôle `DEFAULT`.
- Une demande d’emprunt est créée avec le statut `EN_ATTENTE`.
- Une demande approuvée porte le statut `APPROUVE`.
- Une demande refusée ne bloque plus la disponibilité du matériel.
- La disponibilité est calculée selon les réservations qui chevauchent la
  période demandée.
- Si une date de retour réelle existe, elle remplace la date de retour
  prévisionnelle dans le calcul.

## Structure du projet

```text
src/
├── main/
│   ├── java/com/mns/cda/locmns/
│   │   ├── config/       Configuration générale et Swagger
│   │   ├── controller/   Contrôleurs REST
│   │   ├── dao/          Dépôts Spring Data JPA
│   │   ├── dto/          Objets échangés avec les clients
│   │   ├── exception/    Exceptions métier et réponses d’erreur
│   │   ├── model/        Entités JPA et énumérations
│   │   ├── security/     JWT, authentification et autorisations
│   │   ├── service/      Logique métier
│   │   └── view/         Vues de sérialisation JSON
│   └── resources/
│       ├── application.properties
│       └── data-init.sql
└── test/
    ├── java/.../unit/          Tests unitaires
    ├── java/.../integration/   Tests d’intégration
    └── resources/
        └── application-integration.properties
```

## Tests

Exécuter uniquement les tests unitaires :

```powershell
.\mvnw.cmd test
```

Exécuter les tests unitaires et les tests d’intégration :

```powershell
.\mvnw.cmd verify
```

Sous Linux ou macOS, remplacer `.\mvnw.cmd` par `./mvnw`.

Les tests d’intégration utilisent une base H2 en mémoire, configurée en mode de
compatibilité PostgreSQL. Ils n’utilisent donc pas la base locale.

## Compilation

Créer le fichier exécutable :

```powershell
.\mvnw.cmd clean package
```

Le résultat est généré dans :

```text
target/app.jar
```

Lancer le fichier compilé :

```bash
java -jar target/app.jar
```

## Docker

Construire l’application avant de construire l’image :

```powershell
.\mvnw.cmd clean package
docker build -t loc-mns-back .
```

Lancer l’image en lui fournissant les variables nécessaires :

```bash
docker run --rm -p 8080:8080 --env-file .env loc-mns-back
```

Le fichier `compose.yml` permet également de lancer PostgreSQL et une image
préconfigurée du back-end.

## Données de démonstration

Le fichier `src/main/resources/data-init.sql` contient un jeu de données de
développement. Son chargement dépend de `SQL_INIT_MODE`.

Pour éviter les doublons, ne l’activer que sur une base vide. La valeur
habituelle après l’initialisation est :

```properties
SQL_INIT_MODE=never
```

## Projet front-end

Le client Angular se trouve dans le dépôt `loc_mns_front`. En développement, il
communique avec cette API sur `http://localhost:8080`.
