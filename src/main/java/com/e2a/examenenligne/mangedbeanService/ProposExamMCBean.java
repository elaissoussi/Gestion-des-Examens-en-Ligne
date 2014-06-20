package com.e2a.examenenligne.mangedbeanService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceExamen;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceUtilisateur;
import com.e2a.examenenligne.entities.Categorie;
import com.e2a.examenenligne.entities.Choix;
import com.e2a.examenenligne.entities.Etat;
import com.e2a.examenenligne.entities.Examen;
import com.e2a.examenenligne.entities.Question;
import com.e2a.examenenligne.entities.QuestionMultChoix;
import com.e2a.examenenligne.entities.Type;
import com.e2a.examenenligne.entities.UtilisateurExpert;



@ManagedBean
@SessionScoped
public class ProposExamMCBean implements Serializable {
  
	@EJB
	private ServiceExamen serviceExamen;
	@EJB
	private ServiceUtilisateur serviceUtilisateur;
	
	//*********  Questions************
	private List<Question> listQuestions;
	private Categorie[] listCategories;
	private Examen exam ;
	//on doit avoir reference sur l'expert qui est entrain de formuler l'exam
	private UtilisateurExpert expert;
	 @PostConstruct
	    public  void init(){
		     exam = new Examen(Type.ChoixMult);
		     setListCategories(Categorie.class.getEnumConstants());
		     //   QuestionMultChoix listQuestions[] = new QuestionMultChoix[20];
		     //    
	         listQuestions= new ArrayList<Question>();
	         for (int i=0; i<20; i++){
	              List <Choix>  choix =new ArrayList<Choix>(); 
	              for (int j=0; j<3; j++){
	            	 choix.add(new Choix(j+1)) ;
	              }
	              QuestionMultChoix qmc=new QuestionMultChoix(i+1);
	              qmc.setChoix(choix);
	              listQuestions.add(qmc) ;
	         } 
	        
	     }
	 
	 
	public String newExamMC(){
		//les deux lignes qui suit permettent d'extraire le login de l'expert courant à partir du context
		//avant de le charger de la base de données via à son login
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		expert=serviceUtilisateur.getExpertByLogin(request.getUserPrincipal().getName());
		//on associe l'exam à l'expert courant
        exam.setExpert(expert);
        //avant d'etre validé par l'admin, l'exam doit rester dans l'etat desactive
	   	exam.setEtat(Etat.Desactive);
	    exam.setQuestions(listQuestions);
		serviceExamen.create(exam);
		//on retourne vers la pages des fonctionnalités de l'expert
		return "index.xhtml";
	}
	
	
	
	//******************************  Getters  and Setters ***************************************************
    public List<Question> getQuestions() {
		return listQuestions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.listQuestions = questions;
	}

	public Examen getExam() {
		return exam;
	}

	public void setExam(Examen exam) {
		this.exam = exam;
	}
    public Categorie[] getListCategories() {
		return listCategories;
	}
    public void setListCategories(Categorie[] listCategories) {
		this.listCategories = listCategories;
	}

	
}
