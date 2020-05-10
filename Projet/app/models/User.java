package models;

import play.libs.Files.TemporaryFile;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.validation.Constraints.* ;
public class User {
    
    @Required
    @Pattern(value = "^.{8,32}",    message = "sale bz on  est plus en 2010")
    
	public String pseudo;

    @Required
    @Pattern(value = "^.{3,20}",    message = "kikko detected")
    
    public String password; 
    
    //getter   est seter  pour la  sécu
    
    /*
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
    */
  }
