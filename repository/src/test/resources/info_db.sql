CREATE TABLE IF NOT EXISTS authors (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authors_pkey PRIMARY KEY (id),
    CONSTRAINT uk_9mhkwvnfaarcalo4noabrin5j UNIQUE (name)
)

CREATE TABLE IF NOT EXISTS news (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    content character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created character varying(255) COLLATE pg_catalog."default" NOT NULL,
    modified character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(50) COLLATE pg_catalog."default" NOT NULL,
    authors_id bigint,
    CONSTRAINT news_pkey PRIMARY KEY (id),
    CONSTRAINT uk_9tfgiwqioj4gn86792hj5fgx3 UNIQUE (title),
    CONSTRAINT fkd3ii72d59htt8693kjrxm1km4 FOREIGN KEY (authors_id)
        REFERENCES public.authors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS comments (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    content character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created character varying(255) COLLATE pg_catalog."default" NOT NULL,
    modified character varying(255) COLLATE pg_catalog."default" NOT NULL,
    news_id bigint,
    CONSTRAINT comments_pkey PRIMARY KEY (id),
    CONSTRAINT fkqx89vg0vuof2ninmn5x5eqau2 FOREIGN KEY (news_id)
        REFERENCES public.news (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS tags (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tags_pkey PRIMARY KEY (id),
    CONSTRAINT uk_t48xdq560gs3gap9g7jg36kgc UNIQUE (name)
)

CREATE TABLE IF NOT EXISTS news_tags(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    news_id bigint,
    tags_id bigint,
    CONSTRAINT news_tags_pkey PRIMARY KEY (id),
    CONSTRAINT fk30dfp9jff63kipjrue2s4931i FOREIGN KEY (tags_id)
        REFERENCES public.tags (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fki06sdgpsvq2oxtharq5q1rc3x FOREIGN KEY (news_id)
        REFERENCES public.news (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)