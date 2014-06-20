package com.e2a.examenenligne.entities;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
//Le nom de champ type au niveau de la classe Mère table Mère 
@DiscriminatorValue("Expert")
@NamedQueries({
	@NamedQuery(name="getExpertByLogin",query="select U from UtilisateurExpert U where U.login= :login"),
})
public class UtilisateurExpert extends Utilisateur {
	
	private String profession;
	
	private List<Examen> examens;
	
	public String getProfession() {
		return profession;
	}
	
	
	public void setProfession(String profession) {
		this.profession = profession;
	}

	//Expert propose 1 ou * examen -->  clé etranger au niveu de examen pour referer sur expert
	@OneToMany(fetch = FetchType.EAGER)
	// on cree juste un cle etrangère au niveu de la table examen au lieu d'une table d jointure
	// verification databases --desc examen  
	@JoinColumn(name = "expert_fk", nullable=false)
	public List<Examen> getExamens() {
		return examens;
	}


	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}
 
	
}
