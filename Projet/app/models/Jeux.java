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
    
	static String[] pseu = new String[2];
    static ArrayList<Boolean> coupJ1 = new ArrayList<Boolean>();
    static ArrayList<Boolean> coupJ2 = new ArrayList<Boolean>();
    ArrayList<Boolean> resutatJ1 = new ArrayList<Boolean>();
    ArrayList<Boolean> resutatJ2 = new ArrayList<Boolean>();

    @Pattern(value = "^[A-Za-z0-9 ]{1,24}$",    message = "sale bz on  est plus en 2010")
	private String pseudo1;

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

    public Jeux(){
        pseudo1="";
        pseudo2="";
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

	public static String jouercoup(String joueur, boolean valeur){
		//assigniation Joueur1 et Joueur2
		if(pseu[0]==null){
			pseu[0]=joueur;
		}else if(pseu[1]==null){
			pseu[1]=joueur;
		}
		
		String res="";
		//verification des coups
        if (joueur.equals(pseu[0]) ) {
            if(coupJ1.size()+1 == coupJ2.size() || coupJ1.size()+1 == coupJ2.size()+1){
                coupJ1.add(valeur);
				res += "Coup J1 OK / ";
            }else{
				res += "Coup J1 déja jouer / ";
			}	
        }else{
            if(coupJ2.size()+1 == coupJ1.size() || coupJ2.size()+1 == coupJ1.size()+1){
                coupJ2.add(valeur);
				res += "Coup J2 OK / ";
            }else{
				res += "Coup J2 déja jouer / ";
			}
        }
		
		//on  incrémente le coup
        if(coupJ2.size() == coupJ1.size()){
            int tour = coupJ1.size()-1;
            if (coupJ1.get(tour) == coupJ2.get(tour)) {
                if(coupJ1.get(tour) == true){
                    res += "les 2 véritée / ";
                }
                else{
                    res += "les 2 mensonge /";
                }
            }
            else{
                if (coupJ1.get(tour) == true) {
                    res += "j1 veriter, j2  mensonge / ";
                }
                else{
                    res += "j2 veriter, j1  mensonge / ";
                }
            }
        }else{
			res += "en attente du second joueur / ";
		}
        return res;
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
