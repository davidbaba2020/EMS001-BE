package com.davacom.employeemanagemnetsystem.service.serviceImpl;

import com.davacom.employeemanagemnetsystem.models.User;
import com.davacom.employeemanagemnetsystem.models.UserPrincipal;
import com.davacom.employeemanagemnetsystem.repository.UserRepository;
import com.davacom.employeemanagemnetsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
   
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(userName);
        if(user == null){
             LOGGER.error("User with username: " + userName + " not found");
            throw new UsernameNotFoundException("User with username: " + userName + " not found");
        }else
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info("Returning found user by username:" + userName);
        return userPrincipal;
        }
    }

