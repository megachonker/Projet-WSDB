package models;
//import play.api.data.*;  //  usless
import play.libs.Files.TemporaryFile;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.validation.Constraints.* ;

public class User {
    
    @Required
    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
    //verifier si pas  existant dans la  bd  ?
    
	private String pseudo;
    @Required
    @Pattern(value = "^(?=.{8,})(?=.*[A-Z]+)(?=.*[0-9]+).*",    message = "kikko detected")//MDP quiu  contien au moin un  chifre est une  majuscule   est qui va  entre  8 et 32 caractere 
    
    private String password; 
    
        public User(){
            
        }
    
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
