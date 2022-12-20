CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `entity_status` int(11) NOT NULL,
  `username` varchar(50),
  `password_hash` varchar(200),
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `user_type` int(11) NOT NULL,
  `city`  varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `image_url` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
);

INSERT INTO `user` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `version`, `entity_status`, `username`, `password_hash`, `first_name`, `last_name`, `email`, `user_type`, `city`, `birth_date`, `phone`) VALUES ('1', 'administrator', '2022-05-18 14:05:18', 'administrator', '2022-05-18 14:05:18', '0', '0', 'dragan', '$2a$10$GaN8q88iQwU2y2BD0PdRi.Swu1Dz2NFhKcY8CP4RZkbYslYUUe9ai', 'Dragan', 'Kalakovic', 'dragan@email.com', '0', 'Mosorin', '1997-02-10', '+123123123');
