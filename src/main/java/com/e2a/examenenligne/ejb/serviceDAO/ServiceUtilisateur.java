package com.e2a.examenenligne.ejb.serviceDAO;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.e2a.examenenligne.entities.Utilisateur;
import com.e2a.examenenligne.entities.UtilisateurClient;
import com.e2a.examenenligne.entities.UtilisateurExpert;
/**
 * EJB pour les operations DAO.... 
 * @author MELAISSO
 */
 
@Stateless
@LocalBean
public class ServiceUtilisateur  extends DataAccessService<UtilisateurExpert>{
	 
	


	public ServiceUtilisateur() {
        super(UtilisateurExpert.class);
	}
	
	@PersistenceContext
	EntityManager em;

	// Création d'un nouveau utilisateur

	public Utilisateur createUtilisateur(UtilisateurExpert utilisateur) {
		
			
		// vérifie qu'un utilisateur n'existe pas 
		@SuppressWarnings("unchecked")
		List<Utilisateur> results = em.createNamedQuery("verifyLogin")
				.setParameter("login", utilisateur.getLogin())
				.getResultList();
		if (results.size() == 0) {
			em.persist(utilisateur);
			this.em.flush();
			return utilisateur;

		} else
			return null;

	}
	
	//  find all users  from database
	@SuppressWarnings("unchecked")
	public List<UtilisateurExpert> getAllUsers(){
		return super.findWithNamedQuery("findAllUsers");
	}

	public UtilisateurExpert getExpertByLogin(String login) {
		UtilisateurExpert expert = (UtilisateurExpert) em.createNamedQuery("getExpertByLogin").setParameter("login", login).getSingleResult();

		return expert;
	}
	
	
	

}
