
INSERT INTO Customer (username, firstname, lastname, password, email, phone)
VALUES ('NeelamPrasad', 'Neelam', 'Prasad', 'password123','neelam.prasad@gmail.com',637383952),
               ('NanditaSinha', 'Nandita', 'Sinha', 'Ganesh','nandita.sinha@gmail.com',46568942),
               ('AdityaSinha', 'Aditya', 'Sinha', 'letmein','aditya.sinha@gmail.com',32536364),
               ('NareshPrasad', 'Naresh', 'Prasad', 'pass123','naresh.prasad@gmail.com',6767676767),
               ('Nivi123', 'Nivedita', 'Kumar', 'Welcome234','Nivedita.kumar@gmail.com',8901234561),
               ('Mayank', 'Mayank', 'Kumar', 'mypassword','Mayank.Kumar@gmail.com',3253636478);


INSERT INTO Account (id, customer_id, account_Number, balance)
VALUES (1, 1, '100007', 1000.00),
       (2, 2, '100008', 500.50),
       (3, 1, '100001', 1000.00),
       (4, 2, '100002', 1500.50),
       (5, 3, '100003', 5200.50),
       (6, 3, '500001', 5900.50),
       (7, 4, '500002', 100.50),
       (8, 4, '778999', 200.50),
       (9, 5, '782922', 300.50),
       (10, 6, '1425367', 400.50),
       (11, 6, '2424566', 700.50);


 INSERT INTO Transaction (from_account_id, to_account_id, amount, transaction_date,comment)
 VALUES (1, 2, 200.00, '2024-06-20 19:56:04.850771','Payment for services'),
        (2, 1, 100.50, '2024-05-21 20:09:27.334672','Rent for May'),
        (2, 1, 100.50, '2024-05-22 20:09:27.334672','Rent for June'),
        (1, 2, 200.00, '2024-03-23 19:56:04.850771','Food Contri'),
 	    (2, 3, 200.00, '2024-06-24 19:56:04.850771','Payment for services'),
        (3, 1, 100.50, '2024-05-25 20:09:27.334672','Rent for May'),
        (3, 1, 100.50, '2024-05-26 20:09:27.334672','Rent for June'),
        (3, 2, 200.00, '2024-03-27 19:56:04.850771','Food Contri'),
        (4, 3, 200.00, '2024-06-28 19:56:04.850771','Payment for services'),
        (4, 1, 100.50, '2024-05-29 20:09:27.334672','Rent for May'),
        (4, 1, 100.50, '2024-05-30 20:09:27.334672','Rent for June'),
        (4, 2, 200.00, '2024-03-20 19:56:04.850771','Food Contri'),
        (4, 3, 200.00, '2024-06-21 19:56:04.850771','Payment for services'),
        (4, 1, 100.50, '2024-05-22 20:09:27.334672','Rent for May'),
        (4, 1, 100.50, '2024-05-23 20:09:27.334672','Rent for June'),
        (4, 2, 200.00, '2024-03-24 19:56:04.850771','Food Contri'),
        (3, 5, 200.00, '2024-06-25 19:56:04.850771','Payment for services'),
        (3, 4, 100.50, '2024-05-26 20:09:27.334672','Rent for May'),
        (3, 4, 100.50, '2024-05-27 20:09:27.334672','Rent for June'),
        (2, 4, 200.00, '2024-03-28 19:56:04.850771','Food Contri');



