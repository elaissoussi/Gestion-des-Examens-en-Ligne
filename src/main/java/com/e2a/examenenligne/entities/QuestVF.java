package com.e2a.examenenligne.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("QVF")
public class QuestVF extends Question {

	public QuestVF() {
		super();
	}
	public QuestVF(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	private VraiFaux reponseVF;

	@Enumerated(EnumType.STRING)
	public VraiFaux getReponseVF() {
		return reponseVF;
	}

	public void setReponseVF(VraiFaux reponseVF) {
		this.reponseVF = reponseVF;
	}

}
