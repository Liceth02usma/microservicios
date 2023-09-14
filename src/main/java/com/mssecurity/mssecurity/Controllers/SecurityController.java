package com.mssecurity.mssecurity.Controllers;


import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/public/security")
public class SecurityController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private JwtService myJWTService; // obligar a spring a crear esto con las fabricas osea no se necesita
    // new JwtService

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String respuesta = "";
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser != null && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))) {
            // generar token
            String token = myJWTService.generateToken(actualUser);
            respuesta = token;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return respuesta;
    }
}


