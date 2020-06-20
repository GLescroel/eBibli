--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "eBibli";
--
-- Name: eBibli; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "eBibli" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';


ALTER DATABASE "eBibli" OWNER TO postgres;

\connect "eBibli"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bibliotheque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bibliotheque (
    id integer NOT NULL,
    nom character varying(255)
);


ALTER TABLE public.bibliotheque OWNER TO postgres;

--
-- Name: emprunt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emprunt (
    id integer NOT NULL,
    date_emprunt date,
    date_retour date,
    date_retour_prevu date,
    en_retard boolean,
    encours boolean,
    prolonge boolean,
    emprunteur_id integer,
    livre_id integer
);


ALTER TABLE public.emprunt OWNER TO postgres;

--
-- Name: livre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livre (
    id integer NOT NULL,
    disponible boolean,
    reserve boolean,
    bibliotheque_id integer NOT NULL,
    next_emprunteur_id integer,
    ouvrage_id integer NOT NULL
);


ALTER TABLE public.livre OWNER TO postgres;

--
-- Name: ouvrage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ouvrage (
    id integer NOT NULL,
    image character varying(255),
    resume character varying(255),
    titre character varying(255)
);


ALTER TABLE public.ouvrage OWNER TO postgres;

--
-- Name: ouvrage_reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ouvrage_reservations (
    ouvrage_id integer NOT NULL,
    reservations_id integer NOT NULL
);


ALTER TABLE public.ouvrage_reservations OWNER TO postgres;

--
-- Name: reservation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservation (
    id integer NOT NULL,
    alerte boolean,
    date_alerte date,
    date_reservation date,
    date_retrait_max date,
    emprunteur_id integer,
    ouvrage_id integer
);


ALTER TABLE public.reservation OWNER TO postgres;

--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id integer NOT NULL,
    role character varying(255)
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utilisateur (
    id integer NOT NULL,
    email character varying(255),
    nom character varying(255),
    password character varying(255),
    prenom character varying(255),
    role_id integer
);


ALTER TABLE public.utilisateur OWNER TO postgres;

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


--
-- Name: bibliotheque bibliotheque_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bibliotheque
    ADD CONSTRAINT bibliotheque_pkey PRIMARY KEY (id);


--
-- Name: emprunt emprunt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_pkey PRIMARY KEY (id);


--
-- Name: livre livre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT livre_pkey PRIMARY KEY (id);


--
-- Name: ouvrage ouvrage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ouvrage
    ADD CONSTRAINT ouvrage_pkey PRIMARY KEY (id);


--
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: ouvrage_reservations uk_jq2ne1n3rgjsrpcdlmqvm0v53; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ouvrage_reservations
    ADD CONSTRAINT uk_jq2ne1n3rgjsrpcdlmqvm0v53 UNIQUE (reservations_id);


--
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id);


--
-- Name: emprunt fk2am7gg2u639gs1ygwtmtsgpil; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT fk2am7gg2u639gs1ygwtmtsgpil FOREIGN KEY (emprunteur_id) REFERENCES public.utilisateur(id);


--
-- Name: livre fk2qdbpmsyb4bj5hgsyus3kmf5y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT fk2qdbpmsyb4bj5hgsyus3kmf5y FOREIGN KEY (next_emprunteur_id) REFERENCES public.utilisateur(id);


--
-- Name: utilisateur fkaqe8xtajee4k0wlqrvh2pso4l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT fkaqe8xtajee4k0wlqrvh2pso4l FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: reservation fkhp1vxvi16qsbg9wewy27ingtb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkhp1vxvi16qsbg9wewy27ingtb FOREIGN KEY (ouvrage_id) REFERENCES public.ouvrage(id);


--
-- Name: reservation fkhxtc5ekhlsp6mi0eetbhbp8e4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkhxtc5ekhlsp6mi0eetbhbp8e4 FOREIGN KEY (emprunteur_id) REFERENCES public.utilisateur(id);


--
-- Name: livre fkidtj3xupvdmkb5bwq8xi455ks; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT fkidtj3xupvdmkb5bwq8xi455ks FOREIGN KEY (ouvrage_id) REFERENCES public.ouvrage(id);


--
-- Name: emprunt fkjnn7ll8vl64xhmb6779svt7c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT fkjnn7ll8vl64xhmb6779svt7c FOREIGN KEY (livre_id) REFERENCES public.livre(id);


--
-- Name: livre fkr1d20hy7k1mp2fphrnl87mycb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT fkr1d20hy7k1mp2fphrnl87mycb FOREIGN KEY (bibliotheque_id) REFERENCES public.bibliotheque(id);


--
-- PostgreSQL database dump complete
--

