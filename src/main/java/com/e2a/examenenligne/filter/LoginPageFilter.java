package com.e2a.examenenligne.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  To prevent user from going back to Login page if the user already logged in
 * @author monsif El aissoussi 
 */


public class LoginPageFilter implements Filter{
   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,   FilterChain filterChain) throws IOException, ServletException{
       HttpServletRequest request = (HttpServletRequest) servletRequest;
       HttpServletResponse response = (HttpServletResponse) servletResponse;

       if(request.getUserPrincipal() != null){ //If user is already authenticated
                String navigateString = "";
                System.out.println("ok");
          	  if (request.isUserInRole("Administrateur")) {
	                navigateString = "/Administrateur/index.xhtml";
	            } else if (request.isUserInRole("Expert")) {
	                navigateString = "/Expert/index.xhtml";
	            } else if (request.isUserInRole("Utilisateur")) {
	                navigateString = "/Utilisateur/index.xhtml";
	            }
                response.sendRedirect(request.getContextPath()+navigateString);
       } else{
           filterChain.doFilter(servletRequest, servletResponse);
       }
   }

   @Override
   public void destroy(){
   }
   
   @Override
   public void init(FilterConfig filterConfig) throws ServletException{
   }
}