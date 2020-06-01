package models;

import io.ebean.*;
import javax.persistence.*;
import play.data.validation.Constraints.*;

@Entity
public class Contact extends Model{

    private static final long serialVersionUID= 1L;
    
    @Id
    public long id;
    
    public static Finder<Long, Contact> find = new Finder<Long,Contact>(Contact.class);
    
    
    //Un pseudo en attribut est obligatoire pour créer un objet
     @Required
     @Pattern(
        value = "^[A-Za-z0-9]{1,24}$",
        message = "Pseudo non valide.")
    private String pseudo ;
    
    //Une adresse mail valide en attribut est obligatoire pour créer un objet
    @Required
    @Email
    private String mail ;
    
    //Un objet du message en attribut et obligatoire pour créer un objet
     @Required
     @Pattern(
        value = "^[A-Za-z0-9]{2,}",
        message = "L'objet de votre demande doit avoir au minimum 2 caractères.")
    private String objet ;
    
    //Un message en attribut est obligatoire pour créer un objet
    @Required
     @Pattern(
        value = "^[A-Za-z0-9]{2,}",
        message = "Le message doit avoir au minimum 2 caractères.")
    private String message ;
    
    //Constructeur par défaut
    public Contact(){
    }
    
    //Getter de pseudo
    public String getPseudo(){
        return this.pseudo;
    }

    //Setter de pseudo
    public void setPseudo(String p){
        this.pseudo = p;
    }
    
    //Getter de l'adresse mail
    public String getMail(){
        return this.mail;
    }
    
    //Setter de l'adresse mail
    public void setMail(String em){
        this.mail = em;
    }
    
    //Getter de l'objet du message
    public String getObjet(){
        return this.objet;
    }
    
    //Setter de l'objet du message
    public void setObjet(String o){
        this.objet = o;
    }
    
    //Getter du message
    public String getMessage(){
        return this.message;
    }
    
    //Setter du message
    public void setMessage(String me){
        this.message = me;
    }
}
