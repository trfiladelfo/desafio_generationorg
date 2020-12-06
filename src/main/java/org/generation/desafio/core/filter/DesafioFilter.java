package org.generation.desafio.core.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Classe para utilizada para filtrar todas as requisição para o servidor
 */
@Component
@Order(1)
public class DesafioFilter implements Filter {

    /**
     * @see Filter
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {

            chain.doFilter(request, response);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


}
