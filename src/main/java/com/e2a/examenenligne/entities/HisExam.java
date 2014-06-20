package com.e2a.examenenligne.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries(
		{
			@NamedQuery(name="findAllHisExam", query="select H from HisExam H"),
			@NamedQuery(name="findAllHisExamsByUtilisateur", query="select H from HisExam H  where H.utilisateurExpert.login=:login")
			
		})
public class HisExam extends BaseEntity implements Serializable {
	
	

	private Date datePassage;
	private float scoreExam;
	// 1--1
	// private UtilisateurClient utilisateurClient;
	private UtilisateurExpert utilisateurExpert;
	// 1--1
	private Examen examen;
	private EtatSauvegarde etatExam;
	private List<ReponseUtilisateur> repsUtilisateur;

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatePassage() {
		return datePassage;
	}

	public void setDatePassage(Date datePassage) {
		this.datePassage = datePassage;
	}

	public float getScoreExam() {
		return scoreExam;
	}

	public void setScoreExam(float scoreExam) {
		this.scoreExam = scoreExam;
	}

	@Enumerated(EnumType.STRING)
	public EtatSauvegarde getEtatExam() {
		return etatExam;
	}

	public void setEtatExam(EtatSauvegarde etatExam) {
		this.etatExam = etatExam;
	}

	@OneToOne
	@JoinColumn(name = "examen_fk", nullable = false)
	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "hisExam_fk")
	public List<ReponseUtilisateur> getRepsUtilisateur() {
		return repsUtilisateur;
	}

	public void setRepsUtilisateur(List<ReponseUtilisateur> repsUtilisateur) {
		this.repsUtilisateur = repsUtilisateur;
	}

	@OneToOne
	@JoinColumn(name = "UtilisateurClient_fk", nullable = false)
	public UtilisateurExpert getUtilisateurExpert() {
		return utilisateurExpert;
	}

	public void setUtilisateurExpert(UtilisateurExpert utilisateurExpert) {
		this.utilisateurExpert = utilisateurExpert;
	}

}
