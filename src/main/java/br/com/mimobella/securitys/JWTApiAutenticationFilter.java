package br.com.mimobella.securitys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Filtro onde todas as requisições serão capturadas para autenticar*/
public class JWTApiAutenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            /*Estabelece a autenticação do usuario */
            Authentication authentication = new JWTTokenAutenticationService()
                    .getAuthentication((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);

            /*processo de autenticação para o spring security*/
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            e.printStackTrace();
            servletResponse.getWriter().write("Ocorreu um erro no sistema, Contate o administrador: " + "\n" + e.getMessage());
        }

    }
}