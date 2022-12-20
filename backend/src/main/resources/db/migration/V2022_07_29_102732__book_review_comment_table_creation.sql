CREATE TABLE `book_review_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `entity_status` int(11) NOT NULL,
  `text` varchar(10000) NOT NULL,
  `book_review_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_book_review_id` (`book_review_id`),
  KEY `fk4_user_id` (`user_id`),
  CONSTRAINT `fk1_book_review_id` FOREIGN KEY (`book_review_id`) REFERENCES `book_review` (`id`),
  CONSTRAINT `fk4_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `book_review_comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `entity_status` int(11) NOT NULL,
  `book_review_comment_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_book_review_comment_id` (`book_review_comment_id`),
  KEY `fk6_user_id` (`user_id`),
  CONSTRAINT `fk1_book_review_comment_id` FOREIGN KEY (`book_review_comment_id`) REFERENCES `book_review_comment` (`id`),
  CONSTRAINT `fk6_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);