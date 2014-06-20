package com.e2a.examenenligne.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(
	{
		@NamedQuery(name="findAllExams", query="select E from Examen E"),
		@NamedQuery(name="countExamens", query="select count(E)  from Examen E"),
		@NamedQuery(name="findExamById" , query="select E from Examen E where E.id =:id"),
		@NamedQuery(name="findExamenByCategorie", query="select E from Examen E  where E.categorie=:categorie"),
		@NamedQuery(name="findExamenByExpert", query="select E from Examen E  where E.expert.login=:login")
		
	})
public class Examen  extends BaseEntity implements Serializable  {

	private String titre;
	private Categorie categorie;
	private Etat etat;
	private Type type;
	private UtilisateurExpert expert;
    private List<Question> questions;
   
    public Examen (){
    }
    public Examen (Type type){
    	this.type=type;
    }
  
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	
	@Enumerated(EnumType.STRING)
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Enumerated(EnumType.STRING)
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expert_fk")
	public UtilisateurExpert getExpert() {
		return expert;
	}

	public void setExpert(UtilisateurExpert expert) {
		this.expert = expert;
	}
//  
//	@OneToMany(mappedBy ="examen",fetch = FetchType.LAZY)
	
	//****************************************************************

	//Cascade all  permet de propager l'effet de perssistance 
	// exemple si on persiste l'examen tous les question qui lui associés vont etre perssistant mm pour la suppression
	//le changement effectué ici est, @JoinColumn(name = "examen_fk")-->@JoinColumn(name = "EXAMEN_ID")
		//et cela car lors de la creation de la base de données il y avait dans la table Question deux column
		//la premiére "EXAMEN_ID" généré sysstématiquement par le gestionnaire de persistance à cause de la presence de
		//l'attribut private Examen examen dans l'entité Question
		//et la deuxiéme column est "examen_fk" issue de l'onnotation @JoinColumn(name = "examen_fk") associé à la relation OneToMany declaré ci-dessus
		//le redandance etant claire, on a fusionner les deux column dans une
		//en tirant profit de la génération systématique de "EXAMEN_ID" et en donnant cet column comme paramétre à  @JoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "EXAMEN_ID")
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
}
