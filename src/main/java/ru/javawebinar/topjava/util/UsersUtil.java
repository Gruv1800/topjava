package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsersUtil {
    public static final Set<User> USERS = Stream.of(
            new User(0, "User1", "user1@email.com", "pass1", Role.ROLE_USER),
            new User(1, "User2", "user2@email.com", "pass2", Role.ROLE_USER),
            new User(2, "Admin1", "admin1@email.com", "admin1", Role.ROLE_ADMIN)
    ).collect(Collectors.toSet());
}
