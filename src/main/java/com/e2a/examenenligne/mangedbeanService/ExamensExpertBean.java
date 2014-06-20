package com.e2a.examenenligne.mangedbeanService;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.LazyDataModel;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceExamen;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceUtilisateur;
import com.e2a.examenenligne.entities.Categorie;
import com.e2a.examenenligne.entities.Examen;
import com.e2a.examenenligne.entities.Type;
import com.e2a.examenenligne.entities.UtilisateurExpert;

@ManagedBean
@SessionScoped
public class ExamensExpertBean {

	@EJB
	ServiceExamen serviceexamen;

	@EJB
	ServiceUtilisateur serviceutilisateur;

	// l'examen selectionner
	private Examen selectedExamen;

	// Lazy loading user list
	private LazyDataModel<Examen> lazyModel;

	// L'expert en session
	FacesContext context1 = FacesContext.getCurrentInstance();

	private UtilisateurExpert expert = new UtilisateurExpert();

	private Categorie[] listCategories;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// expert=serviceutilisateur.getExpertByLogin(request.getUserPrincipal().getName());
		System.out.println(" init de ExamensExpertBean avec login est: "
				+ request.getUserPrincipal().getName());
		lazyModel = new LazyExmenExpertDataModel(serviceexamen, request
				.getUserPrincipal().getName());
		selectedExamen = new Examen();
		setListCategories(Categorie.class.getEnumConstants());
	}

	// ************************ Getters and Setters
	// ********************************

	public LazyDataModel<Examen> getLazyModel() {
		return lazyModel;
	}

	public Categorie[] getListCategories() {
		return listCategories;
	}

	public void setListCategories(Categorie[] listCategories) {
		this.listCategories = listCategories;
	}

	public void setLazyModel(LazyDataModel<Examen> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Examen getSelectedExamen() {
		return selectedExamen;
	}

	public void setSelectedExamen(Examen selectedExamen) {
		this.selectedExamen = selectedExamen;
	}

	// ****************************** Methodes de	// Redirection*************************

	public String  afficherExamen() {
		
		if (selectedExamen.getType() == (Type.ChoixMult))
			return "/Expert/modifierExamMC.xhtml";
		else
			return "/Expert/modifierExamQD.xhtml";

	}
}
