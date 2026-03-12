package com.higor.userauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        System.out.println(">>> REGISTER CHAMADO: " + body);
        try {
            User user = service.registrar(
                    body.get("email"),
                    body.get("nome"),
                    body.get("senha")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("id", user.getId(), "email", user.getEmail(), "nome", user.getNome())
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        System.out.println(">>> LOGIN CHAMADO: " + body);
        try {
            String token = service.login(body.get("email"), body.get("senha"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}