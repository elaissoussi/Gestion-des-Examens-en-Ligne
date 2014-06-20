package com.e2a.examenenligne.ejb.serviceDAO;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.e2a.examenenligne.entities.Examen;
@Stateless
@LocalBean
public class ServiceExamen  extends DataAccessService<Examen>{

    public ServiceExamen() {
       super(Examen.class);
    }
    
   

}
