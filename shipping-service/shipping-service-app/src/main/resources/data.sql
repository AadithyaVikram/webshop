INSERT INTO users (id, email) VALUES
    (1, 'user@user.com');

INSERT INTO PACKAGES (id, user_id, status, expected_shipping_date) VALUES
    (1001, 1, 'Delivered', '2024-02-18'),
    (1002, 1, 'In Transit', '2024-03-18');
