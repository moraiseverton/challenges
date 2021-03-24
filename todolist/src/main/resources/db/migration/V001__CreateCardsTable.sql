CREATE TABLE IF NOT EXISTS cards (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description LONGVARCHAR,
  current_status VARCHAR(40) NOT NULL,
  due_date TIMESTAMP NOT NULL
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;