
INSERT INTO Customer (username, firstname, lastname, password, email, phone)
VALUES ('NeelamPrasad', 'Neelam', 'Prasad', '$2a$10$daP6ZD2vpemeRopJtf9msO/2npdETJ6MV.8lJT6lM9rfGy9W0E5mq','neelam.prasad@gmail.com',637383952),
              --Password@123
               ('NanditaSinha', 'Nandita', 'Sinha', '$2a$10$Dp38jMsUr19Rz/gy9eYSaej19klBNp8obdUQ.KaxS/Ln4GEB8Vk2G','nandita.sinha@gmail.com',46568942),
              --Ganesh@123
               ('AdityaSinha', 'Aditya', 'Sinha', '$2a$10$poQhuKVlElsSGrQcpnnvSeQXkKpEP45/JKbq/43pqIxV8DrbnE5sG','aditya.sinha@gmail.com',32536364),
               --Letmein@1
               ('NareshPrasad', 'Naresh', 'Prasad', '$2a$10$zIwi81SRMWIga/Wq9BNKQeO.jA5xdfrboT7zGbGUXBHdpw650RrDm','naresh.prasad@gmail.com',6767676767),
               --Pass123@
               ('Nivi123', 'Nivedita', 'Kumar', '$2a$10$ayxU6XZeIptj.Iu2yVv/4ePjup4yt0zZ726Th3cvTxgblArvai/Ji','Nivedita.kumar@gmail.com',8901234561),
               --Welcome@234
               ('Mayank', 'Mayank', 'Kumar', '$2a$10$LZjThsxCQ71G2ddz1d24cuG2SCAsizwlD9saQEesVp5ZKOIt4s4cW','Mayank.Kumar@gmail.com',3253636478);
               --Mypassword@1


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



