package com.higor.userauth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registrar(String email, String nome, String senha) {
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        String senhaCriptografada = passwordEncoder.encode(senha);
        User user = new User(email, nome, senhaCriptografada, "USER");
        return repository.save(user);
    }

    public String login(String email, String senha) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));

        if (!passwordEncoder.matches(senha, user.getSenha())) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        return jwtUtil.gerarToken(user.getEmail(), user.getRole());
    }

    public Optional<User> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> listarTodos() {
        return repository.findAll();
    }

    public Optional<User> atualizar(Long id, String nome, String email) {
        return repository.findById(id).map(user -> {
            user.setNome(nome);
            user.setEmail(email);
            return repository.save(user);
        });
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}