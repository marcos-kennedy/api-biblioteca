package com.projetos.biblioteca.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.projetos.biblioteca.infra.security.GeradorToken;
import com.projetos.biblioteca.models.user.AutenticacaoDTO;
import com.projetos.biblioteca.models.user.LoginResponseDTO;
import com.projetos.biblioteca.models.user.RegistroUsuarioDTO;
import com.projetos.biblioteca.models.user.Usuario;
import com.projetos.biblioteca.repositorys.UsuarioRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("autenticacao")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GeradorToken geradorToken;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AutenticacaoDTO dados) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        var autenticacao = this.authenticationManager.authenticate(usernamePassword);
        var token = geradorToken.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody RegistroUsuarioDTO dados) {
        if (this.usuarioRepository.findByUsername(dados.username()) != null){
            return ResponseEntity.badRequest().build();
        } else {
            String passwordCriptografado = new BCryptPasswordEncoder().encode(dados.password());
            Usuario usuario = new Usuario(dados.username(), passwordCriptografado, dados.role());
            
            this.usuarioRepository.save(usuario);
            return ResponseEntity.ok().build();
        }
    } 
}
