INSERT INTO utilisateur (email, password, nom, prenom, date_de_naissance)
VALUES
    ('jean.dupont@example.com', '$2a$10$hashedpassword1', 'Dupont', 'Jean', '1990-05-14'),
    ('marie.martin@example.com', '$2a$10$hashedpassword2', 'Martin', 'Marie', '1985-09-22'),
    ('paul.durand@example.com', '$2a$10$hashedpassword3', 'Durand', 'Paul', '1998-01-10'),
    ('sophie.bernard@example.com', '$2a$10$hashedpassword4', 'Bernard', 'Sophie', '1992-07-03');

INSERT INTO type (nom)
VALUES
    ('ordinateur'),
    ('tablette'),
    ('salle'),
    ('livre');

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


INSERT INTO modele (nom, description)
VALUES
    ('asus rogue 2', 'ordinateur portable avec 16 go de mémoire et intel core i7'),
    ('nokia 3310 long', 'tablette de dessin graphique 25 pouces avec stylet'),
    ('salle 203', 'salle de cours avec tableau interactif, capacité 24 places'),
    ('clean code', 'livre sur les bonnes pratiques en developpement et notamment le clean code');

INSERT INTO materiel (reference, modele_id)
VALUES
    ('PC-001', 1),
    ('PC-002', 1),
    ('TAB-001', 2),
    ('SALLE-203', 3),
    ('BOOK-CC-001', 4);

INSERT INTO type (nom)
VALUES
    ('ordinateur'),
    ('tablette'),
    ('salle'),
    ('livre');