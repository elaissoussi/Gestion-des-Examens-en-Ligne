package com.e2a.examenenligne.converter;

import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.e2a.examenenligne.entities.Choix;
import com.e2a.examenenligne.entities.ReponseExamVF;
import com.e2a.examenenligne.entities.Statut;
import com.e2a.examenenligne.entities.UtilisateurExpert;



/**
 * Classe utilitaire pour effectuer la conversion entre les page jsf et les
 * objet POJO Ainsi on trouve getAsObjet ManagedBean de JSF <---> getAsString
 * page xhtml JSF
 * 
 * @author MELAISSO
 * 
 * 
 */
@FacesConverter("com.e2a.examenenligne.converter.ObjectConverter")
public class ObjectConverter implements Converter {

	private static HashMap<String, Object> map = new HashMap<String, Object>();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Object o = map.get(value);
		if (o instanceof UtilisateurExpert) {
			return (UtilisateurExpert) o;
		} else if (o instanceof Statut) {
			return (Statut) o;
		}else if (o instanceof ReponseExamVF) {
			return (ReponseExamVF) o;
		} 
		else if (o instanceof Choix) {
			return (Choix) o;
		} 
		else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object o) {
		
		if (o instanceof  UtilisateurExpert)  {
			UtilisateurExpert u = (UtilisateurExpert) o;
			map.put(u.toString(),u);
			return u.toString();
		} else if (o instanceof Statut) {
			Statut s = (Statut) o;
			map.put(s.toString(), s);
			return s.toString();
		}else if (o instanceof ReponseExamVF) {
			ReponseExamVF r = (ReponseExamVF) o;
			map.put(r.toString(), r);
			return r.toString();
		} 
		else if (o instanceof Choix) {
			Choix c = (Choix) o;
			map.put(c.toString(), c);
			return c.toString();
		} 
		else
			return null;

	}

}
