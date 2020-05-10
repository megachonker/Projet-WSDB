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
        DynamicForm requestData = formFactory.form().bindFromRequest(request); // dans le  cour  le request n'existe pas  ...
        
        if (requestData.hasErrors()) {
            return badRequest(views.html.login.render(formFactory.form(User.class))) ;//  utilisant  un dynamic  form  je ne peut pas revoiller  l'objet donc   l'erreur
        }
        else{
            String pseudo = requestData.get("pseudo");  //TELMENT  MOCHE mais renvoiller un  object ne  marche pas ...
            String password = requestData.get("password");
            return ok(views.html.profile.render(pseudo,password));   
        }
    }

    public Result login() {
        //on crée  un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        return ok(views.html.login.render(userForm));
    }   
    
}
