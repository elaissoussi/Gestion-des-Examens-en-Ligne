package com.e2a.examenenligne.ejb.serviceDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.e2a.examenenligne.entities.UtilisateurExpert;


/**
 * Implementation of the generic Data Access Service
 * All CRUD (create, read, update, delete) basic data access operations for any 
 * persistent object are performed in this class.
 * @author El AISSOUSSI  Monsif 
 */

public  abstract class DataAccessService<T> {

	 @PersistenceContext
	    private EntityManager em;
	 
	 public DataAccessService() {
	}
	 
	 
		private Class<T> type  ;
		
     public   DataAccessService (Class<T> type){
    	   this.type=type ;
       }		
		
     
     /**
      * Stores an instance of the entity class in the database
      * @param T Object
      * @return 
      */
     public T create(T t) {
         this.em.persist(t);
         return t;
     }
     
     
     /**
      * Retrieves an entity instance that was previously persisted to the database 
      * @param T Object
      * @param id
      * @return 
      */
     public T find(Object id) {
         return this.em.find(this.type, id);
     }
     
     /**
      * Removes the record that is associated with the entity instance
      * @param type
      * @param id 
      */
     public void delete(Object id) {
         Object ref = this.em.getReference(this.type, id);
         this.em.remove(ref);
     }

     
     /**
      * Updates the entity instance
      * @param <T>
      * @param t
      * @return the object that is updated
      */
     public T update(T item) {
         if( item instanceof UtilisateurExpert){
        	 UtilisateurExpert user = (UtilisateurExpert)item;
                 if(user.getId() == 1){
                     return item;
                 }
             }
         return (T) this.em.merge(item);
         
     }
     
     
     /**
      * Returns the number of records that meet the criteria
      * @param namedQueryName
      * @return List
      */
     public List findWithNamedQuery(String namedQueryName) {
         return this.em.createNamedQuery(namedQueryName).getResultList();
     }
    
     /**
      * Returns the number of records that will be used with lazy loading / pagination 
      * @param namedQueryName
      * @param start
      * @param end
      * @return List
      */
     
     public List findWithNamedQuery(String namedQueryName, int start, int end) {
         Query query = this.em.createNamedQuery(namedQueryName);
         query.setMaxResults(end - start);
         query.setFirstResult(start);
         return query.getResultList();
     }
     
     
     public List findWithNamedQuery(String namedQueryName, String attribute, String attributeValue,int start, int end) {
         Query query = this.em.createNamedQuery(namedQueryName);
         query.setParameter(attribute, attributeValue);
         query.setMaxResults(end - start);
         query.setFirstResult(start);
         return query.getResultList();
     }
     
     /**
      * Returns the number of total records
      * @param namedQueryName
      * @return int
      */
     public int countTotalRecord(String namedQueryName) {
         Query query = em.createNamedQuery(namedQueryName);
         Number result = (Number) query.getSingleResult();
         return result.intValue();
     }
     
     //******************************Getters & Setters *************************************
	 public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	 
}
