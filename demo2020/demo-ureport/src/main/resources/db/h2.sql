SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS report_file;
CREATE TABLE report_file (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(400) NOT NULL,
  content CLOB,
  create_time TIMESTAMP,
  update_time TIMESTAMP,

  CONSTRAINT pk_t_report_file PRIMARY KEY (id)
);



