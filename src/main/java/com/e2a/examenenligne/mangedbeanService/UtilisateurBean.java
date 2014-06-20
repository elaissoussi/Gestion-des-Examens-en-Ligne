package com.e2a.examenenligne.mangedbeanService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.LazyDataModel;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceStatut;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceUtilisateur;
import com.e2a.examenenligne.entities.Statut;
import com.e2a.examenenligne.entities.UtilisateurExpert;

@ManagedBean
@ViewScoped
public class UtilisateurBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	ServiceUtilisateur serviceutilisateur;

	@EJB
	ServiceStatut servicestatut;

	// Lazy loading user list
	private LazyDataModel<UtilisateurExpert> lazyModel;
	// Creating new user
	private UtilisateurExpert newUser = new UtilisateurExpert();
	// Selected user that will be updated
	private UtilisateurExpert selectedUser = new UtilisateurExpert();
	// Available role list
	private List<Statut> statutList;
	private List<Statut> statutLists;

	// for inscription user
	private UtilisateurExpert utilisateur = new UtilisateurExpert();

	/**
	 * Initializing Data Access Service for LazyUserDataModel class role list
	 * for UserContoller class
	 */
	@PostConstruct
	public void init() {
		setLazyModel(new LazyUtilisateurDataModel(serviceutilisateur));
		setStatutList(servicestatut.getListStatut());
		setStatutLists(servicestatut.getListStatuts());
	}

	// **********************************CRUD***************************

	public void doCreateUser() {
		serviceutilisateur.createUtilisateur(newUser);
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateUser(ActionEvent actionEvent) {
		serviceutilisateur.update(selectedUser);
	}

	public void doDelete(ActionEvent actionEvent) {
		serviceutilisateur.delete(selectedUser.getId());
	}



	// ******************************** Create from Inscription	// *******************************************

	public void createUtilisateurBean() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String navigateString = null;
	
		if (serviceutilisateur.createUtilisateur(utilisateur) != null) {

			try {
				request.login(utilisateur.getLogin(),
						utilisateur.getMotDePasse());
				if (request.isUserInRole("Administrateur")) {
					navigateString = "/Administrateur/index.xhtml";
				} else if (request.isUserInRole("Expert")) {
					navigateString = "/Expert/index.xhtml";
				} else if (request.isUserInRole("Utilisateur")) {
					navigateString = "/Utilisateur/index.xhtml";
				}

				context.getExternalContext().redirect(request.getContextPath() + navigateString);

			} catch (ServletException e) {
				e.printStackTrace();
				// user already existe --------------
			}
			// navigates to the appropriate page
			catch (IOException e) {
				context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Ce login déja utililsé "));  
				e.printStackTrace();
			}
		}

	}
	
	// **********************Getters and Setters
	// ************************************

	public UtilisateurExpert getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(UtilisateurExpert utilisateur) {
		this.utilisateur = utilisateur;
	}

	public UtilisateurExpert getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UtilisateurExpert selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UtilisateurExpert getNewUser() {
		return newUser;
	}

	public void setNewUser(UtilisateurExpert newUser) {
		this.newUser = newUser;
	}
	public LazyDataModel<UtilisateurExpert> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<UtilisateurExpert> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public List<Statut> getStatutList() {
		return statutList;
	}

	public void setStatutList(List<Statut> statutList) {
		this.statutList = statutList;
	}

	public List<Statut> getStatutLists() {
		return statutLists;
	}

	public void setStatutLists(List<Statut> statutLists) {
		this.statutLists = statutLists;
	}


}
