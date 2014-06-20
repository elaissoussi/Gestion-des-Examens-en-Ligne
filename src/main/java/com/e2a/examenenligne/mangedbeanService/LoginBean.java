package com.e2a.examenenligne.mangedbeanService;



import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@ManagedBean
@ApplicationScoped
public class LoginBean  implements Serializable{
	
		private String login ; 
		private String motdepasse;
		
		
		public String getLogin() {
			return login;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public String getMotdepasse() {
			return motdepasse;
		}
		public void setMotdepasse(String motdepasse) {
			this.motdepasse = motdepasse;
		}
		
		
		 /**
	     * Listen for button clicks on the #{loginController.login} action,
	     * validates the username and password entered by the user and navigates to
	     * the appropriate page.
	     *
	     * @param actionEvent
	     */
	    public void login(ActionEvent actionEvent) {

	        FacesContext context = FacesContext.getCurrentInstance();
	        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	        
	        String navigateString = "";
            // Checks if username and password are valid if not throws a ServletException
            try {
				request.login(login,motdepasse);
				  if (request.isUserInRole("Administrateur")) {
		                navigateString = "/Administrateur/index.xhtml";
		            } else if (request.isUserInRole("Expert")) {
		                navigateString = "/Expert/index.xhtml";
		            } else if (request.isUserInRole("Utilisateur")) {
		                navigateString = "/Utilisateur/index.xhtml";
		            }
				  
				  context.getExternalContext().redirect(request.getContextPath() + navigateString);
				  
			} catch (ServletException e) {
				
				context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Vérifier les données entrées"));  

			}
            //  navigates to the appropriate page
             catch (IOException e) {
 				context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Vérifier les données entrées"));  
				e.printStackTrace();
			}
            
          
	   
       

	    }
	    
	    //*************Logout *************************
	    
	    public void logout() {
	    	
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        if (session != null) {
	            session.invalidate();
	        }
	        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/acceuil.xhtml?faces-redirect=true");
	    }

}
