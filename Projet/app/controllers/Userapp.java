package controllers;

import models.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;


public class Userapp extends Controller {
    
    //On veut se baser sur un formulaire
    
    @Inject 
    FormFactory formFactory;
    MessagesApi messagesApi;
    Form<User> userForm;

    
    @Inject
    public Userapp(FormFactory formFactory, MessagesApi messagesApi){
        this.userForm = formFactory.form(User.class);
        this.messagesApi = messagesApi;
    }
    
    //Page d'accueil
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public Result profile(Http.Request request) {
        //on déclare userForm avec une valleur  sinon ça   fait crash 
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request); 
        //On  vérifie si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            //S'il y a  une erreur alors on renvoit le formulaire
            return badRequest(views.html.User.login.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire 
        }
        else{
            
            //Si tout ce passe bien alors on va créer un object user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //on va  sauvgarder le les data dans  la  base de  donnée
            userProfils.save();
            //Oon  balance un ok avec un get
            return ok(views.html.User.profile.render(userProfils));   
                
        }

    }

    public Result login(Http.Request request) {
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans login
        return ok(views.html.User.login.render(userForm, request,messagesApi.preferred(request)));
    } 
    
    public Result userlist() {
        List<User> liste = User.find.all();
        return ok(views.html.User.liste.render(liste));
    }
    
    public Result delete(Long id) {
        User u = User.find.byId(id);
        u.delete();
        return ok("user  Suprimer");
    }
    
    public Result flush() {
        List<User> u = User.find.all(); // type inference works here!
        for(User truc : u) {
            truc.delete();
        }
        return ok("tout le monde est mort / Suprimer");
    }
    
    
    
}
