-- Account password: test
INSERT INTO ACCOUNT (id, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, username) VALUES (1, true, true, true, true, '$2a$04$sgQR2Kco2Ux8XvzaIVSRpuaGNTjAMHrLZVc/1zy2egFNyQQ2c2Usu', 'test');
INSERT INTO ACCOUNT (id, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, username) VALUES (2, true, true, true, true, '$2a$04$sgQR2Kco2Ux8XvzaIVSRpuaGNTjAMHrLZVc/1zy2egFNyQQ2c2Usu', 'test2');
INSERT INTO ACCOUNT (id, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, username) VALUES (3, true, true, true, true, '$2a$04$sgQR2Kco2Ux8XvzaIVSRpuaGNTjAMHrLZVc/1zy2egFNyQQ2c2Usu', 'admin');

INSERT INTO AUTHORITY (ID, AUTHORITY) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (ID, AUTHORITY) VALUES (2, 'ROLE_ADMIN');

INSERT INTO ACCOUNT_AUTHORITIES (ACCOUNT_ID, AUTHORITIES_ID) VALUES (1, 1);
INSERT INTO ACCOUNT_AUTHORITIES (ACCOUNT_ID, AUTHORITIES_ID) VALUES (2, 1);
INSERT INTO ACCOUNT_AUTHORITIES (ACCOUNT_ID, AUTHORITIES_ID) VALUES (3, 2);

INSERT INTO TODO (ID, NAME, DESCRIPTION, SECRET, OWNER_ID) VALUES (1, 'Sample TODO', 'This is a sample public todo', false, 1);
INSERT INTO TODO (ID, NAME, DESCRIPTION, SECRET, OWNER_ID) VALUES (2, 'Secret TODO', 'This is a sample secret todo', true, 2);
