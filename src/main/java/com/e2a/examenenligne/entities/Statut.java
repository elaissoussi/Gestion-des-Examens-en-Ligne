package com.e2a.examenenligne.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/*
 * 
 * to create an admin  we use the followig query= select S from Statut S    --- then we create admin 
 * after this we change it to what we have now id <> 1
 */

@Entity
@NamedQueries({
	@NamedQuery(name="findAllStaut",query="select S from Statut S where S.id <> 1"),
	@NamedQuery(name="findAllStauts",query="select S from Statut S "),
	@NamedQuery(name="findStatut", query ="select S from Statut S where S.statut=  :statut")
})
public class Statut   extends  BaseEntity implements Serializable{
	
	private String statut;
	
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		//System.out.println(obj instanceof Statut);
		//System.out.println(super.equals(obj));
		return (obj instanceof Statut ) ;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
	

}
