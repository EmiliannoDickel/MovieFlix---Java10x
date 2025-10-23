package com.dickel.movieflix.service;

import com.dickel.movieflix.entity.User;
import com.dickel.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save (User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password)); //pego a senha do usuario e codifico ela antes de salvar no banco
        return userRepository.save(user);
    }
}
