package com.mytaxi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.UserMapper;
import com.mytaxi.datatransferobject.UserDTO;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.user.UserService;

/**
 * All operations with a user will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/users")
public class UserController
{

    private final  UserService userService;

    @Autowired
    public UserController(final UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/")
    private UserDTO findUsers(@RequestParam String username) throws EntityNotFoundException
    {
        return UserMapper.makeUserDTO(userService.findUserByName(username));
    }
    
    @PostMapping("/logouts")
    public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (auth != null){    
        	auth.setAuthenticated(false);
        	username = ((User)auth.getPrincipal()).getUsername().toString();
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.setStatus(401);
        //Adding username in redirect url to force chrome to prompt userdetails
        response.addHeader("Location", "http://localhost:8080/" + username + "@example");
    }
    
}
