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
    Form<User> userForm;
    
    
        
    @Inject
    public Userapp(FormFactory formFactory, MessagesApi messagesApi){
        this.userForm = formFactory.form(Jeux.class);
        this.messagesApi = messagesApi;
    }

    public makeLoby (Http.Request request) {
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(Jeux.class);
        //On envoit le formulaire dans login
        return ok(views.html.Jeux.makeloby.render(userForm, request,messagesApi.preferred(request)));
    } 
    
    
    public Result lobymake(Http.Request request) {
        //on déclare userForm avec une valleur  sinon ça   fait crash 
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //On  vérifie si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //S'il y a  une erreur alors on renvoit le formulaire
            return badRequest(views.html.User.login.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire 
        }
        else{
            
            //Si tout ce passe bien alors on va créer un object user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //on va  sauvgarder le les data dans  la  base de  donnée
            userProfils.save();
            //Oon  balance un ok avec un get
            return ok(views.html.User.profile.render(userProfils));   
            //redirect("userliste");
        }

    }
    
    
    
    //Page d'accueil
    public Result lobymake(Long idjoueur1,Long idjoueur2) {
        User joueur1 = User.find.byId(idjoueur1);
        User joueur2 = User.find.byId(idjoueur2);
        return ok();
    }
    

    public Result lobyliste() {
        List<Jeux> liste = Jeux.find.all();
        return ok(views.html.User.liste.render(liste));
    } 
    
    public Result loby(Long idloby) {
        Jeux loby = Jeux.find.byId(idloby);
        return ok(Jeux.resumerJoueur());
    }
}
