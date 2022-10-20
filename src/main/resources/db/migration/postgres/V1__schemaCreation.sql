DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS employees;



CREATE TABLE locations
(
    location_id    SERIAL PRIMARY KEY,
    street_address CHARACTER VARYING(40),
    postal_code    CHARACTER VARYING(12),
    city           CHARACTER VARYING(30) NOT NULL,
    state_province CHARACTER VARYING(25)
);

CREATE TABLE departments
(
    department_id   SERIAL PRIMARY KEY,
    department_name CHARACTER VARYING(30) NOT NULL,
    location_id     INTEGER,
    FOREIGN KEY (location_id) REFERENCES locations (location_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE jobs
(
    job_id     SERIAL PRIMARY KEY,
    job_title  CHARACTER VARYING(35) NOT NULL,
    min_salary NUMERIC(8, 2),
    max_salary NUMERIC(8, 2)
);

CREATE TABLE employees
(
    employee_id   SERIAL PRIMARY KEY,
    first_name    CHARACTER VARYING(20),
    last_name     CHARACTER VARYING(25)  NOT NULL,
    email         CHARACTER VARYING(100) NOT NULL,
    phone_number  CHARACTER VARYING(20),
    hire_date     DATE                   NOT NULL,
    job_id        INTEGER                NOT NULL,
    salary        NUMERIC(8, 2)          NOT NULL,
    department_id INTEGER,
    FOREIGN KEY (job_id) REFERENCES jobs (job_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments (department_id) ON UPDATE CASCADE ON DELETE CASCADE
);