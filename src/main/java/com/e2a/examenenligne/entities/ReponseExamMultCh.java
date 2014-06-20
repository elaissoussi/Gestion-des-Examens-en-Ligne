package com.e2a.examenenligne.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue("reponseQCM")
public class ReponseExamMultCh extends ReponseUtilisateur{	

private Choix choix;
@ManyToOne( fetch = FetchType.LAZY)
public Choix getChoix() {
	return choix;
}

public void setChoix(Choix choix) {
	this.choix = choix;
}

	
}
