package com.e2a.examenenligne.entities;



import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Client")
public class UtilisateurClient extends Utilisateur {

}
