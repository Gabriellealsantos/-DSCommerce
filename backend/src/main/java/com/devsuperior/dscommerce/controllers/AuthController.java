package com.devsuperior.dscommerce.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "none")
@Tag(name = "Autenticação", description = "Endpoint para autenticação e obtenção de token JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Login padrão",
                                    summary = "Exemplo de login",
                                    value = """
                    {
                      "username": "alex@gmail.com",
                      "password": "123456"
                    }
                """
                            )
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Parâmetros para a requisição ao /oauth/token
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        // Esses parâmetros podem continuar sendo enviados, mas também adicionamos o header Basic Auth

        // Configurar headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Adiciona o header de Basic Authentication para as credenciais do cliente
        String clientCredentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes(StandardCharsets.UTF_8));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap> response = restTemplate.postForEntity(accessTokenUri, request, LinkedHashMap.class);

        return ResponseEntity.ok(response.getBody());
    }
}
