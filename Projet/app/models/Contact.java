package controllers;

import models.*;
import play.mvc.*;
import play.data.*;
import java.util.List;
import javax.inject.Inject;
import play.i18n.MessagesApi; 


public class Contactapp extends Controller {
    
    //Création de formulaire
    
    @Inject 
    FormFactory formFactory;
    MessagesApi messagesApi;
    Form <Contact> contactForm;
    
    @Inject 
    public Contactapp(FormFactory formFactory, MessagesApi messagesApi){
        this.contactForm = formFactory.form(Contact.class);
        this.messagesApi = messagesApi;
    }
    
    //Page de contact
    public Result contact(Http.Request request) {
        return ok(views.html.Contact.contact.render(contactForm, request, messagesApi.preferred(request)));
} 
    //Page après envoi formulaire
    public Result resultatcontactform(Http.Request request) {
        Form<Contact> cForm = contactForm.bindFromRequest(request);
            //Si erreur réafficher la page contact avec les messages d'erreur
            if (cForm.hasErrors()) {
                return badRequest(views.html.Contact.contact.render(cForm, request, messagesApi.preferred(request)));
            }
            //Sinon afficher la page contact avec message stipulant que le message a bien été envoyé.
            else{
                Contact a = cForm.get();
                a.save();
                return ok(views.html.Contact.submission.render());  
            }
    }
    
    //Page de la liste des message de contact qui sont stockées
    public Result listemsgformcontact(Http.Request request){
        User u = User.find.byId(Long.parseLong((request.session().get("session").get())));
        List<Contact> liste = Contact.find.all();
        return ok(views.html.Contact.listemsgformcontact.render(u, liste));
    }
    
    //Page pour lire un message de contact
    public Result showcontactform(Http.Request request, Long id) {
        User u = User.find.byId(Long.parseLong((request.session().get("session").get())));
        List<Contact> liste = Contact.find.all();
        Contact a = Contact.find.byId(id) ;
        return ok(views.html.Contact.showcontactform.render(u, a)) ;
    }
    
    //Suppression d'un message de contact de la base de données
    public Result deletecontactform(Long id) {
       Contact a = Contact.find.byId(id) ;
        a.delete();
        return redirect(routes.Contactapp.listemsgformcontact()) ;
    } 
      
    //Suppression de tous les messages de contact
    public Result flush() {
        List<Contact> liste = Contact.find.all();
        for(Contact c : liste) {
            c.delete();
        }
        return redirect(routes.Contactapp.listemsgformcontact());
    }
    
    //Page de redirection après envoi  
    public Result submission() {
        return ok(views.html.Contact.submission.render()) ;
    }
    
}
