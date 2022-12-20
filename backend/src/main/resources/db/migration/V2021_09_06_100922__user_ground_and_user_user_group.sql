CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_user_group` (
  `user_id` bigint(20) NOT NULL,
  `user_group_id` bigint(20) NOT NULL,
  KEY `fk_user_user_group_user_group_id` (`user_group_id`),
  KEY `fk_user_id` (`user_id`),
  PRIMARY KEY (`user_id`, `user_group_id`),
  CONSTRAINT `fk_user_user_group_user_group_id` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `user_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `view_name` varchar(50) NOT NULL,
  `user_view_role` int(11) NOT NULL,
  `view_right` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_group_user_permission` (
  `user_group_id` bigint(20) NOT NULL,
  `user_permission_id` bigint(20) NOT NULL,
  KEY `fk_user_group_permission_user_group_id` (`user_group_id`),
  KEY `fk_user_group_permission_user_permission_id` (`user_permission_id`),
  PRIMARY KEY (`user_group_id`, `user_permission_id`),
  CONSTRAINT `fk_user_group_permission_user_group_id` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_group_permission_user_permission_id` FOREIGN KEY (`user_permission_id`) REFERENCES `user_permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

