CREATE TABLE  IF NOT EXISTS Employee (
                                         id SERIAL PRIMARY KEY,
                                         title VARCHAR(50) NOT NULL,                    -- Title of the employee (e.g., Mr., Ms.)
                          full_name VARCHAR(250) NOT NULL,      -- Full name of the employee (cannot be empty)
                          gender VARCHAR(10),                      -- Gender of the employee (e.g., Male, Female)
                          age INTEGER CHECK (age > 0),          -- Age of the employee (must be positive)
                          email VARCHAR(255) UNIQUE,            -- Email of the employee (must be unique and valid)
                          phone_number INT,                  -- Phone number (using BIGINT to accommodate various formats)
                          hire_date TIMESTAMP,                  -- Hire date (stored as a timestamp)
                          department VARCHAR(100)

);
