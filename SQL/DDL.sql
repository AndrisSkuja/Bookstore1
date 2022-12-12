CREATE SEQUENCE order_id
    increment 1
    minvalue 1
    no maxvalue;

CREATE TABLE auto_add_book 
(
    auto_add_id character varying,
    isbn character varying,
    publisher_name character varying,
    amount bigint,
    primary key (auto_add_id)
);

CREATE TABLE basket_contents
(
    user_id character varying,
    isbn character varying,
    amount bigint,
    PRIMARY KEY (user_id, isbn)
);

CREATE TABLE books
(
    isbn character varying NOT NULL,
    author character varying,
    name character varying,
    genre character varying,
    publisher character varying,
    number_of_pages bigint,
    price bigint,
    sales_percentage bigint,
    rating bigint,
    min_number bigint,
    number_of_books_sold bigint,
    amount_stored bigint,
    prev_month_sold bigint,
    PRIMARY KEY (isbn)
);

CREATE TABLE "order"
(
    order_number bigint NOT NULL,
    billing_info character varying,
    shipping_info character varying,
    arrival_date character varying,
    order_date character varying,
    tracking_status character varying,
    user_id character varying,
    PRIMARY KEY (order_number)
);

CREATE TABLE order_contents
(
    user_id character varying NOT NULL,
    isbn character varying NOT NULL,
    amount bigint,
    PRIMARY KEY (user_id, isbn)
);

CREATE TABLE phone_number
(
    publisher_name character varying NOT NULL,
    phone_number character varying,
    contact_name character varying,
    PRIMARY KEY (publisher_name)
);

CREATE TABLE publishers
(
    publisher_name character varying NOT NULL,
    address character varying,
    email_address character varying,
    banking_info character varying,
    PRIMARY KEY (publisher_name)
);

CREATE TABLE rating
(
    user_id character varying NOT NULL,
    "ISBN" character varying NOT NULL,
    rating bigint,
    PRIMARY KEY (user_id, "ISBN")
);

CREATE TABLE users
(
    user_id character varying NOT NULL,
    billing_info character varying,
    shipping_info character varying,
    user_type boolean,
    password character varying,
    PRIMARY KEY (user_id)
);

CREATE VIEW v_sales_per_genre AS
    SELECT price, number_of_books_sold, genre
    FROM books;

CREATE VIEW v_sales_per_author AS
    SELECT price, number_of_books_sold, author
    FROM books;

CREATE VIEW v_sales_vs_expenditures AS
    SELECT price, sales_percentage, number_of_books_sold
    FROM books;