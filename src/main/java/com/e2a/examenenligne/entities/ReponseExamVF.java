package com.e2a.examenenligne.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("reponseVF")
public class ReponseExamVF extends ReponseUtilisateur {

	private VraiFaux reponseVF;
	
	public ReponseExamVF() {
		super();
	}
  public ReponseExamVF(  VraiFaux  vf) {
		super();
        this.reponseVF = vf ;
      }
	@Enumerated(EnumType.STRING)
	public VraiFaux getReponseVF() {
		return reponseVF;
	}

	public void setReponseVF(VraiFaux reponseVF) {
		this.reponseVF = reponseVF;
	}

}
