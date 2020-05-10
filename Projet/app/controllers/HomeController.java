package controllers;

import models.*;

import play.data.*;
import javax.inject.Inject;

import play.mvc.*;



public class HomeController extends Controller {
    //on veux ce  baser  sur  des  formulère
    @Inject FormFactory formFactory;
    Form<User> userForm;

    //main page
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result profile(Http.Request request) {
        if(request != null){  //on verifie si on invoque la page avec des data ou pas  pour eviter qu'il parse  des data nul 
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
        else{
            //si  le formulaire est vide   allor on  fait le   rendu de la page de login
            return badRequest(views.html.login.render(userForm));
        }
    }

    public Result login() {
        //on crée  un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //on envoit le formulaire dans login
        return ok(views.html.login.render(userForm));
    }   
    
}
