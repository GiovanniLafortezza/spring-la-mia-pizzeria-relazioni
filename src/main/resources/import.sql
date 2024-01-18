INSERT INTO pizzas (name, description, image, price) VALUES('Margherita', 'Pomodoro, mozzarella di bufala e basilico', 'https://www.scattidigusto.it/wp-content/uploads/2018/03/pizza-margherita-originale-Scatti-di-Gusto.jpg', 5.50);
INSERT INTO pizzas (name, description, image, price) VALUES('Wurstel e Patatine', 'Mozzarella, wurstel e patatine', 'https://www.scattidigusto.it/wp-content/uploads/2015/11/pizza-wurstel-e-patatine.jpg', 6.50);

INSERT INTO offers (pizza_id, title, start_date, end_date, price) VALUES(1, 'Offerta Weekend', '2024-01-23', '2024-01-24', 4.50);
INSERT INTO offers (pizza_id, title, start_date, end_date, price) VALUES(1, 'Offerta Sera', '2024-01-18', '2024-01-22', 5.00);

INSERT INTO ingredients (name) VALUE ('Mozzarella');
INSERT INTO ingredients (name) VALUE ('Pomodoro');
INSERT INTO ingredients (name) VALUE ('Basilico');
INSERT INTO ingredients (name) VALUE ('Pepe');
INSERT INTO ingredients (name) VALUE ('Patatine');
INSERT INTO ingredients (name) VALUE ('Wurstel');

