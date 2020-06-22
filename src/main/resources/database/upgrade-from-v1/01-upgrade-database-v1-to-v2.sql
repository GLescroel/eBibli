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
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);

--
-- Name: ouvrage_reservations uk_jq2ne1n3rgjsrpcdlmqvm0v53; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ouvrage_reservations
    ADD CONSTRAINT uk_jq2ne1n3rgjsrpcdlmqvm0v53 UNIQUE (reservations_id);

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


ALTER TABLE public.livre
ADD column reserve
DEFAULT false WITH VALUES

ALTER TABLE public.livre
ADD column next_emprunteur_id
DEFAULT null WITH VALUES