package models;
import play.api.data.*;
import play.data.validation.*;
import play.data.validation.Constraints.* ;
//import play.libs.Files.TemporaryFile;
//import play.mvc.Http.MultipartFormData.FilePart;

import io.ebean.*;
import java.util.*;
import javax.persistence.*;

@Entity
public class User extends Model{
    
    private static final long serialVersionUID= 1L;
    
    @Id
    public long id;
    
    public static Finder<Long, User> find = new Finder<Long,User>(User.class);
    
    
    //definition des Prérequis  par champ
    
    @Required
    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
	private String pseudo;
    //verifier si pas  existant dans la  bd  ?
    
    @Required
    @Pattern(value = "^(?=.{8,})(?=.*[A-Z]+)(?=.*[0-9]+).*",    message = "kikko detected")//au moins un chiffre et une majuscule et qui va entre 8 et 32 caracteres
    private String password; 
    
        public User(){
            
        }
    
    
    //Déclaration accesseur  est Seter
    
    //faire status connecter  deconecter
    
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }
  }
