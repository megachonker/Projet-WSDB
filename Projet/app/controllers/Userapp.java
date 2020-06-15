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

    //Profil  est ambigu, il permet de charger la page profil et vérifie les formulaires d'enregistrement
    public Result profile(Http.Request request) {
        //On déclare userForm avec une valeur  sinon ça fait crash
        //Si  il   n'y  a   pas  de  soucis on va  convertir  la requête en  formulaire
        Form<User> formulaireRecus = userForm.bindFromRequest(request);
        //On  vérifie si  le  formulaire à  des erreur  voir User.java
        if (formulaireRecus.hasErrors()) {
            if(request.session().get("session").isPresent()){//verification si l'id de  l'user  concord avec  la bd
                User userProfils = User.find.byId(Long.parseLong((request.session().get("session").get())));
                return ok(views.html.User.profile.render(userProfils));
            }
            //On vérifie  si  le  formulaire est  vide
            if(formulaireRecus.toString().equals("Form(of=class models.User, data={}, value=Optional[pseudo: nullidentifiant: 0], errors=[ValidationError(password,[error.required],[]), ValidationError(pseudo,[error.required],[])])")||
               formulaireRecus.toString().equals("Form(of=class models.User, data={}, value=Optional[pseudo: nullidentifiant: 0], errors=[ValidationError(pseudo,[error.required],[]), ValidationError(password,[error.required],[])])")){//Si le formulaire est vide
                return redirect("/login");
            }
            //S'il y a  une erreur alors on renvoit le formulaire
            return badRequest(views.html.User.register.render(formulaireRecus, request,messagesApi.preferred(request))); //Marche pas,  on a  un renvoie  de  page  mais pas   de  formulaire
        }
        else{
            //Si tout ce passe bien alors on va créer un objet user qui a les data du formulaire
            User userProfils = formulaireRecus.get();
            //On va  sauvegarder les données dans  la  base de donnée
            userProfils.save();
            //Puis on redirige
            return redirect("/login");
        }

    }

    //Page de connexion
    public Result login(Http.Request request) {
        //On créé un formulaire à partir de User
        userForm = formFactory.form(User.class);
        //On envoit le formulaire dans checklogin
        return ok(views.html.User.login.render(userForm, request,messagesApi.preferred(request)));
    }

    public Result checklogin(Http.Request request) {
        Form<User> formulaireRecus = userForm.bindFromRequest(request);
        if (formulaireRecus.hasErrors()) {
            return badRequest(views.html.User.login.render(formulaireRecus, request,messagesApi.preferred(request))); //Marche pas,  on a  un renvoie  de  page  mais pas   de  formulaire
        }
        else{
            User userProfils = formulaireRecus.get();
            List<User> u = User.find.all(); //Type inference works here!
            for(User truc : u) {
                if (truc.getPseudo().equals(userProfils.getPseudo())){
                    if(truc.getPassword().equals(userProfils.getPassword())){
                        truc.setStatut(1);
                        truc.update();
                        return redirect("/profile").addingToSession(request, "session", String.valueOf(truc.id));//On ajoute un cookie qui a pour id de session l'id de l'user en sachant que le mieux c'est un truc  aléatoire
                    }
                }
            }
            return ok(views.html.User.login.render(userForm, request,messagesApi.preferred(request)));
            //return ok(views.html.User.profile.render(userProfils));
            //redirect("userliste");
        }

    }

    //Déconnexion en créant une nouvelle session vierge
    public Result unlog(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        u.setStatut(0);
        u.update();
        return ok(views.html.messagetempo.render("Session en cours de déconnexion... À très bientôt ! ♥")).withNewSession();
}

    //Page d'enregistrement
    public Result register(Http.Request request) {
        //On créé un formulaire a  partir de User
        userForm = formFactory.form(User.class);
        //On envoie le formulaire dans login
        return ok(views.html.User.register.render(userForm, request,messagesApi.preferred(request)));
    }

    //Rendu de la liste des joueurs
    public Result userlist(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        List<User> liste = User.find.all();
        return ok(views.html.User.liste.render(u, liste));
    } 
    
    //Suppression d'un utilisateur avec redirection sur la liste des joueurs
    public Result delete(Long id) {
        User u = User.find.byId(id);
        u.delete();
        return redirect(routes.Userapp.userlist());
    }
    
    //Vidange de toute la BDD de joueurs avec redirection sur la liste des joueurs
    public Result flush() {
        List<User> u = User.find.all(); //Type inference works here!
        for(User truc : u) {
            truc.delete();
        }
        return redirect(routes.Userapp.userlist());
    }
    
    //Formulaire de modification de profil basique acessible depuis le profil
    public Result updateuser(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get()))) ;
        Form <User> uForm = formFactory.form(User.class) ;
        uForm = uForm.fill(u);
        return ok(views.html.User.updateuser.render(u, uForm, request, messagesApi.preferred(request))) ;
    }
    
    //Envoie du formulaire de modification de profil basique acessible depuis le profil qui mettra la BDD à jour
    public Result updateuserform(Http.Request request) {
        final Form<User> uForm = userForm.bindFromRequest(request);
        User u = uForm.get();
        u.setId(Long.parseLong((request.session().get("session").get())));
        u.update();
        return redirect(routes.Userapp.profile());
    }
    
    //Pour avoir le nombre de joueurs inscrit dans la sidebar
    public static int getnbjoueur() {
        return User.find.all().size();
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
    
    //Génération du panneau d'administration en fonction du grade de l'utilisateur
    public Result panneladmin(Http.Request request) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get())));
        return ok(views.html.User.panneladmin.render(u));
    } 
    
    //Pour avoir le nombre de joueur connecté dans la sidebar
    public static int getnbjoueuronline() {
        int value = 0;
        List<User> liste = User.find.all();
        if (!liste.isEmpty()){
        for (User u : liste) {
            if(u.getGrade().equalsIgnoreCase("Administrateur") || u.getGrade().equalsIgnoreCase("Joueur")){
                if(u.getStatut() == 1){
                    value++;
                }  
            }
        }
        }
        return value;
    }
    
}
