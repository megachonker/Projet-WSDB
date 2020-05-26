package controllers;

//CLEANER !!

import models.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;


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

    public Result joinloby(long id){
        Jeux loby = Jeux.find.byId(id); //voir si  le loby existe pas erreur?
        long idvisiteur  = 1;//verrifuser();
        if(idvisiteur  == (loby.getUser1().id)){  //EQUALSE !!
            return ok("joueur 1 admis");
        }
        else if(idvisiteur  == (loby.getUser2().id)){
            return ok("joueur 2 admis");
        }
        else{
            return ok("joueur non admi");
        }

    }

//    public Result newgame(Http.Request request) {
////        User joueur1 = User.find.byId(idjoueur1);
////        User joueur2 = User.find.byId(idjoueur2);
//        return ok();
//    }

    //création d'une partie
//    public Result newgame(Long idjoueur1,Long idjoueur2) {
//        User joueur1 = User.find.byId(idjoueur1);
//        User joueur2 = User.find.byId(idjoueur2);
//        return ok();
//    }

    //Page après envoi formulaire
    public Result resultatnewgame(Http.Request request) {
        Form<Jeux> cForm = jeuxForm.bindFromRequest(request);
        //Si erreur réafficher la page contact avec les messages d'erreur
        if (cForm.hasErrors()) {
            return badRequest("bad request");//views.html.Jeux.game.render(cForm, null, request, messagesApi.preferred(request)));
        }
        //Sinon afficher la page contact avec message stipulant que le message a bien été envoyé.
        else{
            Jeux a = cForm.get();
            a.save();
            return ok("resultatnewgamefait");//views.html.Jeux.game.render(cForm, "Votre requête nous a bien été transmise et sera traitée dès que possible. Merci !", request, messagesApi.preferred(request)));
        }
    }

    //formulaire pour  une action
//    public Result formulaireactionloby(Http.Request request) {
//        jeuxForm = formFactory.form(Jeux.class);
//        return ok(views.html.Jeux.game.render(jeuxForm, request,messagesApi.preferred(request)));
//    }

    //Valide  l'action du joueur dans le jeux
//    public Result checkformulaireactionloby(Http.Request request) {
//        Form<Jeux> formulaireRecus = jeuxForm.bindFromRequest(request);
//            Jeux tour = formulaireRecus.get();
//            tour.save();
//            return ok(views.html.User.profile.render(tour));
//        }




//    public Result lobyliste() {
//        List<Jeux> liste = Jeux.find.all();
//        return ok(views.html.Jeux.liste.render(liste));
//    }
//
//    public Result loby(Long idloby) {
//        Jeux loby = Jeux.find.byId(idloby);
//        return ok(Jeux.resumerJoueur());
//    }
}
