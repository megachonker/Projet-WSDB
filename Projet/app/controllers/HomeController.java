package controllers;


import models.*;

import play.data.*;
import javax.inject.Inject;

import play.mvc.*;




public class HomeController extends Controller {
@Inject FormFactory formFactory;
Form<User> userForm;
    //nique  sa  mere a java
    /*


    public Result sayhelloform(){
        userForm = formFactory.form(User.class);
        return ok(views.html.sayhelloform.render(userForm));
    }

    public Result helloworldform() {
        Form<User> pForm = userForm.bindFromRequest() ;
        User a = pForm.get() ;
        return ok(views.html.helloworldform.render(a)) ;
    }
    
    */
    
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result test(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        String pseudo = requestData.get("pseudo");
        String password = requestData.get("password");
        return ok(views.html.test.render(pseudo));
    }

    public Result login() {
        return ok("azer");//views.html.login.render());
    }

    public Result loginBIS() {

        userForm = formFactory.form(User.class);
            // case class UserData(pseudo: String, password: String)   
        return ok(views.html.login.render(userForm));
    }   
    
    

    
    
    /*
    public Result identification() {  // statique  normalement
        Form<Login> loginForm  = form(login.class).bindFromRequest();

        //a  voir
            if (loginForm.hasErrors()) {
        return badRequest(views.html.login.render(loginForm));
    } else {
        session().clear();
        session("email", loginForm.get().email);
        return redirect(
            routes.Application.index()
        );
    }
    }

    public String validation() {
        if(User.identification(pseudo)==null){
            return "mdp/user invalide"
        }
        return null;
    }
    */
}
