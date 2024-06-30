
INSERT INTO Customer (username, firstname, lastname, password)
VALUES ('john_doe', 'John', 'Doe', 'password123'),
               ('NanditaSinha', 'Nandita', 'Sinha', 'Ganesh'),
                ('AdityaSinha', 'Aditya', 'Sinha', 'letmein');



INSERT INTO Account (id, customer_id, account_Number, balance)
VALUES (1, 1, 'ACC001', 1000.00),
       (2, 2, 'ACC002', 500.50);

INSERT INTO Transaction (id, from_account_id, to_account_id, amount, transaction_Date)
VALUES (1, 1, 2, 200.00, CURRENT_TIMESTAMP),
       (2, 2, 1, 100.50, CURRENT_TIMESTAMP);