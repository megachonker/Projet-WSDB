package controllers;

//CLEANER !!

import models.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;


public class HomeController extends Controller {
    

    
    //Page d'accueil
    public Result index() {
        return ok(views.html.index.render());
    }
    
    
}
