package controllers;


import models.*;
import play.mvc.*;
import play.data.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi;
import views.html.Jeux.listloby;


public class Jeuxapp extends Controller {
    
    @Inject
    FormFactory formFactory;
    MessagesApi messagesApi;
    Form<Jeux> jeuxForm;
	
	int n=-1;
	
    @Inject
    public Jeuxapp(FormFactory formFactory, MessagesApi messagesApi){
        this.jeuxForm = formFactory.form(Jeux.class);
        this.messagesApi = messagesApi;
    }

    //On affichage de la  page pour une nouvel  partie
    public Result newgame(Http.Request request) {
        //on test que  le mec  qui crée la  game est user  id  1
        //ici  on part du  principe  qu'on est  admin  pour  debugage
        if (true){ //((request.session().get("session").get()).equals("1")){//on verifi en fonction de l'id donc  faudra plus tard chercher dans la  bd
            return ok(views.html.Jeux.makeloby.render(jeuxForm,request, messagesApi.preferred(request)));
        }
        else {
            //message derreur qui  redirige ver page type
            return ok(views.html.messagetempo.render("Vous n'ète pas admin ===> Dégage !"));
        }
    }

    public Result lobyliste() {
        List<Jeux> liste = Jeux.find.all();
        return ok(listloby.render(liste));
    }


    //Page a la récéption du formulaire
    public Result resultatnewgame(Http.Request request) {
        //on  remplie le formulaire
        Form<Jeux> cForm = jeuxForm.bindFromRequest(request);
        //Si erreur réafficher la page contact avec les messages d'erreur
        if (cForm.hasErrors()) {
            return badRequest("ERROR bad request ;:"+cForm.toString());//views.html.Jeux.game.render(cForm, null, request, messagesApi.preferred(request)));
        }
        //Sinon afficher la page contact avec message stipulant que le message a bien été envoyé.
        else{
            //on transmet les variable du formulaire dans  jeux
            Jeux magame = cForm.get();

            //setUser1   peut  porter a  confusion  car  ici ces  l'id  d'user  qui est dans  les  loby

            //On va  dumper tout la base de donnée dans une liste car les requette ne fonctionne pas
            List<User> machin = User.find.all();

                //verifie si les champ sont vide
            if(magame.getPseudo1().equals("")){
                magame.setUser1(n--);
            }
            if(magame.getPseudo2().equals("")){
                magame.setUser2(n--);
            }

            for(User selecUser : machin) {  // on  va  lister chaque user

                if (selecUser.getPseudo().equals(magame.getPseudo1())) {//si l'utilisateur qui a le droit  de rejoindre la game existe dans la bd
                    magame.setUser1((int) selecUser.id);//on va chercher a stoquer l'identifiant de l'user demander pour  la  wheitlsliete
                }
                if (selecUser.getPseudo().equals(magame.getPseudo2())) {
                    magame.setUser2((int)selecUser.id);
                }
            }
            if(magame.getUser1()==0||magame.getUser2()==0||magame.getUser1()==magame.getUser2()){
                return badRequest(views.html.Jeux.makeloby.render(cForm, request,messagesApi.preferred(request)));
            }

            magame.save();
            return redirect("/");//views.html.Jeux.game.render(cForm, "Votre requête nous a bien été transmise et sera traitée dès que possible. Merci !", request, messagesApi.preferred(request)));
//            return redirect("/"+magame.id);//views.html.Jeux.game.render(cForm, "Votre requête nous a bien été transmise et sera traitée dès que possible. Merci !", request, messagesApi.preferred(request)));
        }
    }
//    public Result validsearch(long id){
//        return redirect("/lobyjoin/"+id);
//    }

    public Result joinlobyAffichage(long id){
        return ok("views.html.Jeux.");

    }

        public Result joinloby(Http.Request request,long id){ //join loby verrifi que l'utilisateur peut renter  mais est aussi la  main du game

        long idvisiteur  = Long.parseLong((request.session().get("session").get()));//avec le cookie on   cherche l'id de  l'user

        Jeux loby = Jeux.find.byId(id); //cherche le loby dans la bd en fonction de l'uri
        User actualuser = User.find.byId(idvisiteur);// j'isole la  classe user
//        return ok(""+loby.toString());

        if(loby.getUser1()<0 && (int)idvisiteur!=loby.getUser2()){ // es  id de  place libre

            loby.id =  id;
            loby.setUser1((int)idvisiteur);//peut  etre   erreur ?
            loby.setPseudo1(actualuser.getPseudo());
            loby.update();//2foix en  double peut opti
            return ok(views.html.Jeux.game.render("joueur 1",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else if(loby.getUser2()<0 && loby.getUser1()!=(int)idvisiteur){
            loby.setUser2((int)idvisiteur);//peut  etre   erreur ?
            loby.setPseudo2(actualuser.getPseudo());
            loby.update();
            return ok(views.html.Jeux.game.render("joueur 2",loby, actualuser, request, messagesApi.preferred(request)));
        }

        if(idvisiteur  == (loby.getUser1())){  //EQUALSE !! //que l'on va comparer avec les id autoriser du loby
            return ok(views.html.Jeux.game.render("joueur 1",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else if(idvisiteur  == (loby.getUser2())){
            return ok(views.html.Jeux.game.render("joueur 2",loby, actualuser, request, messagesApi.preferred(request)));
        }
        else{
            return ok(views.html.messagetempo.render("joueur non admi ===> Dégage !"));
        }

    }


    public Result checkactionloby(Http.Request request) {
        jeuxForm = formFactory.form(Jeux.class);
        return ok("action valide");
    }

//    public Result checkactionloby(Http.Request request) {
//        jeuxForm = formFactory.form(Jeux.class);
//        return redirect(/);
//    }

    public Result flush() {
        List<Jeux> u = Jeux.find.all(); // type inference works here!
        for(Jeux truc : u) {
            truc.delete();
        }
        return ok(views.html.messagetempo.render("supression base donnée des jeux"));
    }
 public static int getmatchencours() {
        return Jeux.find.all().size();
    }
}


