package com.e2a.examenenligne.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Statut" )
@DiscriminatorValue("Admin")

@NamedQueries({
	@NamedQuery(name="findAllUsers",query="select U from Utilisateur U"),
	@NamedQuery(name="findUserById",query="select U from Utilisateur U where U.id=:id"),
	@NamedQuery(name="verifyUser",query="select U from Utilisateur U where U.login= :login AND  U.motDePasse= :motdepasse"),
	@NamedQuery(name="verifyLogin",query="select U from Utilisateur U where U.login= :login"),
	@NamedQuery(name="countUsers",query="select count(U) from Utilisateur U")

})

public class Utilisateur extends BaseEntity implements Serializable {
	private String nom;
	private String prenom;
	private String login;
	private String motDePasse;
	private Statut statut;

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	} 
	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
  
	
	
}
