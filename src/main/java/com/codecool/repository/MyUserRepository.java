package com.codecool.repository;

import com.codecool.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUserName(String userName);
}
