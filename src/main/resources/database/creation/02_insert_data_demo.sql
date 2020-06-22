--
-- Data for Name: bibliotheque; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bibliotheque (id, nom) VALUES (1, 'Bibliotheque jeunesse');
INSERT INTO public.bibliotheque (id, nom) VALUES (2, 'Bibliotheque adulte');
INSERT INTO public.bibliotheque (id, nom) VALUES (3, 'Bibliotheque pour tous');


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (1, '2020-05-30', NULL, '2020-06-27', false, true, false, 4, 1);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (2, '2020-04-15', '2020-04-30', '2020-05-12', false, false, false, 4, 2);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (3, '2020-05-01', NULL, '2020-05-29', true, true, false, 4, 3);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (4, '2020-01-15', '2020-04-15', '2020-03-12', true, false, true, 4, 4);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (5, '2020-05-03', NULL, '2020-06-29', false, true, true, 8, 5);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (6, '2020-05-18', NULL, '2020-06-15', false, true, false, 4, 6);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (7, '2020-05-20', NULL, '2020-06-05', false, true, false, 8, 7);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (8, '2019-10-15', '2019-10-30', '2019-11-10', false, false, false, 7, 8);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (9, '2020-03-15', NULL, '2020-04-12', true, true, false, 6, 9);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (10, '2020-01-15', '2020-04-15', '2020-03-12', true, false, true, 5, 10);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (11, '2020-04-20', NULL, '2020-06-12', false, true, true, 3, 11);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (12, '2020-06-02', NULL, '2020-06-30', false, true, false, 2, 12);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (13, '2020-05-20', NULL, '2020-06-18', false, true, false, 6, 2);
INSERT INTO public.emprunt (id, date_emprunt, date_retour, date_retour_prevu, en_retard, encours, prolonge, emprunteur_id, livre_id) VALUES (14, '2020-01-15', '2020-06-01', '2020-03-12', true, false, true, 7, 10);


--
-- Data for Name: livre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (1, false, false, 1, NULL, 1);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (2, false, false, 1, NULL, 2);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (3, false, false, 1, NULL, 3);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (4, true, false, 1, NULL, 1);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (5, false, false, 3, NULL, 2);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (6, false, false, 3, NULL, 5);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (7, false, false, 1, NULL, 4);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (8, true, false, 3, NULL, 6);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (9, false, false, 1, NULL, 7);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (10, true, true, 3, 4, 8);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (11, false, false, 2, NULL, 14);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (12, false, false, 2, NULL, 15);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (13, true, false, 2, NULL, 16);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (14, true, false, 1, NULL, 9);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (15, true, false, 3, NULL, 10);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (16, true, false, 3, NULL, 11);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (17, true, false, 1, NULL, 12);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (18, true, false, 3, NULL, 13);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (19, true, false, 1, NULL, 12);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (20, true, false, 3, NULL, 11);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (21, true, false, 3, NULL, 14);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (22, true, false, 3, NULL, 15);
INSERT INTO public.livre (id, disponible, reserve, bibliotheque_id, next_emprunteur_id, ouvrage_id) VALUES (23, true, false, 3, NULL, 16);


--
-- Data for Name: ouvrage; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (1, 'Histoires_tout_petits.jpg', 'Un recueil d"histoires merveilleuses pour nos tout petits', 'Les 30 plus belles histoires pour les tout-petits');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (2, 'Histoires_2ans.jpg', 'Un recueil d"histoires merveilleuses pour les enfants de 2 ans', 'Les plus belles histoires pour les enfants de 2 ans');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (3, 'Histoires_3ans.jpg', 'Un recueil d"histoires merveilleuses pour les enfants de 3 ans', 'Les plus belles histoires pour les enfants de 3 ans');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (4, 'Histoires_4ans.jpg', 'Un recueil d"histoires merveilleuses pour les enfants de 4 ans', 'Les plus belles histoires pour les enfants de 4 ans');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (5, 'Histoires_5ans.jpg', 'Un recueil d"histoires merveilleuses pour les enfants de 5 ans', 'Les plus belles histoires pour les enfants de 5 ans');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (6, 'Histoires_6ans.jpg', 'Un recueil d"histoires merveilleuses pour les enfants de 6 ans', 'Les plus belles histoires pour les enfants de 6 ans');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (7, 'Histoires_fees_princesses.jpg', 'Un recueil d"histoires merveilleuses pleines de fées, de princesses mais aussi de sorcières et de dragons', 'Les plus belles histoires de fées et de princesses');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (8, 'Histoires_Motordu.jpg', 'Une compilation des meilleures aventures du Prince de Motordu', 'Les plus belles histoires du Prince de Motordu');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (9, 'Petite_princesse.jpg', 'Une compilation des meilleures histoires d"une petite Princesse facétieuse', 'Je veux une histoire !');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (10, 'Histoires_ecole_maternelle.jpg', 'Un recueil d"histoires merveilleuses pour les enfants en école maternelle', 'Les plus belles histoires pour l"école maternelle');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (11, 'Histoires_noel.jpg', 'Un recueil d"histoires de noël merveilleuses', 'Les 25 plus belles histoires de noël');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (12, 'Histoires_soir.jpg', 'Un recueil d"histoires merveilleuses à lire au coucher pour aider les enfants à faire de beaux rêves', 'Les 20 plus belles histoires à lire le soir');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (13, 'Lettres_moulin.jpg', 'Un recueil de nouvelles champêtres d"Alphonse Daudet', 'Les lettres de mon moulin');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (14, 'Communaute_anneau.jpg', 'Une saga fantastique au pays du Gondor', 'Le seigneur des anneaux : la communauté de l"anneau');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (15, 'Deux_tours.jpg', 'La suite des aventures de Fredon', 'Le seigneur des anneaux : les deux tours');
INSERT INTO public.ouvrage (id, image, resume, titre) VALUES (16, 'Retour_roi.jpg', 'La fin des aventures de Fredon dans le Mordor', 'Le seigneur des anneaux : le retour du Roi');


--
-- Data for Name: ouvrage_reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (1, false, NULL, '2020-05-15', NULL, 4, 2);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (2, false, NULL, '2020-05-11', NULL, 5, 2);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (3, false, NULL, '2020-05-12', NULL, 1, 2);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (4, false, NULL, '2020-05-13', NULL, 2, 2);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (5, false, NULL, '2020-05-20', NULL, 4, 7);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (6, false, NULL, '2020-05-17', NULL, 2, 7);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (7, true, '2020-06-01', '2020-05-20', '2020-06-03', 4, 8);
INSERT INTO public.reservation (id, alerte, date_alerte, date_reservation, date_retrait_max, emprunteur_id, ouvrage_id) VALUES (8, false, NULL, '2020-05-25', NULL, 1, 8);


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role (id, role) VALUES (1, 'Administrateur');
INSERT INTO public.role (id, role) VALUES (2, 'Abonne');
INSERT INTO public.role (id, role) VALUES (3, 'Visiteur');
INSERT INTO public.role (id, role) VALUES (4, 'Employe');


--
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (1, 'ADMIN@OC.COM', 'ADMIN', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', 'ADMIN', 1);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (2, 'USER@OC.COM', 'USER', '$2a$11$fwbi72Ko53c6Dvd87f7d7u7hkiFQ5dGnS2SO8JYdiMO41olj78EQi', 'USER', 3);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (3, 'MEMBRE@OC.COM', 'MEMBRE', '$2a$11$yel.cNbeeJNTEa7czcLHHup4wIJVQLqay293LmAewxQZgSV05KSAK', 'MEMBRE', 2);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (4, 'GLESCROEL@HOTMAIL.COM', 'LESCROEL', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', 'Gregory', 1);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (5, 'DUPONT@OC.COM', 'DUPONT', '$2a$11$poeYMQ8pqjKSk1GqvidW2./pYd6kVly0iVrwKB8uWd/YBJocoUOci', 'RENÉ', 3);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (6, 'DUBOIS@OC.COM', 'DUBOIS', '$2a$11$2LLBNGe2bzp1YUsAA7T/RuXmzK7ascvDammvpgsN10bUwOH3LQi7O', 'ANDRÉE', 3);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (7, 'SMITH@OC.COM', 'SMITH', '$2a$11$doPqn9Ex3T9zKrjm3R09m.LnjbeEMNGKWwwR2ElBcevmvaPi9uQ32', 'JOHN', 3);
INSERT INTO public.utilisateur (id, email, nom, password, prenom, role_id) VALUES (8, 'GREGORY.LESCROEL@AXA.FR', 'LESCROEL', '$2a$11$NWK4enrvaXbcLu0Hf4eVIe3cdNk82VO6BvfNx7UMBeHYGJjQcFMQi', 'Gregory', 1);
