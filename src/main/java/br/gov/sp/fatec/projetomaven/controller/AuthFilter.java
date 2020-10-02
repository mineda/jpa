package br.gov.sp.fatec.projetomaven.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {

  private ServletContext context;
  private String username = "admin";
  private String password = "password_dificil";
  private String realm = "PROTECTED";

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    this.context.log("Filtro de autenticacao acessado!");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    // Verifica se tem o header Authorization
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      // Divide o conteúdo do header por espacos
      StringTokenizer st = new StringTokenizer(authHeader);
      // Se possui conteúdo
      if (st.hasMoreTokens()) {
        String basic = st.nextToken();
        // Verifica se possui o prefixo Basic
        if (basic.equalsIgnoreCase("Basic")) {
          try {
            // Extrai as credenciais (Base64)
            String credentials = new String(Base64.getDecoder().decode(st.nextToken()));
            this.context.log("Credentials: " + credentials);
            // Separa as credenciais em usuario e senha
            Integer p = credentials.indexOf(":");
            if (p != -1) {
              String _username = credentials.substring(0, p).trim();
              String _password = credentials.substring(p + 1).trim();
              // Se nao bate com configuracao retorna erro
              if (!username.equals(_username) || !password.equals(_password)) {
                unauthorized(response, "Bad credentials");
                return;
              }
              // Prossegue com a requisicao
              chain.doFilter(req, res);
            } else {
              unauthorized(response, "Invalid authentication token");
            }
          } catch (UnsupportedEncodingException e) {
            throw new Error("Couldn't retrieve authentication", e);
          }
        }
      }
    } else {
      unauthorized(response);
    }
  }

  @Override
  public void destroy() {
    // Aqui pode-se desalocar qualquer recurso

  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    this.context = config.getServletContext();
    this.context.log("Filtro inicializado!");
    this.username = config.getInitParameter("username");
    this.password = config.getInitParameter("password");
    String paramRealm = config.getInitParameter("realm");
    if (paramRealm != null && paramRealm.length() > 0) {
      this.realm = paramRealm;
    }
  }

  private void unauthorized(HttpServletResponse response, String message) throws IOException {
    response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
    response.sendError(401, message);
  }

  private void unauthorized(HttpServletResponse response) throws IOException {
    unauthorized(response, "Unauthorized");
  }

}