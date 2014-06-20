package com.e2a.examenenligne.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;



@Entity
@DiscriminatorValue("QCM")
public class QuestionMultChoix extends Question {
	
	
	public QuestionMultChoix() {
		super();
	}
	public QuestionMultChoix(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	private List<Choix> choix;


//Si on perssite la question les choix aussi --> supression 
@OneToMany( cascade=CascadeType.ALL, fetch = FetchType.LAZY)
@JoinColumn(name="Question_id")
public List<Choix> getChoix() {
	return choix;
}

public void setChoix(List<Choix> choix) {
	this.choix = choix;
}


}
