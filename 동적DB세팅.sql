CREATE TABLE blog (
	blog_id INT AUTO_INCREMENT PRIMARY KEY,
	writer VARCHAR(16) NOT NULL,
    blog_title VARCHAR(200) NOT NULL,
    blog_content VARCHAR(4000) NOT NULL,
    published_at DATETIME DEFAULT now(),
    updated_at DATETIME DEFAULT now(),
    blog_count INT DEFAULT 0
);