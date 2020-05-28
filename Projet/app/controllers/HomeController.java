package controllers;

import play.mvc.*;

public class HomeController extends Controller {
    

    
    //Page d'accueil
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getcookie(Http.Request request) {
        return ok(request.session().get("session").get());
    }
}
