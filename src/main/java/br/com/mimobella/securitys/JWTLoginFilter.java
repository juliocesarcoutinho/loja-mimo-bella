package br.com.mimobella.securitys;


import br.com.mimobella.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /*Configuração de Gerenciador de Autenticação*/
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        /*Obriga a autenticar a Url*/
        super(new AntPathRequestMatcher(url));

        /*Gerenciador de Autenticação*/
        setAuthenticationManager(authenticationManager);
    }

    /*Retorna o Usuario Processado na autenticação*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        /*Obtem o Usuario*/
        Usuario user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), Usuario.class);

        /*Retorna o User com login e senha  */
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    public JWTLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public JWTLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public JWTLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher
            , AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        new JWTTokenAutenticationService().addAutentication(response, authResult.getName());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof BadCredentialsException) {
            response.getWriter().write("Usuario ou senha não conferem");
        } else {
            response.getWriter().write("Falha ao efetuar o Login " + failed.getMessage());
        }

//        super.unsuccessfulAuthentication(request, response, failed);
    }
}