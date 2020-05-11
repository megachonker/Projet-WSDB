package controllers;

import models.*; // utiliser la classe User.java 

import play.mvc.*;
import play.data.*; //permetre play d'utiliser des data

import javax.inject.Inject; // utiliser des  formulaire 
//import wiews.html.*;// pas  besoin ?
import java.util.List; //  gestion  liste/ tableaux



public class HomeController extends Controller {
    //on veux ce  baser  sur  des  formulère
    @Inject FormFactory formFactory;
    Form<User> userForm;

    //main page
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result profile(Http.Request request) {
        //on déclare userForm avec une valleur  sinonça   fait crash 
        userForm = formFactory.form(User.class);
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //on  cherche si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //si  il y a  une erreur allor on renvoit le  precedant  formulaire
            return badRequest(views.html.login.render(userForm)); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire 
        }
        else{
            //si  tout ce passe  bien allor on  va crée un  object  user qui a  les data  du formulaire
            User userProfils = formulaireRecus.get();
            //on  balance  un  ok avec  un get
            return ok(views.html.profile.render(userProfils));   
                
        }

    }

    public Result login() {
        //on crée  un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //on envoit le formulaire dans login
        return ok(views.html.login.render(userForm));
    }   
    
}
