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
    
    
    
     @Required
     @Pattern(
        value = "^[A-Za-z0-9]{1,24}$",
        message = "Pseudo non valide.")
    private String pseudo ;
    
    @Required
    @Email
    private String mail ;
    
     @Required
     @Pattern(
        value = "^[A-Za-z0-9]{2,}",
        message = "L'objet de votre demande doit avoir au minimum 2 caractères.")
    private String objet ;
    
    @Required
     @Pattern(
        value = "^[A-Za-z0-9]{2,}",
        message = "Le message doit avoir au minimum 2 caractères.")
    private String message ;
    
    public Contact(){
    }
    
    public String getPseudo(){
        return this.pseudo;
    }
    
    public void setPseudo(String p){
        this.pseudo = p;
    }
    
       public String getMail(){
        return this.mail;
    }
    
    public void setMail(String em){
        this.mail = em;
    }
    
    public String getObjet(){
        return this.objet;
    }
    
    public void setObjet(String o){
        this.objet = o;
    }
    
    public String getMessage(){
        return this.message;
    }
    
    public void setMessage(String me){
        this.message = me;
    }
}
