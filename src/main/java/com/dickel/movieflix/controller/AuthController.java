package com.dickel.movieflix.controller;

import com.dickel.movieflix.config.TokenService;
import com.dickel.movieflix.entity.User;
import com.dickel.movieflix.exception.UsernameOrPassordInvalidException;
import com.dickel.movieflix.mapper.UserMapper;
import com.dickel.movieflix.request.UserRequest;
import com.dickel.movieflix.response.LoginRequest;
import com.dickel.movieflix.response.LoginResponse;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.response.UserResponse;
import com.dickel.movieflix.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("movieflix/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e autenticação de usuários")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Endpoint para registrar um novo usuário no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode =  "400", description = "Requisição inválida", content = @Content())
   public ResponseEntity<UserResponse> save (@Valid @RequestBody UserRequest request){
        User userSaved = UserMapper.toUserRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userService.save(userSaved)));
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Endpoint para autenticar um usuário e obter um token JWT.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode =  "401", description = "Usuário ou senha inválidos", content = @Content())
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){ //aqui esta a logica de autenticação
        try {
            UsernamePasswordAuthenticationToken userAndPassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authentication = authenticationManager.authenticate(userAndPassword); //usa o auth manager la do security config para autenticar

            User user = (User) authentication.getPrincipal();

            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            throw new UsernameOrPassordInvalidException("Usuário ou senha inválidos");
        }
    }
}
