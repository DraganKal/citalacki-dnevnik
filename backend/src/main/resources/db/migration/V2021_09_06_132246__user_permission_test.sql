INSERT INTO `user_group` (`id`, `created_by`, `created_date`, `version`, `name`) VALUES
(1, 'admin', '2022-04-01 10:00:00', 1, 'ADMINISTRATOR'),
(2, 'admin', '2022-04-01 10:00:00', 1, 'USER');

INSERT INTO `user_permission`
(`id`, `created_by`, `created_date`, `version`, `view_name`, `user_view_role`, `view_right`) VALUES
(1, 'admin', '2021-06-08 10:00:00', 1, 'book', 0, 0),
(2, 'admin', '2021-06-08 10:00:00', 1, 'book', 0, 1),
(3, 'admin', '2021-06-08 10:00:00', 1, 'book', 1, 0),
(4, 'admin', '2021-06-08 10:00:00', 1, 'book', 1, 1);


INSERT INTO `user_group_user_permission`
(`user_group_id`, `user_permission_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),

(2, 2),
(2, 4);


INSERT INTO `user_user_group` (`user_id`, `user_group_id`) VALUES ('1', '1');

