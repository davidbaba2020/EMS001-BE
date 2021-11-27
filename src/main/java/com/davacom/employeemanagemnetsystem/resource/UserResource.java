package com.davacom.employeemanagemnetsystem.resource;

import com.davacom.employeemanagemnetsystem.exception.models.ExceptionHandling;
import com.davacom.employeemanagemnetsystem.exception.models.UsernameExistException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/", "/user"})
public class  UserResource extends ExceptionHandling {
    @GetMapping("/home")
    public String showUser() throws UsernameExistException {
//        return "This application is working fine and return........";
        throw new UsernameExistException("User not found exception try later or soon ");
    }
}
