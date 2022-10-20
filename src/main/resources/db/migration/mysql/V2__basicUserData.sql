INSERT INTO `roles` (`id`, `name`)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO `users` (`id`, `email`, `name`, `password`, `username`, `active`)
VALUES (1, 'alan.turing@turingmail.com', 'alan turing', '123456', 'aturing', true),
       (2, 'matias.humilde@turingmail.com', 'matias humilde', 'qwerty', 'mhumilde', true),
       (3, 'charles.babbage@turingmail.com', 'charles babbage', 'babas', 'cbabbage', false);

INSERT INTO `user_roles` (`user_id`, `roles_id`)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 2);
