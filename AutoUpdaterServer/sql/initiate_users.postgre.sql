CREATE TABLE users (
  id serial,
  username varchar(60) NOT NULL,
  password varchar(60) NOT NULL,
  name varchar(120) NOT NULL,
  admin boolean NOT NULL DEFAULT '0',
  packageAdmin boolean NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE (username)
);

INSERT INTO users (username, password, name, admin, packageAdmin) VALUES ('root','$2a$10$kHyIXv/A1SSzSIQ3GvsQVeK5yyhYqMfkDRunfoq19B49YooI7xdHO','Main Repo Administrator',true,true);