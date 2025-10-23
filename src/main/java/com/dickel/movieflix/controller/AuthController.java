package com.dickel.movieflix.controller;

import com.dickel.movieflix.config.TokenService;
import com.dickel.movieflix.entity.User;
import com.dickel.movieflix.mapper.UserMapper;
import com.dickel.movieflix.request.UserRequest;
import com.dickel.movieflix.response.LoginRequest;
import com.dickel.movieflix.response.LoginResponse;
import com.dickel.movieflix.response.UserResponse;
import com.dickel.movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("movieflix/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
   public ResponseEntity<UserResponse> save (@RequestBody UserRequest request){
        User userSaved = UserMapper.toUserRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userService.save(userSaved)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){ //aqui esta a logica de autenticação
        UsernamePasswordAuthenticationToken userAndPassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPassword); //usa o auth manager la do security config para autenticar

        User user = (User)authentication.getPrincipal();

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));

    }
}
