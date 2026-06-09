package br.com.doacaoalimentos.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/*")
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Permitir CORS preflight
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Permitir acesso ao endpoint /api/login sem token
        String path = httpRequest.getRequestURI();
        if (path.contains("/api/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Extrair token do header Authorization
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println("{\"erro\": \"Acesso não autorizado - Token não fornecido\"}");
            return;
        }

        // Validar formato "Bearer TOKEN"
        if (!authHeader.startsWith("Bearer ")) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println("{\"erro\": \"Acesso não autorizado - Formato inválido\"}");
            return;
        }

        // Extrair token
        String token = authHeader.substring(7);

        // Validar token
        String email = JwtUtil.validateToken(token);
        if (email == null) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println("{\"erro\": \"Acesso não autorizado - Token inválido ou expirado\"}");
            return;
        }

        // Token válido - continuar
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
