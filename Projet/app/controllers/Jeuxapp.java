package controllers;


import models.*;
import play.mvc.*;
import play.data.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;
import views.html.Jeux.listloby;


public class Jeuxapp extends Controller {
    
    @Inject
    FormFactory formFactory;
    MessagesApi messagesApi;
    Form<Jeux> jeuxForm;
	
	int n=-1;
	
    @Inject
    public Jeuxapp(FormFactory formFactory, MessagesApi messagesApi){
        this.jeuxForm = formFactory.form(Jeux.class);
        this.messagesApi = messagesApi;
    }

    //On affichage la page de création de partie
    public Result newgame(Http.Request request) {
            List<User> liste = User.find.all();
            User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
            return ok(views.html.Jeux.makeloby.render(u, liste, jeuxForm,request, messagesApi.preferred(request)));
        }

    //On affichage la page des salons de jeu
    public Result lobyliste() {
        List<Jeux> liste = Jeux.find.all();
        return ok(listloby.render(liste));
    }

    //Page a la récéption du formulaire de création de partie
    public Result resultatnewgame(Http.Request request) {
        //On remplit le formulaire
        Form<Jeux> cForm = jeuxForm.bindFromRequest(request);
        //Si erreur, réafficher la page de création de partie avec les messages d'erreur
        if (cForm.hasErrors()) {
             List<User> liste = User.find.all();
            User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
            return badRequest(views.html.Jeux.makeloby.render(u, liste, cForm, request,messagesApi.preferred(request))); //Marche pas, on a un renvoit de page mais pas de  formulaire
        }
        //Si pas erreur, on traite le formulaire
        else{
            //On transmet les variables du formulaire dans jeux
            Jeux magame = cForm.get();

            //setUser1 peut porter à confusion, car ici c'est l'id d'user qui est dans les salons

            //On va dumper tout la base de donnée dans une liste car les requêtes ne fonctionnent pas
            List<User> machin = User.find.all();

            //Verifie si les champs sont vide
            if(magame.getPseudo1().equals("")){
                magame.setUser1(n--);
            }
            if(magame.getPseudo2().equals("")){
                magame.setUser2(n--);
            }

            for(User selecUser : machin) {  // On va lister chaque user

                if (selecUser.getPseudo().equals(magame.getPseudo1())) {//Si l'utilisateur qui a le droit  de rejoindre la game existe dans la BDD
                    magame.setUser1((int) selecUser.id);//On va chercher à stocker l'identifiant de l'user demandé pour la  whiteliste
                }
                if (selecUser.getPseudo().equals(magame.getPseudo2())) {
                    magame.setUser2((int)selecUser.id);
                }
            }
            if(magame.getUser1()==0||magame.getUser2()==0||magame.getUser1()==magame.getUser2()){
                List<User> liste = User.find.all();
                User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
                return badRequest(views.html.Jeux.makeloby.render(u, liste, cForm, request,messagesApi.preferred(request)));
            }

            magame.save();
            return redirect("/panneladmin");
        }
    }

    public Result joinlobyAffichage(long id){
        return ok("views.html.Jeux.");

    }

	public Result joinloby(Http.Request request,long id){ //Join lobby vérifie que l'utilisateur peut rentrer mais est aussi la main du game

        if (request.session().get("session").isEmpty()){
            User actualuser = new User("guest");
            actualuser.save();
            return redirect("/lobyjoin/"+id).addingToSession(request, "session", String.valueOf(actualuser.id));
        }
        long idvisiteur  = Long.parseLong((request.session().get("session").get()));//Avec le cookie on   cherche l'id de l'user

        Jeux loby = Jeux.find.byId(id); //Cherche le lobby dans la BDD en fonction de l'id
        User actualuser = User.find.byId(idvisiteur);// J'isole la  classe user

        if(loby.getUser1()<0 && (int)idvisiteur!=loby.getUser2()){ // Es id de place libre

            loby.id =  id;
            loby.setUser1((int)idvisiteur);//Peut-être erreur ?
            loby.setPseudo1(actualuser.getPseudo());
            loby.update();//2fois en double peut opti
            return ok(views.html.Jeux.game.render("joueur 1",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else if(loby.getUser2()<0 && loby.getUser1()!=(int)idvisiteur){
            loby.setUser2((int)idvisiteur);//Peut-être erreur ?
            loby.setPseudo2(actualuser.getPseudo());
            loby.update();
            return ok(views.html.Jeux.game.render("joueur 2",loby, actualuser, request, messagesApi.preferred(request)));
        }

        if(idvisiteur  == (loby.getUser1())){  //EQUALSE !! //Que l'on va comparer avec les id autorisés du lobby
            return ok(views.html.Jeux.game.render("joueur 1",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else if(idvisiteur  == (loby.getUser2())){
            return ok(views.html.Jeux.game.render("joueur 2",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else{
            return ok(views.html.messagetempo.render("joueur non admi ===> Dégage !"));
        }

    }


    public Result checkactionloby(Http.Request request) {
        jeuxForm = formFactory.form(Jeux.class);
        return ok("action valide");
    }
    

    public Result flush() {
        List<Jeux> u = Jeux.find.all(); //Type inference works here!
        for(Jeux truc : u) {
            truc.delete();
        }
        return ok(views.html.messagetempo.render("supression base donnée des jeux"));
    }
    
    //On affichage la page des salons de jeu pour administrateur
    public Result lobylisteadmin(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        List<Jeux> liste = Jeux.find.all();
        return ok(views.html.Jeux.listlobyadmin.render(u, liste));
    }
    
    
    public Result deletegame(Long id) {
       Jeux a = Jeux.find.byId(id) ;
       a.delete();
       return redirect(routes.Jeuxapp.lobyliste()) ;
    } 
    
    
 	public static int getmatchencours() {
        return Jeux.find.all().size();
    }
	

	public Result jouer(String pseudo,Boolean value,long id) {
        Jeux.jouercoup(pseudo,value);
        return redirect("/lobyjoin/"+id);
		
    }
	
}


