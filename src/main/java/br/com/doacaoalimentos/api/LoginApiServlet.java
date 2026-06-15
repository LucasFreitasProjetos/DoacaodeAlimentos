package br.com.doacaoalimentos.api;

import br.com.doacaoalimentos.dao.UsuarioDAO;
import br.com.doacaoalimentos.model.Usuario;
import br.com.doacaoalimentos.security.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/login")
public class LoginApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepareJsonResponse(response);

        try {
            // Ler corpo da requisição
            String body = JsonUtil.readBody(request);

            // Extrair email e senha
            String email = JsonUtil.getString(body, "email");
            String senha = JsonUtil.getString(body, "senha");

            // Validar campos
            if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("{\"erro\": \"Email e senha são obrigatórios\"}");
                return;
            }

            // Autenticar usuário
            Usuario usuario = UsuarioDAO.autenticar(email, senha);
            if (usuario == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("{\"erro\": \"Email ou senha inválidos\"}");
                return;
            }

            // Gerar token JWT
            String token = JwtUtil.generateToken(email);

            // Retornar token
            response.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = "{\"token\": \"" + token + "\", \"email\": \"" + email + "\"}";
            response.getWriter().println(jsonResponse);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\": \"Erro ao processar login\"}");
            e.printStackTrace();
        }
    }

    /**
     * Configura headers para resposta JSON com CORS
     */
    private void prepareJsonResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
