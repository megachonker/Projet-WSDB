package controllers;

import models.*;
import play.mvc.*;
import play.data.*;
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


    //profile  est embigue il permet de loader la page profile est verifie les formuler de register
    public Result profile(Http.Request request) {
        //on déclare userForm avec une valleur  sinon ça   fait crash
        //si  il   n'y  a   pas  de  soucis on va  convertire  la requete en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request);
        //On  vérifie si  le  formulaire a  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            if(request.session().get("session").isPresent()){//verification si kookies
                User userProfils = User.find.byId(Long.parseLong((request.session().get("session").get())));
                return ok(views.html.User.profile.render(userProfils));
            }
            //on verrrifie  si  le  formulaire est  vide
            if(formulaireRecus.toString().equals("Form(of=class models.User, data={}, value=Optional[pseudo: nullidentifiant: 0], errors=[ValidationError(password,[error.required],[]), ValidationError(pseudo,[error.required],[])])")||
               formulaireRecus.toString().equals("Form(of=class models.User, data={}, value=Optional[pseudo: nullidentifiant: 0], errors=[ValidationError(pseudo,[error.required],[]), ValidationError(password,[error.required],[])])")){//si le formulaire est vide
                return redirect("/login");
            }
            //S'il y a  une erreur alors on renvoit le formulaire ou pas de  formulaire
            return badRequest(views.html.User.register.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire
        }
        else{

            //Si tout ce passe bien alors on va créer un object user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //on va  sauvgarder le les data dans  la  base de  donnée
            userProfils.save();
            //Oon  balance un ok avec un get
            return ok(views.html.User.profile.render(userProfils));
                                            //.addingToSession(request, "session", String.valueOf(truc.id));//on ajoute un kookie qui a pour id de session l'id de l'user en sachant que le mieux ces un truc  random

            //redirect("userliste");
        }

    }


    public Result login(Http.Request request) {
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans checklogin
        return ok(views.html.User.login.render(userForm, request,messagesApi.preferred(request)));
    }

    public Result checklogin(Http.Request request) {
        Form<User> formulaireRecus = userForm.bindFromRequest(request);
        if (formulaireRecus.hasErrors()) {
            return badRequest(views.html.User.login.render(formulaireRecus, request,messagesApi.preferred(request))); //marche pas,  on a  un renvoit  de  page  maispas   de  formulaire
        }
        else{
            User userProfils = formulaireRecus.get();
            List<User> u = User.find.all(); // type inference works here!
            for(User truc : u) {
                if (truc.getPseudo().equals(userProfils.getPseudo())){
                    if(truc.getPassword().equals(userProfils.getPassword())){
                        return redirect("/profile").addingToSession(request, "session", String.valueOf(truc.id));//on ajoute un kookie qui a pour id de session l'id de l'user en sachant que le mieux ces un truc  random
                    }
                }
            }
            return ok(views.html.User.login.render(userForm, request,messagesApi.preferred(request)));
            //return ok(views.html.User.profile.render(userProfils));
            //redirect("userliste");
        }

    }

    //Déconnexion
    public Result unlog(Http.Request request) {
        return ok(views.html.User.unlog.render()).withNewSession();
}

    public Result register(Http.Request request) {
        //On créé un formulaire a  partire de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans login
        return ok(views.html.User.register.render(userForm, request,messagesApi.preferred(request)));
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
    
    public Result updateuser(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        Form <User> uForm = formFactory.form(User.class) ;
        uForm = uForm.fill(u);
        return ok(views.html.User.updateuser.render(u, uForm, request, messagesApi.preferred(request))) ;
    }
    
    public Result updateuserform(Http.Request request) {
        final Form<User> uForm = userForm.bindFromRequest(request);
        User u = uForm.get();
        u.setId(Long.parseLong((request.session().get("session").get())));
        u.update();
        return redirect(routes.Userapp.profile());
    }
    
    public Result nbjoueur() {
        List<User> liste = User.find.all();
        return ok(views.html.Var.nbjoueur.render(liste));
    }
    
    //Formulaire de modification de profil pour les administrateurs
    public Result updateuseradmin(Long id, Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        Form <User> uForm = formFactory.form(User.class) ;
        uForm = uForm.fill(u);
        return ok(views.html.User.updateuseradmin.render(u, uForm, request, messagesApi.preferred(request))) ;
    }
    
    //Envoi du formulaire de modification de profil pour les administrateurs
    public Result updateuseradminform(Long id, Http.Request request) {
        final Form<User> uForm = userForm.bindFromRequest(request);
        User u = uForm.get();
        u.setId(Long.parseLong((request.session().get("session").get())));
        u.update();
        return redirect(routes.Userapp.userlist());
    }
}
