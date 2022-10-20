DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `roles`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(60) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `email`    varchar(120) DEFAULT NULL,
    `name`     varchar(60)  DEFAULT NULL,
    `password` varchar(10)  DEFAULT NULL,
    `username` varchar(20)  DEFAULT NULL,
    `active` boolean DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
    UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles`
(
    `user_id`  bigint NOT NULL,
    `roles_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `roles_id`),
    KEY `FKdbv8tdyltxa1qjmfnj9oboxse` (`roles_id`),
    CONSTRAINT `FKdbv8tdyltxa1qjmfnj9oboxse` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;