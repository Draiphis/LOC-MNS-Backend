--password = rootroot

INSERT INTO etat (usure)
VALUES
    ('NEUF'),
    ('TRES_BON_ETAT'),
    ('BON_ETAT'),
    ('USURE_VISIBLE'),
    ('MAUVAIS_ETAT'),
    ('HORS_SERVICE');

INSERT INTO utilisateur (email, password, nom, prenom, date_de_naissance)
VALUES
    ('jean.dupont@example.com', '$2a$10$K/du.sGTNzIYFYjPYlsa1uXLP54JcVEAKhbdw3.p6qGbce7Nzr9yW', 'Dupont', 'Jean', '1990-05-14'),
    ('marie.martin@example.com', '$2a$10$K/du.sGTNzIYFYjPYlsa1uXLP54JcVEAKhbdw3.p6qGbce7Nzr9yW', 'Martin', 'Marie', '1985-09-22'),
    ('paul.durand@example.com', '$2a$10$K/du.sGTNzIYFYjPYlsa1uXLP54JcVEAKhbdw3.p6qGbce7Nzr9yW', 'Durand', 'Paul', '1998-01-10'),
    ('sophie.bernard@example.com', '$2a$10$K/du.sGTNzIYFYjPYlsa1uXLP54JcVEAKhbdw3.p6qGbce7Nzr9yW', 'Bernard', 'Sophie', '1992-07-03'),
('j.d@e.com', '$2a$10$K/du.sGTNzIYFYjPYlsa1uXLP54JcVEAKhbdw3.p6qGbce7Nzr9yW', 'Dupont', 'Jean', '1990-04-14');



INSERT INTO role (role)
VALUES
    ('DEFAULT'),
    ('CDA'),
    ('DESIGN'),
    ('PROF'),
    ('ELEVE'),
    ('ADMIN'),
    ('ADMINISTRATION'),
    ('COMMERCE'),
    ('RESEAU');

INSERT INTO utilisateur_role (utilisateur_id, role_id)
VALUES
    (1, 2),
    (1, 1),
    (2, 4),
    (2, 1),
    (3, 5),
    (3, 1),
    (4, 6),
    (4, 1);


INSERT INTO type (nom)
VALUES
    ('ordinateur'),
    ('tablette'),
    ('salle'),
    ('livre');

INSERT INTO marque (nom)
VALUES
    ('Asus'),
    ('Apple'),
    ('Samsung'),
    ('Dell'),
    ('HP'),
    ('Lenovo'),
    ('Cisco'),
    ('Logitech'),
    ('No Starch Press'),
    ('Pearson');


INSERT INTO modele (nom, image, description, type_id, marque_id)
VALUES

-- ORDINATEURS
(
    'ROG Strix G16',
    'rog_strix_g16',
    'ordinateur portable gaming Asus avec Intel Core i7 et 16 Go de RAM',
    1,
    1
),

(
    'MacBook Pro M3',
    'macbook_pro_m3',
    'ordinateur portable Apple avec puce M3 et écran Retina',
    1,
    2
),

(
    'Dell Latitude 5540',
    'dell_latitude_5540',
    'ordinateur professionnel destiné à la bureautique et au développement',
    1,
    4
),

(
    'HP EliteBook 840',
    'hp_elitebook_840',
    'ordinateur portable professionnel HP avec station d’accueil USB-C',
    1,
    5
),

(
    'ThinkPad T14',
    'thinkpad_t14',
    'ordinateur Lenovo robuste pour le développement et les environnements professionnels',
    1,
    6
),

-- TABLETTES
(
    'Galaxy Tab S9',
    'galaxy_tab_s9',
    'tablette Samsung avec écran AMOLED et stylet S-Pen',
    2,
    3
),

(
    'iPad Pro 13',
    'ipad_pro_13',
    'tablette Apple haute performance pour design et multimédia',
    2,
    2
),

(
    'Wacom Intuos Pro',
    'wacom_intuos_pro',
    'tablette graphique professionnelle avec stylet pour le design',
    2,
    8
),

-- SALLES
(
    'Salle Réseau 204',
    'salle',
    'salle équipée de switches Cisco et d’un vidéoprojecteur',
    3,
    7
),

(
    'Salle Design 104',
    'salle',
    'salle équipée de postes Apple et tablettes graphiques',
    3,
    2
),

(
    'Salle Dev 203',
    'salle',
    'salle de développement avec postes fixes et double écrans',
    3,
    4
),

-- LIVRES
(
    'Clean Code',
    'clean_code',
    'ouvrage sur les bonnes pratiques de développement logiciel',
    4,
    9
),

(
    'The Pragmatic Programmer',
    'the_pragmatic_programmer',
    'livre de référence pour développeurs professionnels',
    4,
    10
),

(
    'Computer Networking Basics',
    'computer_networking_basics',
    'livre d’introduction aux réseaux informatiques',
    4,
    10
);

INSERT INTO type_role (type_id, role_id)
VALUES

-- ordinateur
(1, 2),
(1, 6),
(1, 9),

-- tablette
(2, 2),
(2, 3),
(2, 6),

-- salle
(3, 4),
(3, 6),
(3, 7),

-- livre
(4, 2),
(4, 4),
(4, 5),
(4, 6);

INSERT INTO materiel (reference, modele_id)
VALUES
    ('PC-001', 1),
    ('PC-002', 1),
    ('TAB-001', 2),
    ('SALLE-203', 3),
    ('BOOK-CC-001', 12);

INSERT INTO etat_materiel (etat_id, materiel_id, date_modification_etat)
VALUES

-- PC-001 NEUF
(1, 1, NOW()),

-- PC-002 BON ETAT
(3, 2, NOW()),

-- TAB-001 TRES BON ETAT
(2, 3, NOW()),

-- SALLE-203 USURE
(4, 4, NOW()),

-- BOOK-CC-001 MAUVAIS ETAT
(5, 5, NOW());
