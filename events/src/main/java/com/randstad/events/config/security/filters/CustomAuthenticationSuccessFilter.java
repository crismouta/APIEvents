package com.randstad.events.config.security.filters;

import com.randstad.events.domain.models.User;
import com.randstad.events.infra.repositories.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
@WebFilter
public class CustomAuthenticationSuccessFilter extends OncePerRequestFilter {
    private final IUserRepository userRepository;

    public CustomAuthenticationSuccessFilter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtro personalizado ejecutado");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        RequestEntity<Void> requestEntity = getVoidRequestEntity(authentication);

        // Realiza la solicitud al punto final UserInfo
        ResponseEntity<Map<String, Object>> responseEntity = new RestTemplate().exchange(requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});

        // Extrae el correo electrónico del usuario de la respuesta
        String userEmail = (String) responseEntity.getBody().get("email");
        System.out.println(userEmail);

        saveAuthenticatedUserInDataBase(userEmail);

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private void saveAuthenticatedUserInDataBase(String userEmail) {
        User existingUser = userRepository.findByEmail(userEmail);
        System.out.println(existingUser);

        if (existingUser != null) {
            System.out.println("el usuario ya existe en la DB");
        } else {
            User newUser = new User();
            System.out.println(newUser);
            newUser.setEmail(userEmail);
            userRepository.save(newUser);
        }
    }

    private static RequestEntity<Void> getVoidRequestEntity(Authentication authentication) {
        Jwt jwtToken = (Jwt) authentication.getPrincipal();
        String accessTokenValue = jwtToken.getTokenValue();

        // Configura la solicitud HTTP para obtener UserInfo
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessTokenValue);

        RequestEntity<Void> requestEntity = null;
        try {
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI("https://dev-7h7e4za1e50fpaao.eu.auth0.com/userinfo"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return requestEntity;
    }

}
