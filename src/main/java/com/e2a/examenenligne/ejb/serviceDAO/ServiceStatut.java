package com.e2a.examenenligne.ejb.serviceDAO;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.e2a.examenenligne.entities.Statut;

@Stateless
@LocalBean
public class ServiceStatut  extends DataAccessService<Statut> {

	@PersistenceContext
	EntityManager em;

	// get our list of our statut   without admin role 
	public List<Statut> getListStatut() {
		return super.findWithNamedQuery("findAllStaut");
	
		 }
	
	// get our list of our statut + admin 
		public List<Statut> getListStatuts() {
			return super.findWithNamedQuery("findAllStauts");
		}



}
