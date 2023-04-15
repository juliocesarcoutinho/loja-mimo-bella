package br.com.mimobella.securitys;


import br.com.mimobella.configs.ApplicationContextLoad;
import br.com.mimobella.models.Usuario;
import br.com.mimobella.repositories.UsuarioRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.BeansException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*Cria a autenticação e retorna a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticationService {

    /*Token de validade 11 dias */
    private static final long EXPIRATION_TIME = 959990000;

    /*Chave Secreta*/
    private static final String SECRET = "q1w2e3r4";

    /*Prefixo do token tipo Bearer*/
    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /*Gera o token e da a reposta para o cliente do JWT*/
    public void addAutentication(HttpServletResponse response, String usename) throws IOException {
        /*Montagem do token*/
        String JWT = Jwts.builder() /*Chama o Gerador de token*/
                .setSubject(usename) /*Adiciona o usuario*/
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*tempo de expiração*/
                .signWith(SignatureAlgorithm.HS512, SECRET) /*Assinatura junto com o Secret, vai gerar todas as informações do token*/
                .compact();

        String token = TOKEN_PREFIX + " " + JWT;

        /* Da a resposta para a tela (API, javaScript etc..) */
        response.addHeader(HEADER_STRING, token);
        liberacaoCors(response);

        /*Usado para teste no Postman*/
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

    }

    /*Metodo que retorna o usuario validado pelo token caso não seja validado retorna null*/
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(HEADER_STRING);

        try {
            if (token != null) {
                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim(); /*Pega o token e retira o prefixo*/

                /*Faz a Liberação do token e obtem o usuario (Extrai de dentro do token o usuario*/
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody().getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad.getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getSenha(),
                                usuario.getAuthorities()
                        );
                    }
                }

            }

        } catch (SignatureException e) {
            response.getWriter().write("Token está Invalido ");
        }catch (ExpiredJwtException e){
            response.getWriter().write("Token esta Expirado, efetue o login novamente");
        }finally {
            liberacaoCors(response);
        }

        return null;
    }

    /*Fazendo liberação contra erro de COrs no navegador*/
    private void liberacaoCors(HttpServletResponse response) {

        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }

    }

}

