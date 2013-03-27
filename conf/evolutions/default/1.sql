# --- !Ups

CREATE TABLE configuration
(  
  key character varying(100) NOT NULL,
  value character varying(300) NOT NULL,
  CONSTRAINT configuration_pkey PRIMARY KEY (key)
);

CREATE TABLE campaign
(
  id serial NOT NULL,
  name character varying(150) NOT NULL,
  filename character varying(100),
  subject character varying(200),
  body TEXT,
  num_emails integer default 0,
  num_queued integer default 0,
  num_sended integer default 0,
  num_error integer default 0,
  queued timestamp without time zone,
  sended timestamp without time zone,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone NOT NULL,
  CONSTRAINT campaign_pkey PRIMARY KEY (id)
);

CREATE TABLE campaign_stats
(
  id serial NOT NULL,
  campaign_id integer NOT NULL,  
  CONSTRAINT campaign_stats_pkey PRIMARY KEY (id),
  CONSTRAINT campaign_stats_campaign_id_fkey FOREIGN KEY (campaign_id)
      REFERENCES campaign (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE subscriber
(
  id serial NOT NULL,
  email character varying(150) NOT NULL,
  state character varying(20) NOT NULL,
  created timestamp without time zone NOT NULL,
  CONSTRAINT subscriber_pkey PRIMARY KEY (id),
  CONSTRAINT mail_key UNIQUE (email)
);

CREATE TABLE email
(
  campaign_id integer NOT NULL,
  subscriber_id integer NOT NULL,
  queued timestamp without time zone NOT NULL,
  sended timestamp without time zone,  
  unsuscribe_token character varying(20) NOT NULL,
  CONSTRAINT email_pkey PRIMARY KEY (campaign_id, subscriber_id),
  CONSTRAINT email_campaign_id_fkey FOREIGN KEY (campaign_id)
      REFERENCES campaign (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT email_subscriber_id_fkey FOREIGN KEY (subscriber_id)
      REFERENCES subscriber (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE log
(
  id serial NOT NULL,
  value TEXT NOT NULL,
  created timestamp without time zone NOT NULL,
  CONSTRAINT log_pkey PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE configuration;
DROP TABLE email;
DROP TABLE campaign_stats;
DROP TABLE campaign;
DROP TABLE subscriber;
DROP TABLE log;
