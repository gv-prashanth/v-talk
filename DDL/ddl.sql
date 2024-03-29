-- public.chats definition

-- Drop table

-- DROP TABLE public.chats;

CREATE TABLE public.chats (
	id serial NOT NULL,
	message text NOT NULL,
	receiver varchar NOT NULL,
	sender varchar NOT NULL,
	created_on timestamptz NOT NULL,
	CONSTRAINT chats_pk PRIMARY KEY (id)
);


-- public.logininfo definition

-- Drop table

-- DROP TABLE public.logininfo;

CREATE TABLE public.logininfo (
	id serial NOT NULL,
	ipaddress varchar NULL,
	useragent varchar NULL,
	geoinfo varchar NULL,
	logintime timestamptz NOT NULL,
	osinfo varchar NULL,
	sender varchar NOT NULL
);


-- public.userinfo definition

-- Drop table

-- DROP TABLE public.userinfo;

CREATE TABLE public.userinfo (
	username varchar NOT NULL,
	phone numeric NULL,
	CONSTRAINT userinfo_pk PRIMARY KEY (username)
);
ALTER TABLE public.userinfo ADD last_notification timestamptz NULL;


-- public.attachments definition

-- Drop table

-- DROP TABLE public.attachments;

CREATE TABLE public.attachments (
	id serial NOT NULL,
	chat_id int4 NOT NULL,
	"data" text NULL,
	attachment_meta text NULL,
	CONSTRAINT attachments_pk PRIMARY KEY (id),
	CONSTRAINT attachments_fk FOREIGN KEY (chat_id) REFERENCES public.chats(id)
);