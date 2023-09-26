package pe.edu.upc.food_hunger_tf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.food_hunger_tf.entities.Roles;
import pe.edu.upc.food_hunger_tf.entities.Usuario;
import pe.edu.upc.food_hunger_tf.jwt.JwtService;
import pe.edu.upc.food_hunger_tf.repositories.IUsuarioRepository;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUsuarioRepository iUsuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = iUsuarioRepository.findByNombreU(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
