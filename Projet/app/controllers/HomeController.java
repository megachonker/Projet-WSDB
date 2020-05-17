package controllers;

<<<<<<< HEAD
import models.*; // utiliser la classe User.java 

import play.mvc.*;
import play.data.*; //permetre play d'utiliser des data

import javax.inject.Inject; // utiliser des  formulaire 
//import wiews.html.*;// pas  besoin ?
import java.util.List; //  gestion  liste/ tableaux
=======
import models.*;
import play.mvc.*;
import play.data.*;
import javax.inject.Inject;
import views.html.*;
import java.util.List;
>>>>>>> 09183d0... Ajout de la page contact et suppléments

import play.i18n.MessagesApi;

public class HomeController extends Controller {
<<<<<<< HEAD
    //on veux ce  baser  sur  des  formulère
=======
    
    //On veut se baser sur un formulaire
>>>>>>> 09183d0... Ajout de la page contact et suppléments
    @Inject 
    FormFactory formFactory;
    Form<User> userForm;
    MessagesApi messagesApi;

    
    @Inject
    public HomeController(FormFactory formFactory, MessagesApi messagesApi){
        this.userForm = formFactory.form(User.class);
        this.messagesApi = messagesApi;
    }
    
<<<<<<< HEAD
    //main page
=======
    //Page d'accueil
>>>>>>> 09183d0... Ajout de la page contact et suppléments
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result profile(Http.Request request) {
<<<<<<< HEAD
        //on déclare userForm avec une valleur  sinonça   fait crash 
        //userForm = formFactory.form(User.class);
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //on  cherche si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //si  il y a  une erreur allor on renvoit le  precedant  formulaire
=======
        //on déclare userForm avec une valleur  sinon ça   fait crash 
        //userForm = formFactory.form(User.class);
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //On  vérifie si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //S'il y a  une erreur alors on renvoit le formulaire
>>>>>>> 09183d0... Ajout de la page contact et suppléments
            return badRequest(views.html.login.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire 
        }
        else{
            
<<<<<<< HEAD
            //si  tout ce passe  bien allor on  va crée un  object  user qui a  les data  du formulaire
            User userProfils = formulaireRecus.get();
            //on  balance  un  ok avec  un get
=======
            //Si tout ce passe bien alors on va créer un object user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //Oon  balance un ok avec un get
>>>>>>> 09183d0... Ajout de la page contact et suppléments
            return ok(views.html.profile.render(userProfils));   
                
        }

    }

    public Result login(Http.Request request) {
<<<<<<< HEAD
        //on crée  un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //on envoit le formulaire dans login
        return ok(views.html.login.render(userForm, request,messagesApi.preferred(request)));
    }   
    
     public Result contact() {
        return ok(views.html.contact.render());
    }
=======
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans login
        return ok(views.html.login.render(userForm, request,messagesApi.preferred(request)));
    } 
>>>>>>> 09183d0... Ajout de la page contact et suppléments
    
}
