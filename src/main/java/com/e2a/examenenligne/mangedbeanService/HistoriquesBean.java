package com.e2a.examenenligne.mangedbeanService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.LazyDataModel;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceHisExamen;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceUtilisateur;
import com.e2a.examenenligne.entities.Choix;
import com.e2a.examenenligne.entities.HisExam;
import com.e2a.examenenligne.entities.Question;
import com.e2a.examenenligne.entities.QuestionMultChoix;
import com.e2a.examenenligne.entities.Type;
import com.e2a.examenenligne.entities.UtilisateurExpert;

@ManagedBean
@SessionScoped
public class HistoriquesBean implements Serializable {
	@EJB
	ServiceHisExamen seviceHE;

	@EJB
	ServiceUtilisateur serviceutilisateur;

	// l'examen selectionner
	private HisExam selectedHExamen = new HisExam();

	// Lazy loading user list
	private LazyDataModel<HisExam> lazyModel;

	private UtilisateurExpert expert = new UtilisateurExpert();

	private List<Choix> listChoix;

	public List<Choix> getListChoix() {
		getBonneChoix();
		return listChoix;
	}

	public void setListChoix(List<Choix> listChoix) {
		this.listChoix = listChoix;
	}

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		setExpert(serviceutilisateur.getExpertByLogin(request
				.getUserPrincipal().getName()));
		lazyModel = new LazyHistoriqueExamensDataModel(seviceHE, request
				.getUserPrincipal().getName());
	}

	// ********** Bonnes Choixs************************************

	public void getBonneChoix() {
		listChoix = new ArrayList<Choix>();
		for (Question q : selectedHExamen.getExamen().getQuestions()) {
			for (Choix c : ((QuestionMultChoix) q).getChoix()) {
				if (c.isBonReponse())
					listChoix.add(c);
			}
		}
	}

	// ****************************** Methodes de rediriction	// *****************************

	public String afficherDetails() {

		if (selectedHExamen.getExamen().getType() == (Type.ChoixMult))
			return "/Utilisateur/hisPasseMC.xhtml";
		else
			return "/Utilisateur/hisPasseQD.xhtml";

	}

	// **************************GEtters & setters	// ******************************
	public LazyDataModel<HisExam> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<HisExam> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public HisExam getSelectedHExamen() {
		return selectedHExamen;
	}

	public void setSelectedHExamen(HisExam selectedHExamen) {
		this.selectedHExamen = selectedHExamen;
	}

	public UtilisateurExpert getExpert() {
		return expert;
	}

	public void setExpert(UtilisateurExpert expert) {
		this.expert = expert;
	}

}
