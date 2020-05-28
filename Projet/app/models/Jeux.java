package models;

import io.ebean.*;
import javax.persistence.*;
import play.data.validation.Constraints.* ;

import java.util.ArrayList; // import the ArrayList class


@Entity
public class Jeux extends Model{
    
    private static final long serialVersionUID= 1L;
    
    @Id
    public long id;
    //il   aurait que moi j'aurait fait un  uri
    public static Finder<Long, Jeux> find = new Finder<Long,Jeux>(Jeux.class);
    
    ArrayList<Boolean> coupJ1 = new ArrayList<Boolean>();
    ArrayList<Boolean> coupJ2 = new ArrayList<Boolean>();
    ArrayList<Boolean> resutatJ1 = new ArrayList<Boolean>();
    ArrayList<Boolean> resutatJ2 = new ArrayList<Boolean>();

    @Required
    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
	private String pseudo1;

    @Required
    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
    private String pseudo2;
    @Required
    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
    private String noms;

    private int user1;

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    private int user2;


    private String password;  //sale avec mdp ?

    public void setPseudo1(String pseudo1) {
        this.pseudo1 = pseudo1;
    }

    public void setPseudo2(String pseudo2) {
        this.pseudo2 = pseudo2;
    }

    public Jeux(){//String pseudo1, String pseudo2, String noms){
//        //ces triste de faire comme ça mais scala ces de la merde ;:)
//        // user de type  user ne marche pas
//        List<User> machin = User.find.all(); // une  l iste qui dump tout les user  de la db
//        for(User selecUser : machin) {  // on  va  lister chaque user
//            if (selecUser.getPseudo().equals(pseudo1)) {//si l'utilisateur qui a le droit  de rejoindre la game existe dans la bd
//                user1 = (int) selecUser.id;//on va chercher a stoquer l'identifiant de l'user demander pour  la  wheitlsliete
//            } else if (selecUser.getPseudo().equals(pseudo2)) {
//                user2 = (int) selecUser.id;
//            }
//            else {
//                user2 = 2;
//                user1 = 1;
//            }
//        }
//        //SI USER2  OU 1 VIDE FAIRE UNE ERREUR  !:
//
//        this.noms = noms;
    }


    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getPseudo2() {
        return pseudo2;
    }



    //pas de seter   car pas utile
    public String getPseudo1() {
        return pseudo1;
    }

    public String getNoms() {
        return noms;
    }

    //plus  besoin car pas stoquer dans la bd ?


//    public String listUser(){
//        return user1.toString()+"\n "+ user2.toString();
//    }
    
//    public String status(){
//        return "noms salle: "+noms+" sont id :"+ id+"\n"+listUser();
//    }
    
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
        return "fonction retour avec  erreur";
        }

        //on  incrémente le coup
        if(coupJ2.size() == coupJ2.size()){
            int tour = coupJ1.size();
            if (coupJ1.get(tour) & coupJ2.get(tour)) {
                if(coupJ1.get(tour) == true){
                    return "les joueur on tout les deux dit la véritée";
                }
                else{
                    return "les joueur ou tout les deux dit le mensonge";
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
        }
        return "ces la hesss";
    }

    //dumping des score
    public String resumerJoueur(){
        String  message = "";
        for ( Boolean j1: coupJ1 ) {
            message += "joueur 1" + j1;
            for ( Boolean j2: coupJ2 ) {
                message += "joueur 2" + j2;
            }
        message += "\n";
        }
        return message;
    }
  }
