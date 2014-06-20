package com.e2a.examenenligne.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TypeDeQuestion")
public class Question  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String question;
	protected int order;
	
    public Question() {
	}
	public Question(int i) {
		this.order=i;
	}
	
	@Transient
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}


}
