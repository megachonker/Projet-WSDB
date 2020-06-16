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
    static ArrayList<Integer> pointJ1 = new ArrayList<Integer>();
    static ArrayList<Integer> pointJ2 = new ArrayList<Integer>();
	static int u=0;
	

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

 	public String getPointJ1() {
		String res="Vide";
		if(pointJ1.size()!=0){
			res="";
			for (int i : pointJ1){
				res+=i+"";
			}
		}
        return res;
    }
	
	public String getPointJ2() {
		String res="Vide";
		if(pointJ2.size()!=0){
			res="";
			for (int i : pointJ2){
				res+=i+"";
			}
		}
        return res;
    }

	public String cop1() {
		String res="N'as pas encore joué";
		if(pointJ1.size()!=0){
			res="Coopère";
			if (pointJ1.get(pointJ1.size()-1) == 1 || pointJ1.get(pointJ1.size()-1) ==5){
				res ="Trahie";
			}
		}
		return res;
    }
	
	public String cop2() {
		String res="N'as pas encore joué";
		if(pointJ2.size()!=0){
			res="Coopère";
			if (pointJ2.get(pointJ2.size()-1) == 1 || pointJ2.get(pointJ2.size()-1) ==5){
				res ="Trahie";
			}
		}
		return res;
    }
	
	public int getSommePointJ1() {
		int res=0;
		for (int i : pointJ1){
			res+=i;
		}
        return res;
	}
	public int getSommePointJ2() {
		int res=0;
		for (int i : pointJ2){
			res+=i;
		}
        return res;
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
		
		String res="";
		if(pseu[0]==null){
			pseu[0]=joueur;
		}else if(pseu[1]==null){
			pseu[1]=joueur;
		}
			
			//verification des coups
		if(u<20){
			
		if (joueur.equals(pseu[0]) ) {
			if(coupJ1.size()>coupJ2.size()){
				res += "Coup J1 déja jouer / ";
			}else{
				coupJ1.add(valeur);
				res += "Coup J1 OK / ";
				u++;
			}	
		}else{
			if(coupJ2.size()>coupJ1.size()){
				res += "Coup J2 déja jouer / ";
			}else{
				coupJ2.add(valeur);
				res += "Coup J2 OK / ";
				u++;
			}
		}
			//on  incrémente le coup
		if(coupJ2.size() == coupJ1.size()){
			int tour = coupJ1.size()-1;
			if (coupJ1.get(tour) == coupJ2.get(tour)) {
				if(coupJ1.get(tour) == true){
					res += "les 2 coop (3,3) / ";
					pointJ1.add(3);
					pointJ2.add(3);
				}
				else{
					res += "les 2 trahie (1,1)/";
					pointJ1.add(1);
					pointJ2.add(1);
				}
			}
			else{
				if (coupJ1.get(tour) == true) {
					res += "j1 coop, j2  trahie (0,5) / ";
					pointJ1.add(0);
					pointJ2.add(5);
				}
				else{
					res += "j1 trahie, j2  coop (5,0) / ";
					pointJ1.add(5);
					pointJ2.add(0);
				}
			}
		}else{
			res += "en attente du second joueur / ";
		}
			
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
