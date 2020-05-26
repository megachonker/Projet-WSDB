package controllers;

//CLEANER !!

import play.mvc.*;


public class HomeController extends Controller {
    

    
    //Page d'accueil
    public Result index() {
        return ok(views.html.index.render());
    }

//    public Result setcookie(Http.Request request) {
//        return redirect("/")
//                .addingToSession(request, "connected", "user@gmail.com");
//    }
//
//    public Result getcookie(Http.Request request) {
//        return request
//                .session()
//                .get("connected")
//                .map(user -> ok("Hello " + user))
//                .orElseGet(() -> unauthorized("Oops, you are not connected"));
//    }
}
