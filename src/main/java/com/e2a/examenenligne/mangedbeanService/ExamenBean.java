package com.e2a.examenenligne.mangedbeanService;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.LazyDataModel;
import com.e2a.examenenligne.ejb.serviceDAO.ServiceExamen;
import com.e2a.examenenligne.entities.Examen;


@ManagedBean
@SessionScoped
public class ExamenBean  implements Serializable {
	
	@EJB
	ServiceExamen  serviceexamen; 
	
	//  l'examen selectionner
	private Examen  selectedExamen ;
	
	// Lazy loading user list
		private LazyDataModel<Examen> lazyModel;
		
	
		@PostConstruct
		public void init() {
		         lazyModel = new LazyExamenDataModel(serviceexamen);
		         selectedExamen =  new Examen();
		}
		
		
//************************  Getters and Setters ********************************
		public LazyDataModel<Examen> getLazyModel() {
			return lazyModel;
		}


		public void setLazyModel(LazyDataModel<Examen> lazyModel) {
			this.lazyModel = lazyModel;
		}


		public Examen getSelectedExamen() {
			return selectedExamen;
		}


		public void setSelectedExamen(Examen selectedExamen) {
			this.selectedExamen = selectedExamen;
		}

}
