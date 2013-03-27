# --- !Ups

INSERT INTO configuration VALUES('app.url', 'http://localhost:9000');
INSERT INTO configuration VALUES('repository.upload.path', '/tmp');
INSERT INTO configuration VALUES('aws.message.per.second', '3');
INSERT INTO configuration VALUES('aws.simulator.success.email', 'success@simulator.amazonses.com');
INSERT INTO configuration VALUES('aws.simulator.bounce.email', 'bounce@simulator.amazonses.com');
INSERT INTO configuration VALUES('aws.simulator.ooto.email', 'ooto@simulator.amazonses.com');
INSERT INTO configuration VALUES('aws.simulator.complaint.email', 'complaint@simulator.amazonses.com');
INSERT INTO configuration VALUES('aws.simulator.blacklist.email', 'blacklist@simulator.amazonses.com');
INSERT INTO configuration VALUES('mail.transport.protocol', 'smtp');
INSERT INTO configuration VALUES('mail.smtp.localhost', 'email-smtp.us-east-1.amazonaws.com');
INSERT INTO configuration VALUES('mail.smtp.host', 'email-smtp.us-east-1.amazonaws.com');
INSERT INTO configuration VALUES('mail.smtp.port', '465');
INSERT INTO configuration VALUES('mail.smtp.starttls.enable', 'true');
INSERT INTO configuration VALUES('mail.smtp.socketFactory.port', '465');
INSERT INTO configuration VALUES('mail.smtp.socketFactory.class', 'javax.net.ssl.SSLSocketFactory');
INSERT INTO configuration VALUES('mail.smtp.socketFactory.fallback', 'false');
INSERT INTO configuration VALUES('mail.debug', 'false');
INSERT INTO configuration VALUES('mail.smtp.auth', 'true');

INSERT INTO configuration VALUES('mail.email', 'UNDEFINED');
INSERT INTO configuration VALUES('mail.smtp.mail.sender', 'UNDEFINED');
INSERT INTO configuration VALUES('mail.user', 'UNDEFINED');											   
INSERT INTO configuration VALUES('mail.password', 'UNDEFINED');												   

# --- !Downs
delete from configuration where key = 'repository-upload';
delete from configuration where key = 'aws-message-per-second';
delete from configuration where key = 'aws-simulator-success-email';
delete from configuration where key = 'aws-simulator-bounce-email';
delete from configuration where key = 'aws-simulator-ooto-email';
delete from configuration where key = 'aws-simulator-complaint-email';
delete from configuration where key = 'aws-simulator-blacklist-email';
delete from configuration where key = 'mail.transport.protocol';
delete from configuration where key = 'mail.smtp.localhost';
delete from configuration where key = 'mail.smtp.host';
delete from configuration where key = 'mail.smtp.port';
delete from configuration where key = 'mail.smtp.starttls.enable';
delete from configuration where key = 'mail.smtp.socketFactory.port';
delete from configuration where key = 'mail.smtp.socketFactory.class';
delete from configuration where key = 'mail.smtp.socketFactory.fallback';
delete from configuration where key = 'mail.email';
delete from configuration where key = 'mail.smtp.mail.sender';
delete from configuration where key = 'mail.smtp.auth';
delete from configuration where key = 'mail.user';
delete from configuration where key = 'mail.password');
delete from configuration where key = 'mail.debug';