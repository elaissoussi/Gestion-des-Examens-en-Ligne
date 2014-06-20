package com.e2a.examenenligne.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;


@Entity
public class Choix  extends BaseEntity implements Serializable {
	private String choix;
	private boolean bonReponse;
	private int order ;
//	private QuestionMultChoix questMultChoix;
	
	
	@Transient
   public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
public Choix() {
  }
	public Choix(int i) {
		this.order=i ;
			}
	public String getChoix() {
		return choix;
	}
	public void setChoix(String choix) {
		this.choix = choix;
	}
	public boolean isBonReponse() {
		return bonReponse;
	}
	public void setBonReponse(boolean bonReponse) {
		this.bonReponse = bonReponse;
	}
	
//	public QuestionMultChoix getQuestMultChoix() {
//		return questMultChoix;
//	}
//	public void setQuestMultChoix(QuestionMultChoix questMultChoix) {
//		this.questMultChoix = questMultChoix;
//	}
	
	
	

}
