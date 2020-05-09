package controllers;


import models.*;

import play.data.*;
import javax.inject.Inject;

import play.mvc.*;



public class HomeController extends Controller {

    //nique  sa  mere a java
    /*
@Inject FormFactory formFactory;
Form<User> userForm;

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
    
    public Result test(String fname) {
        return ok(views.html.test.render(fname));
    }

    public Result login() {
        return ok(views.html.login.render());
    }
}
