package com.e2a.examenenligne.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TypedeReponse" )
public  class ReponseUtilisateur   extends BaseEntity  implements Serializable{

	private static final long serialVersionUID = 1L;

	
}
