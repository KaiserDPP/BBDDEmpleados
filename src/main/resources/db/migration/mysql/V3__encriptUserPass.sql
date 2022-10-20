ALTER TABLE `users`
    MODIFY `password` varchar(120);

UPDATE `users`
SET `password` = '$2a$10$i0znIdKH8Gxxam19AaGtEOuaao4z7SFxhNo0mTH7ssneJ60Spp11.'
WHERE `username` = 'jopejope';