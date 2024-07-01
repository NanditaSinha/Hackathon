
INSERT INTO Customer (username, firstname, lastname, password, email, phone)
VALUES ('NeelamPrasad', 'Neelam', 'Prasad', 'password123','neelam.prasad@gmail.com',637383952),
               ('NanditaSinha', 'Nandita', 'Sinha', 'Ganesh','nandita.sinha@gmail.com',46568942),
                ('AdityaSinha', 'Aditya', 'Sinha', 'letmein','aditya.sinha@gmail.com',32536364);



INSERT INTO Account (id, customer_id, account_Number, balance)
VALUES (1, 1, 'ACC001', 1000.00),
       (2, 2, 'ACC002', 500.50);

INSERT INTO Transaction (from_account_id, to_account_id, amount, transaction_date,comment)
VALUES (1, 2, 200.00, '2024-06-30 19:56:04.850771','Payment for services'),
       (2, 1, 100.50, '2024-05-30 20:09:27.334672','Rent for May'),
       (2, 1, 100.50, CURRENT_TIMESTAMP,'Rent for June'),
       (1, 2, 200.00, '2024-03-30 19:56:04.850771','Food Contri');