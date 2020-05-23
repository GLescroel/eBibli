
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
    date_emprunt timestamp without time zone,
    date_retour timestamp without time zone,
    date_retour_prevu timestamp without time zone,
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
    bibliotheque_id integer NOT NULL,
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
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


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
-- Name: utilisateur fkaqe8xtajee4k0wlqrvh2pso4l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT fkaqe8xtajee4k0wlqrvh2pso4l FOREIGN KEY (role_id) REFERENCES public.role(id);


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

