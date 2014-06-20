package com.e2a.examenenligne.mangedbeanService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.e2a.examenenligne.ejb.serviceDAO.ServiceHisExamen;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceUtilisateur;
import com.e2a.examenenligne.entities.Choix;
import com.e2a.examenenligne.entities.Examen;
import com.e2a.examenenligne.entities.HisExam;
import com.e2a.examenenligne.entities.QuestVF;
import com.e2a.examenenligne.entities.Question;
import com.e2a.examenenligne.entities.QuestionMultChoix;
import com.e2a.examenenligne.entities.ReponseExamMultCh;
import com.e2a.examenenligne.entities.ReponseExamVF;
import com.e2a.examenenligne.entities.ReponseUtilisateur;
import com.e2a.examenenligne.entities.Type;
import com.e2a.examenenligne.entities.Utilisateur;
import com.e2a.examenenligne.entities.UtilisateurClient;
import com.e2a.examenenligne.entities.UtilisateurExpert;
import com.e2a.examenenligne.entities.VraiFaux;

@ManagedBean
@SessionScoped
public class PassageExamenBean implements Serializable {

	// ************* Examen**********
	
	@EJB
	ServiceHisExamen   servicehis ;
	@EJB
	ServiceUtilisateur  serviceUtilisateur;
	private Examen selectedExamen;
	private List<Question> listQuestions;
	private Question selectedQuestion;
	private Iterator<Question> iterator;
	private List<ReponseUtilisateur> reponsesUtilisateur;
	// ********* Historique Examen****
	private HisExam historique;

	//******************************
	private int intervalle ;
	private int duree = 900 ;
	// ******************* QCM*********************************
	private List<Choix> listChoix;
	private ReponseExamMultCh reponseQMC;
	// ********************** reponse QVF**********************
	private ReponseExamVF reponseQD;

	// nombre des question fausses avec score
	private int score = 20;

	// service Timer pour la durée de l'examen

	@PostConstruct
	public void init() {
		reponseQD = new ReponseExamVF();
		reponseQMC = new ReponseExamMultCh();
	}

	// le point d'entrée à l'examen
	public void startExamen() {
		intervalle=60 ;
          //   list de choix utilisé dans deux pages différentes d'ou l'initilisation  /:: dependence d'un examen et pas de Bean
		setNbfaussesReponses(20);
		listChoix = new ArrayList<Choix>();
		reponsesUtilisateur = new ArrayList<ReponseUtilisateur>();
		historique = new HisExam();
		listQuestions = selectedExamen.getQuestions();
		iterator = listQuestions.iterator();
		setSelectedQuestion((Question) iterator.next());
		
	}

	public void controlExamen(){
		   intervalle -- ;
		   duree -- ;
		   if(duree == 0)  finiExamen() ;
		   if(intervalle == 0)  doNext();
	}
	// suivant
	public void doNext() {
		intervalle =60 ;
		// on vérifie la question juste ou fause
		verifierReponse();
		// passer à la question suivant
		selectedQuestion = iterator.next();
	}

	public void verifierReponse() {
		// verification pour les questions QD VF
		if (selectedQuestion instanceof QuestVF) {
			reponsesUtilisateur.add(reponseQD);
			if (reponseQD.getReponseVF() != ((QuestVF) selectedQuestion).getReponseVF())
				score--;
		}
		// verification pour les Questions QCM
		if (selectedQuestion instanceof QuestionMultChoix) {
			reponsesUtilisateur.add(reponseQMC);
			if (reponseQMC.getChoix() != getBonchoix())
				score--;
		 	reponseQMC = new ReponseExamMultCh();
		}

		// règle si nb des questions fauses = 5 on fini l'examen
		if (score == 15 || (reponsesUtilisateur.size() == 20))
			finiExamen();
		
	}
	// pour mttre fini à l'examen
	public void finiExamen() {
		remplirReponse();
		//  charger l'historique
		historique.setDatePassage(new Date());
		historique.setExamen(selectedExamen);
		historique.setScoreExam(score);
		historique.setRepsUtilisateur(reponsesUtilisateur);
         
		// **************** fermer les dialoge
		RequestContext context = RequestContext.getCurrentInstance();
		FacesContext context1 = FacesContext.getCurrentInstance();
		context.execute("passageExamenDialog.hide();");
		context.execute("p.stop();");
		// ************* score *******
	
		// **************** redireriction vers la page
        
		try {
			if (selectedExamen.getType() == Type.VraiFaux) {
				context1.getExternalContext().redirect(context1.getExternalContext().getRequestContextPath()+ "/Utilisateur/examenPasseQD.xhtml");

			} else
				context1.getExternalContext().redirect(context1.getExternalContext().getRequestContextPath()+ "/Utilisateur/examenPasseMC.xhtml");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Choix getBonchoix() {
		List<Choix> choix = ((QuestionMultChoix) selectedQuestion).getChoix();
		Choix bon = null;
		for (Choix c : choix) {
			if (c.isBonReponse())
			bon = c;
		}
		return bon;
	}

	public void remplirReponse() {
		if (selectedQuestion instanceof QuestVF) {
			while (reponsesUtilisateur.size() < 20) {
				reponsesUtilisateur.add(new ReponseExamVF(VraiFaux.SansReponse));
				score--;
			}
		}

		// verification pour les Questions QCM
		if (selectedQuestion instanceof QuestionMultChoix) {
			while ((reponsesUtilisateur.size()) < 20) {
				reponsesUtilisateur.add(new ReponseExamMultCh());
				score--;
			}
		}

	}

	//  récuperere les bonnes réponses 
	public void getBonneChoix() {
		for (Question q : selectedExamen.getQuestions()) {
			for (Choix c : ((QuestionMultChoix) q).getChoix()) {
				if (c.isBonReponse())
					listChoix.add(c);
			}
		}
	}

	//  His  
	public void sauvegarder(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 UtilisateurExpert expert=serviceUtilisateur.getExpertByLogin(request.getUserPrincipal().getName());
		// historique.setUtilisateurClient((UtilisateurClient)((Utilisateur)(expert)));
		 historique.setUtilisateurExpert(expert);
	   	 servicehis.create(historique);
	}
	// *********************** Getters & Setters *******************************
	public int getNbfaussesReponses() {
		return score;
	}

	public void setNbfaussesReponses(int nbfaussesReponses) {
		this.score = nbfaussesReponses;
	}

	public Examen getSelectedExamen() {
		return selectedExamen;
	}

	public void setSelectedExamen(Examen selectedExamen) {
		this.selectedExamen = selectedExamen;
	}

	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

	public List<Choix> getChoix() {
	    getBonneChoix();
		return listChoix;
	}

	public void setChoix(List<Choix> choix) {
		this.listChoix = choix;
	}

	public ReponseExamMultCh getReponseQMC() {
		return reponseQMC;
	}

	public void setReponseQMC(ReponseExamMultCh reponseQMC) {
		this.reponseQMC = reponseQMC;
	}

	public ReponseExamVF getReponseQD() {
		return reponseQD;
	}

	public void setReponseQD(ReponseExamVF reponseQD) {
		this.reponseQD = reponseQD;
	}

	public List<ReponseUtilisateur> getReponsesUtilisateur() {
		return reponsesUtilisateur;
	}

	public void setReponsesUtilisateur(
			List<ReponseUtilisateur> reponsesUtilisateur) {
		this.reponsesUtilisateur = reponsesUtilisateur;
	}

	public HisExam getHistorique() {
		return historique;
	}

	public void setHistorique(HisExam historique) {
		this.historique = historique;
	}

	public int getIntervalle() {
		return intervalle;
	}

	public void setIntervalle(int intervalle) {
		this.intervalle = intervalle;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}
}
