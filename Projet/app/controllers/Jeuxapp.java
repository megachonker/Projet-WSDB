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

    @Inject
    public Jeuxapp(FormFactory formFactory, MessagesApi messagesApi){
        this.jeuxForm = formFactory.form(Jeux.class);
        this.messagesApi = messagesApi;
    }

//    public long verrifuser(){
//        Jeux loby = Jeux.find.byId(id); //voir si  le loby existe pas erreur?
//        return id;
//    }

    public Result newgame(Http.Request request) {
        return ok(views.html.Jeux.makeloby.render(jeuxForm,request, messagesApi.preferred(request)));
    }


    //création d'une partie
//    public Result newgame(Long idjoueur1,Long idjoueur2) {
//        User joueur1 = User.find.byId(idjoueur1);
//        User joueur2 = User.find.byId(idjoueur2);
//        return ok();
//    }
    public Result lobyliste() {
        List<Jeux> liste = Jeux.find.all();
        return ok(listloby.render(liste));
    }


    //Page après envoi formulaire

    public Result resultatnewgame(Http.Request request) {
        Form<Jeux> cForm = jeuxForm.bindFromRequest(request);
        //Si erreur réafficher la page contact avec les messages d'erreur
        if (cForm.hasErrors()) {
            return badRequest("ERROR bad request ;:"+cForm.toString());//views.html.Jeux.game.render(cForm, null, request, messagesApi.preferred(request)));
        }
        //Sinon afficher la page contact avec message stipulant que le message a bien été envoyé.
        else{
            Jeux magame = cForm.get();
//setUser1   peut  porter a  confusion  car  ici ces  l'id  d'user  qui est dans  les  loby
            List<User> machin = User.find.all(); // une  l iste qui dump tout les user  de la db
            for(User selecUser : machin) {  // on  va  lister chaque user

                if (selecUser.getPseudo().equals(magame.getPseudo1())) {//si l'utilisateur qui a le droit  de rejoindre la game existe dans la bd
                    magame.setUser1((int) selecUser.id);//on va chercher a stoquer l'identifiant de l'user demander pour  la  wheitlsliete

                }
                if (selecUser.getPseudo().equals(magame.getPseudo2())) {
                    magame.setUser2((int)selecUser.id);
                }
            }
            if(magame.getUser1()==0||magame.getUser2()==0||magame.getUser1()==magame.getUser2()){
                return badRequest(views.html.Jeux.makeloby.render(cForm, request,messagesApi.preferred(request)));
            }
            magame.save();
//            return ok(magame.getPseudo1()+magame.getUser2());
            return redirect("/joinloby/"+magame.id);//views.html.Jeux.game.render(cForm, "Votre requête nous a bien été transmise et sera traitée dès que possible. Merci !", request, messagesApi.preferred(request)));
        }
    }

    public Result joinloby(Http.Request request,long id){ //join loby verrifi que l'utilisateur peut renter  mais est aussi la  main du game

        long idvisiteur  = Long.parseLong((request.session().get("session").get()));//avec le cookie on   cherche l'id de  l'user

        Jeux loby = Jeux.find.byId(id); //cherche le loby dans la bd en fonction de l'uri
        User actualuser = User.find.byId(idvisiteur);// j'isole la  classe user
//        return ok(""+loby.toString());
        if(idvisiteur  == (loby.getUser1())){  //EQUALSE !! //que l'on va comparer avec les id autoriser du loby
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


    //Valide  l'action du joueur dans le jeux
//    public Result checkformulaireactionloby(Http.Request request) {
//        Form<Jeux> formulaireRecus = jeuxForm.bindFromRequest(request);
//            Jeux tour = formulaireRecus.get();
//            tour.save();
//            return ok(views.html.User.profile.render(tour));
//        }




//
//    public Result loby(Long idloby) {
//        Jeux loby = Jeux.find.byId(idloby);
//        return ok(Jeux.resumerJoueur());
//    }


    public Result flush() {
        List<Jeux> u = Jeux.find.all(); // type inference works here!
        for(Jeux truc : u) {
            truc.delete();
        }
        return ok(views.html.messagetempo.render("supression base donnée des jeux"));
    }

}
