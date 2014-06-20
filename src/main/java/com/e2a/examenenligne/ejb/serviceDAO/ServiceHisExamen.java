package com.e2a.examenenligne.ejb.serviceDAO;

import javax.ejb.Stateless;
/*
 * Service HisExamen
 */
import com.e2a.examenenligne.entities.HisExam;
@Stateless
public class ServiceHisExamen    extends DataAccessService<HisExam> {
  
		
	public ServiceHisExamen() {
           super(HisExam.class);	
	}
}
