-- Insert sample data into the category table using the sequence for IDs
INSERT INTO category (id, description, name) VALUES
                                                 (nextval('category_seq'), 'Electronics and gadgets', 'Electronics'),
                                                 (nextval('category_seq'), 'Books and stationery', 'Books'),
                                                 (nextval('category_seq'), 'Home and kitchen appliances', 'Home & Kitchen');

-- Insert sample data into the product table using the sequence for IDs
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 'Smartphone with 128GB storage', 'Smartphone', 50, 699.99, (SELECT id FROM category WHERE name = 'Electronics')),
                                                                                        (nextval('product_seq'), 'High-quality headphones', 'Headphones', 100, 199.99, (SELECT id FROM category WHERE name = 'Electronics')),
                                                                                        (nextval('product_seq'), 'Fiction book by popular author', 'Fiction Book', 200, 14.99, (SELECT id FROM category WHERE name = 'Books')),
                                                                                        (nextval('product_seq'), 'Non-fiction self-help book', 'Self-Help Book', 150, 19.99, (SELECT id FROM category WHERE name = 'Books')),
                                                                                        (nextval('product_seq'), 'Blender with multiple settings', 'Blender', 75, 89.99, (SELECT id FROM category WHERE name = 'Home & Kitchen')),
                                                                                        (nextval('product_seq'), 'Stainless steel cookware set', 'Cookware Set', 40, 129.99, (SELECT id FROM category WHERE name = 'Home & Kitchen'));