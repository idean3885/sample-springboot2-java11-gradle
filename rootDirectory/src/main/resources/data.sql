INSERT INTO members
(id, email, password, role_list, status, created_by, created_date, last_modified_by, last_modified_date)
values
(1, 'admin@email.com', '$2a$12$xwU7pCwIVFxVPdDiSFLEqeIKi0p9S9gXwPnpO2cNDV/eFfOXKEEJ2', 'ROLE_ADMIN', 1, 1, NOW(6), 1, NOW(6)),
(2, 'user@email.com', '$2a$12$/RD2VTBGqM6wD0KNOsNiTuuGDpLWl2xTXhmuScThnW7Xjc0Vc1J3u', 'ROLE_USER', 1, 1, NOW(6), 1, NOW(6));