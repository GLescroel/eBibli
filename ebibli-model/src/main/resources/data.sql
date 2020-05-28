INSERT INTO role (id, role) VALUES (1, 'Administrateur');
INSERT INTO role (id, role) VALUES (2, 'Abonne');
INSERT INTO role (id, role) VALUES (3, 'Visiteur');
INSERT INTO role (id, role) VALUES (4, 'Employe');
INSERT INTO utilisateur(id, email, nom, prenom, password, role_id) VALUES(1, 'ADMIN@OC.COM','ADMIN', 'ADMIN', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', '1');
INSERT INTO utilisateur(id, email, nom, prenom, password, role_id) VALUES(2, 'USER@OC.COM', 'USER', 'USER', '$2a$11$fwbi72Ko53c6Dvd87f7d7u7hkiFQ5dGnS2SO8JYdiMO41olj78EQi', '3');
INSERT INTO utilisateur(id, email, nom, prenom, password, role_id) VALUES(3, 'MEMBRE@OC.COM', 'MEMBRE', 'MEMBRE', '$2a$11$yel.cNbeeJNTEa7czcLHHup4wIJVQLqay293LmAewxQZgSV05KSAK', '2');
INSERT INTO utilisateur(id, email, nom, prenom, password, role_id) VALUES(4, 'GLESCROEL@HOTMAIL.COM', 'LESCROEL', 'Gregory', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', '1');
INSERT INTO utilisateur(id, email, nom, password, prenom, role_id) VALUES (5, 'DUPONT@OC.COM', 'DUPONT', '$2a$11$poeYMQ8pqjKSk1GqvidW2./pYd6kVly0iVrwKB8uWd/YBJocoUOci', 'RENÉ', 3);
INSERT INTO utilisateur(id, email, nom, password, prenom, role_id) VALUES (6, 'DUBOIS@OC.COM', 'DUBOIS', '$2a$11$2LLBNGe2bzp1YUsAA7T/RuXmzK7ascvDammvpgsN10bUwOH3LQi7O', 'ANDRÉE', 3);
INSERT INTO utilisateur(id, email, nom, password, prenom, role_id) VALUES (7, 'SMITH@OC.COM', 'SMITH', '$2a$11$doPqn9Ex3T9zKrjm3R09m.LnjbeEMNGKWwwR2ElBcevmvaPi9uQ32', 'JOHN', 3);
INSERT INTO utilisateur(id, email, nom, prenom, password, role_id) VALUES(8, 'GREGORY.LESCROEL@AXA.FR', 'LESCROEL', 'Gregory', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', '1');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (1, 'Les 30 plus belles histoires pour les tout-petits', 'Un recueil d"histoires merveilleuses pour nos tout petits', 'Histoires_tout_petits.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (2, 'Les plus belles histoires pour les enfants de 2 ans', 'Un recueil d"histoires merveilleuses pour les enfants de 2 ans', 'Histoires_2ans.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (3, 'Les plus belles histoires pour les enfants de 3 ans', 'Un recueil d"histoires merveilleuses pour les enfants de 3 ans', 'Histoires_3ans.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (4, 'Les plus belles histoires pour les enfants de 4 ans', 'Un recueil d"histoires merveilleuses pour les enfants de 4 ans', 'Histoires_4ans.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (5, 'Les plus belles histoires pour les enfants de 5 ans', 'Un recueil d"histoires merveilleuses pour les enfants de 5 ans', 'Histoires_5ans.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (6, 'Les plus belles histoires pour les enfants de 6 ans', 'Un recueil d"histoires merveilleuses pour les enfants de 6 ans', 'Histoires_6ans.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (7, 'Les plus belles histoires de fées et de princesses', 'Un recueil d"histoires merveilleuses pleines de fées, de princesses mais aussi de sorcières et de dragons', 'Histoires_fees_princesses.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (8, 'Les plus belles histoires du Prince de Motordu', 'Une compilation des meilleures aventures du Prince de Motordu', 'Histoires_Motordu.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (9, 'Je veux une histoire !', 'Une compilation des meilleures histoires d"une petite Princesse facétieuse', 'Petite_princesse.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (10, 'Les plus belles histoires pour l"école maternelle', 'Un recueil d"histoires merveilleuses pour les enfants en école maternelle', 'Histoires_ecole_maternelle.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (11, 'Les 25 plus belles histoires de noël', 'Un recueil d"histoires de noël merveilleuses', 'Histoires_noel.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (12, 'Les 20 plus belles histoires à lire le soir', 'Un recueil d"histoires merveilleuses à lire au coucher pour aider les enfants à faire de beaux rêves', 'Histoires_soir.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (13, 'Les lettres de mon moulin', 'Un recueil de nouvelles champêtres d"Alphonse Daudet', 'Lettres_moulin.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (14, 'Le seigneur des anneaux : la communauté de l"anneau', 'Une saga fantastique au pays du Gondor', 'Communaute_anneau.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (15, 'Le seigneur des anneaux : les deux tours', 'La suite des aventures de Fredon', 'Deux_tours.jpg');
INSERT INTO ouvrage(id, titre, resume, image) VALUES (16, 'Le seigneur des anneaux : le retour du Roi', 'La fin des aventures de Fredon dans le Mordor', 'Retour_roi.jpg');
INSERT INTO bibliotheque(id, nom) VALUES (1, 'Bibliotheque jeunesse');
INSERT INTO bibliotheque(id, nom) VALUES (2, 'Bibliotheque adulte');
INSERT INTO bibliotheque(id, nom) VALUES (3, 'Bibliotheque pour tous');
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (1, 1, 1, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (2, 2, 1, true );
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (3, 3, 1, false );
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (4, 1, 1, true );
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (5, 2, 3, false );
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (6, 5, 3, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (7, 4, 1, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (8, 6, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (9, 7, 1, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (10, 8, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (11, 14, 2, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (12, 15, 2, false);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (13, 16, 2, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (14, 9, 1, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (15, 10, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (16, 11, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (17, 12, 1, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (18, 13, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (19, 12, 1, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (20, 11, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (21, 14, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (22, 15, 3, true);
INSERT INTO livre(id, ouvrage_id, bibliotheque_id, disponible) VALUES (23, 16, 3, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (1, 1, 4, '2020-04-15 00:00:00', '2020-05-12 00:00:00', null, false, false, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (2, 2, 4, '2020-04-15 00:00:00', '2020-05-12 00:00:00', '2020-04-30 00:00:00', false, false, false);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (3, 3, 4, '2020-03-15 00:00:00', '2020-04-12 00:00:00', null, false, true, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (4, 4, 4, '2020-01-15 00:00:00', '2020-03-12 00:00:00', '2020-04-15 00:00:00', true, true, false);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (5, 5, 8, '2020-03-15 00:00:00', '2020-05-12 00:00:00', null, true, false, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (6, 6, 4, '2020-03-02 00:00:00', '2020-06-10 00:00:00', null, false, false, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (7, 7, 8, '2020-04-15 00:00:00', '2020-05-12 00:00:00', null, false, false, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (8, 8, 7, '2019-10-15 00:00:00', '2019-11-10 00:00:00', '2019-10-30 00:00:00', false, false, false);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (9, 9, 6, '2020-03-15 00:00:00', '2020-04-12 00:00:00', null, false, true, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (10, 10, 5, '2020-01-15 00:00:00', '2020-03-12 00:00:00', '2020-04-15 00:00:00', true, true, false);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (11, 11, 3, '2020-03-15 00:00:00', '2020-05-12 00:00:00', null, true, false, true);
INSERT INTO emprunt(id, livre_id, emprunteur_id, date_emprunt, date_retour_prevu, date_retour, prolonge, en_retard, encours) VALUES (12, 12, 2, '2020-03-02 00:00:00', '2020-03-30 00:00:00', null, false, false, true);
INSERT INTO reservation(id, ouvrage_id, emprunteur_id, date_reservation, alerte, date_alerte) VALUES (1, 2, 4, '2020-05-10 00:00:00', false, null);
INSERT INTO reservation(id, ouvrage_id, emprunteur_id, date_reservation, alerte, date_alerte) VALUES (2, 2, 5, '2020-05-11 00:00:00', false, null);
INSERT INTO reservation(id, ouvrage_id, emprunteur_id, date_reservation, alerte, date_alerte) VALUES (3, 2, 1, '2020-05-12 00:00:00', false, null);
INSERT INTO reservation(id, ouvrage_id, emprunteur_id, date_reservation, alerte, date_alerte) VALUES (4, 2, 2, '2020-05-13 00:00:00', false, null);
