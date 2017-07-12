package com.codecool.security;

import com.codecool.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser myUser = userService.getUserByUserName(userName).get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>() {{
            add(new SimpleGrantedAuthority("USER"));
        }};
        return new org.springframework.security.core.userdetails.User(myUser.getUserName(), myUser.getPassword(), grantedAuthorities);
    }
}
