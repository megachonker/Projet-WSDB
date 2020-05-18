package controllers;

import play.mvc.*;
import java.util.List;
import play.mvc.*;
import play.data.*;
import javax.inject.Inject;
import views.html.*;
import models.*;
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
        return ok(views.html.Contact.contact.render(contactForm, null, request, messagesApi.preferred(request)));
} 
    //Page après envoi formulaire
    public Result resultatcontactform(Http.Request request) {
        Form<Contact> cForm = contactForm.bindFromRequest(request);
            //Si erreur réafficher la page contact avec les messages d'erreur
            if (cForm.hasErrors()) {
                return badRequest(views.html.Contact.contact.render(cForm, null, request, messagesApi.preferred(request)));
            }
            //Sinon afficher la page contact avec message stipulant que le message a bien été envoyé.
            else{
                Contact a = cForm.get();
                a.save();
                return ok(views.html.Contact.contact.render(cForm, "Votre requête nous a bien été transmise et sera traitée dès que possible. Merci !", request, messagesApi.preferred(request)));  
            }
    }
    
    //Page de la liste des message de contact qui sont stockées
    public Result listemsgformcontact(){
        List<Contact> liste = Contact.find.all();
        return ok(views.html.Contact.listemsgformcontact.render(liste));
    }
    
    //Page pour lire un message de contact
    public Result showcontactform(Long id) {
        Contact a = Contact.find.byId(id) ;
        return ok(views.html.Contact.showcontactform.render(a)) ;
    }
    
    //Suppression d'un message de contact de la base de données ENCORE CASSEE
    //public Result deletecontactform(Long id) {
      //  Contact a = Person.find.byId(id) ;
      //  a.delete();
      //  return ok(routes.Contactapp.deletecontactform(id)) ;
   // } 
    
}