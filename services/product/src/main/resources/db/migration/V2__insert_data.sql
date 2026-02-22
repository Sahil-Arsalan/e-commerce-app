INSERT INTO category (id, name, description) VALUES (1, 'Furniture', 'Home and office furniture');
INSERT INTO category (id, name, description) VALUES (2, 'Sports', 'Sports and fitness items');
INSERT INTO category (id, name, description) VALUES (3, 'Groceries', 'Daily grocery items');
INSERT INTO category (id, name, description) VALUES (4, 'Beauty', 'Beauty and personal care');

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Office Chair', 'Comfortable office chair', 15, 4999.00, 1);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Football', 'Leather football', 30, 799.00, 2);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Rice Bag', '10kg basmati rice', 50, 899.00, 3);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Face Cream', 'Moisturizing cream', 40, 299.00, 4);