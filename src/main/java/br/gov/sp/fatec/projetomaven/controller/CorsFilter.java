package br.gov.sp.fatec.projetomaven.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  private ServletContext context;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    this.context.log("CORSFilter HTTP Request: " + request.getMethod());

    // Autoriza
    HttpServletResponse response = (HttpServletResponse) res;
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE, PATCH");
    response.addHeader("Access-Control-Allow-Headers", "*");

    // Para requisicoes com metodo OPTIONS
    if (request.getMethod().equals("OPTIONS")) {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      return;
    }

    // Passa adiante demais requisicoes
    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {
    // Aqui pode-se desalocar qualquer recurso
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    this.context = config.getServletContext();
    this.context.log("Filtro inicializado!");
  }

}