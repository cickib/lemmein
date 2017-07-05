package com.codecool.security;

import com.codecool.model.MyUser;
import com.codecool.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public Optional<MyUser> getUserById(int id) {
        return Optional.ofNullable(myUserRepository.findOne(id));
    }

    @Override
    public Optional<MyUser> getUserByUserName(String userName) {
        return Optional.ofNullable(myUserRepository.findByUserName(userName));
    }

    @Override
    public Collection<MyUser> getAllUsers() {
        return myUserRepository.findAll(new Sort("regDate"));
    }

    @Override
    public void create(MyUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRegDate(new Date());
        myUserRepository.save(user);
    }
}
