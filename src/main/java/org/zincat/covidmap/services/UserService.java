package org.zincat.covidmap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zincat.covidmap.exceptions.CustomException;
import org.zincat.covidmap.models.ZincatUser;
import org.zincat.covidmap.repositories.UserRepository;
import org.zincat.covidmap.util.JwtUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(String username, String password)
    {
        try
        {
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(username, password)));
            return jwtUtil.generateToken(username, userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e)
        {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String register(ZincatUser user)
    {
        if (!userRepository.existsByUsername(user.getUsername()))
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtUtil.generateToken(user.getUsername(), user.getRoles());
        } else
        {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username)
    {
        userRepository.deleteByUsername(username);
    }

    public String update(long searchKey, ZincatUser user)
    {
        if (userRepository.existsById(searchKey))
        {
            ZincatUser tempUser = userRepository.findById(searchKey).get();
            tempUser.setUsername(user.getUsername());
            tempUser.setPassword(passwordEncoder.encode(user.getPassword()));
            tempUser.setRoles(tempUser.getRoles());
            userRepository.save(tempUser);
            return jwtUtil.generateToken(user.getUsername(), user.getRoles());
        } else
        {
            throw new CustomException("Username not found", HttpStatus.NOT_FOUND);
        }
    }

    public ZincatUser search(String username)
    {
        ZincatUser user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public String refresh(String username)
    {
        return jwtUtil.generateToken(username, userRepository.findByUsername(username).getRoles());
    }


}
