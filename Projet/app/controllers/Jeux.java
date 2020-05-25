package controllers;

//CLEANER !!

import models.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;


public class Jeux extends Controller {
    
    @Inject 
    FormFactory formFactory;
    MessagesApi messagesApi;
    Form<Jeux> jeuxForm;



    @Inject
    public Userapp(FormFactory formFactory, MessagesApi messagesApi){
        this.jeuxForm = formFactory.form(Jeux.class);
        this.messagesApi = messagesApi;
    }

    public Result formulaireactionloby(Http.Request request) {
        jeuxForm = formFactory.form(Jeux.class);
        return ok(views.html.Jeux.game.render(jeuxForm, request,messagesApi.preferred(request)));
    }

    //  ananan
    public Result lobymake(Long idjoueur1,Long idjoueur2) {
        User joueur1 = User.find.byId(idjoueur1);
        User joueur2 = User.find.byId(idjoueur2);
        return ok();
    }


    public Result checkformulaireactionloby(Http.Request request) {
        Form<Jeux> formulaireRecus = jeuxForm.bindFromRequest(request);
        if (formulaireRecus.hasErrors()) {
            return badRequest(views.html.Jeux.game.render(request));//formulaireRecus, request,messagesApi.preferred(request) //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire
        }
        else{
            Jeux tour = formulaireRecus.get();
            tour.save();
            return ok(views.html.User.profile.render(tour));
            //redirect("userliste");
        }

    }




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
