CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `entity_status` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `author` varchar(200) NOT NULL,
  `published_year` integer DEFAULT NULL,
  `description` varchar(2000) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `book_genre` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `entity_status` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `book_book_genre` (
  `book_id` bigint(20) NOT NULL,
  `book_genre_id` bigint(20) NOT NULL,
  KEY `fk_book_id` (`book_id`),
  KEY `fk_book_genre_id` (`book_genre_id`),
  PRIMARY KEY (`book_id`, `book_genre_id`),
  CONSTRAINT `fk_book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_genre_id` FOREIGN KEY (`book_genre_id`) REFERENCES `book_genre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('1', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Domaća knjiga');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('2', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Roman');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('3', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Opšta interesovanja');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('4', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Umetnost');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('5', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Jezik i književnost');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('6', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Stručna literatura');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('7', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Biografija');
INSERT INTO `book_genre` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`) VALUES ('8', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Strip');


INSERT INTO `book` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`, `author`, `published_year`, `description`, `image_url`) VALUES ('1', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Mali Princ', 'Dragulj Draguljovic', '2022', 'Opis malog princa', 'https://www.knjizare-vulkan.rs/files/thumbs/files/images/slike_proizvoda/thumbs_600/251953_600_600px.jpg');
INSERT INTO `book` (`id`, `created_by`, `created_date`, `version`, `entity_status`, `name`, `author`, `published_year`, `description`, `image_url`) VALUES ('2', 'administrator', '2001-01-20 22:00:00', '0', '0', 'Na Drini cuprija', 'Ivo Andric', '2022', 'Opis cuprije', 'https://www.knjizare-vulkan.rs/files/thumbs/files/images/slike_proizvoda/thumbs_600/247847_600_600px.jpg');

INSERT INTO `book_book_genre` (`book_id`, `book_genre_id`) VALUES ('1', '1');
INSERT INTO `book_book_genre` (`book_id`, `book_genre_id`) VALUES ('2', '1');
