package controllers;

import models.*;
import play.mvc.*;
import play.data.*;
import javax.inject.Inject;
import views.html.*;
import java.util.List;

import play.i18n.MessagesApi;

public class HomeController extends Controller {
    
    //On veut se baser sur un formulaire
    @Inject 
    FormFactory formFactory;
    Form<User> userForm;
    MessagesApi messagesApi;

    
    @Inject
    public HomeController(FormFactory formFactory, MessagesApi messagesApi){
        this.userForm = formFactory.form(User.class);
        this.messagesApi = messagesApi;
    }
    
    //Page d'accueil
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result profile(Http.Request request) {
        //on déclare userForm avec une valleur  sinon ça   fait crash 
        //userForm = formFactory.form(User.class);
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //On  vérifie si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //S'il y a  une erreur alors on renvoit le formulaire
            return badRequest(views.html.login.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire 
        }
        else{
            
            //Si tout ce passe bien alors on va créer un object user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //Oon  balance un ok avec un get
            return ok(views.html.profile.render(userProfils));   
                
        }

    }

    public Result login(Http.Request request) {
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans login
        return ok(views.html.login.render(userForm, request,messagesApi.preferred(request)));
    } 
    
}
