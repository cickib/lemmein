package com.codecool.security;


import com.codecool.model.MyUser;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<MyUser> getUserById(int id);

    Optional<MyUser> getUserByUserName(String userName);

    Collection<MyUser> getAllUsers();

    void create(MyUser user);
}
