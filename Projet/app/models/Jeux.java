package models;
import play.api.data.*;
import play.data.validation.*;
import play.data.validation.Constraints.* ;
//import play.libs.Files.TemporaryFile;
//import play.mvc.Http.MultipartFormData.FilePart;

import io.ebean.*;
import java.util.*;
import javax.persistence.*;


import java.util.ArrayList; // import the ArrayList class

@Entity
public class Jeux extends Model{
    
    private static final long serialVersionUID= 1L;
    
    @Id
    public long id;
    
    public static Finder<Long, User> find = new Finder<Long,User>(Jeux.class);
    
    ArrayList<boolean> coupJ1 = new ArrayList<boolean>();
    ArrayList<boolean> coupJ2 = new ArrayList<boolean>();
    ArrayList<boolean> resutatJ1 = new ArrayList<boolean>();
    ArrayList<boolean> resutatJ2 = new ArrayList<boolean>();

	private User user1;
    private User user2;
    private String noms;
    //verifier si pas  existant dans la  bd  ?

    private String password; 
    
    public Jeux(User user1 ,User user1, String noms){
        this.user1 = user1;
        this.user2 = user2;
        this.noms = noms;

    }
    
    
    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getNoms() {
        return noms;
    }
        
    public void setUser1(User user1) {
        this.user1 = user1;
    }
    
    public void setUser2(User user2) {
        this.user2 = user2;
    }
    
    public User getUser1() {
        return user1;
    }
    
    public User getUser2() {
        return user2;
    }

    public String listUser(){
        return user1.toString()+"\n "+ user2.toString();
    }
    
    public String status(){
        return "noms salle: "+noms+" sont id :"+ id+"\n"+listUser();
    }
    
    public String jouercoup(int joueur, boolean valeur){
        //verification des coups
        if (joueur ==1 ) {
            if(coupJ1.size()+1 == coupJ1.size()){
                coupJ1.add(valeur);
            }
            else {
                return "joueur 1 a déja jouer";
            }
        }
        else{
            if(coupJ2.size()+1 == coupJ2.size()){
                coupJ2.add(valeur);
            }
            else {
                return "joueur 2 a déja jouer";
            }
        return
        }

        //on  incrémente le coup
        if(coupJ2.size() == coupJ2.size()){
            tour = coupJ1.size();
            if (coupJ1.get(tour) & coupJ2.get(tour)) {
                if(coupJ1.get(tour) == true){
                    return "les joueur on tout les deux dit la véritée"
                }
                else{
                    return "les joueur ou tout les deux dit le mensonge"
                }
            }
            else{
                if (coupJ1.get(tour) == true) {
                    return "le joeur 1  a dit la veriter le joeur 2 a mentis";
                }
                else{
                    return "le joeur 2  a dit la veriter le joeur 1 a mentis";
                }
            }
            return "tour terminer avec erreur ducoup"
        }
    }

    //dumping des score
    public String resumerJoueur(){
        String  message = "";
        for ( String j1: coupJ1 ) {
            message += "joueur 1" + String.valueOf(j1);
            for ( String j2: coupJ2 ) {
                message += "joueur 2" + String.valueOf(j2) ; 
            }
        message += "\n";
        }
        return message;
    }
  }
