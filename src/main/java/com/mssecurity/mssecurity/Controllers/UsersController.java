package com.mssecurity.mssecurity.Controllers;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/users")
public class UsersController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private RoleRepository theRoleRepository;


    @Autowired
    private JwtService myJWTService; // obligar a spring a crear esto con las fabricas osea no se necesita
    // new JwtService
    @GetMapping("")
    public List<User> index() {
        return this.theUserRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser) {
        newUser.setPassword(this.convertirSHA256(newUser.getPassword()));

        return this.theUserRepository.save(newUser);
    }

    @GetMapping("{id}")
    public User show(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        return theUser;
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User theNewUser) {
        User theActualUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theActualUser != null) {
            theActualUser.setName(theNewUser.getName());
            theActualUser.setEmail(theNewUser.getEmail());
            theActualUser.setPassword(this.convertirSHA256(theActualUser.getPassword()));
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theUser != null) {
            this.theUserRepository.delete(theUser);
        }
    }

    @PutMapping("{user_id}/role/{role_id}")//ruta que ide el usuario y el id del usuario
    public User matchUserRole(@PathVariable String user_id, @PathVariable String role_id) {
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);
        Role theActualRole = this.theRoleRepository.findById(role_id).orElse(null);

        if(theActualUser != null && theActualRole != null){
            theActualUser.setRole((theActualRole));
            return this.theUserRepository.save(theActualUser);
        }else{
            return  null;
        }
    }

    @PutMapping ("{user_id}/role")//ruta que ide el usuario y el id del usuario
    public User unMatchUserRole(@PathVariable String user_id) {
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);

        if(theActualUser != null){
            theActualUser.setRole((null));
            return this.theUserRepository.save(theActualUser);
        }else{
            return  null;
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


}


