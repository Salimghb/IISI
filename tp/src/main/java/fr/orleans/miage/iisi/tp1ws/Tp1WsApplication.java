package fr.orleans.miage.iisi.tp1ws;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Tp1WsApplication {

    private static final String BASEURL = "http://localhost:8081";

    static Client client = ClientBuilder.newClient(new ClientConfig(LoggingFilter.class));

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Tp1WsApplication.class, args);

        String pseudo = "";
        boolean connecte = false;

        while (true){

            Scanner s = new Scanner(System.in);

            if(!connecte){
                do {
                    System.out.println("Entrez votre pseudo :");
                    pseudo = s.nextLine();
                } while (!connexion(pseudo));
                connecte = true;
            }

            System.out.println("(a) Créer une partie");
            System.out.println("(b) Liste des coups précédents");
            System.out.println("(c) Jouer un coup sur la partie actuelle");
            System.out.println("(d) Liste des dictionnaires");
            System.out.println("(q) Déconnexion");

            String choix = s.nextLine();
            switch (choix){
                case "a":
                    System.out.println("Liste des dictionnaires :");
                    listeDicos();
                    System.out.println("Nom du dictionnaire choisi :");
                    String dicoName = s.nextLine();
                    if(!creerPartie(pseudo, dicoName)) connecte = false;
                    s.nextLine();
                    break;
                case "b":
                    if(!listeCoupsPrecedents(pseudo)) connecte = false;
                    s.nextLine();
                    break;
                case "c":
                    System.out.println("Proposition :");
                    String mot = s.nextLine();
                    if(!jouer(pseudo, mot)) connecte = false;
                    s.nextLine();
                    break;
                case "d":
                    listeDicos();
                    s.nextLine();
                    break;
                case "q":
                    if(!deconnexion(pseudo)){
                        connecte = false;
                        break;
                    }
                    SpringApplication.exit(ctx);
                    return;
            }

        }

    }

    //PUT
    private static boolean jouer(String pseudo, String mot) {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus/partie/" + pseudo)
                .request(MediaType.APPLICATION_JSON)
                .property("mot", mot)
                .put(Entity.entity(mot, MediaType.APPLICATION_JSON));

        System.out.println(response.readEntity(String.class));

        if(response.getStatus() == HttpStatus.BAD_REQUEST.value()
                && "Vous n'êtes pas connecté !".equals(response.toString())) return false;

        return true;
    }

    //POST
    private static boolean connexion(String pseudo) {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus")
                .request(MediaType.APPLICATION_JSON)
                .property("pseudo", pseudo)
                .post(Entity.entity(pseudo, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            return false;
        }

        return true;
    }

    //POST
    private static boolean creerPartie(String pseudo, String dicoName) {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus/partie/" + pseudo)
                .request(MediaType.APPLICATION_JSON)
                .property("dicoName", dicoName)
                .post(Entity.entity(dicoName, MediaType.APPLICATION_JSON));

        System.out.println(response.readEntity(String.class));

        if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            return false;
        }

        return true;
    }

    //DELETE
    private static boolean deconnexion(String pseudo) {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus/" + pseudo)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        System.out.println(response.readEntity(String.class));

        if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            return false;
        }

        return true;
    }

    //GET
    private static boolean listeCoupsPrecedents(String pseudo) {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus/partie/" + pseudo)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if(response.getStatus() == HttpStatus.OK.value()) {
            System.out.println("Liste des coups précédents :");
            response.readEntity(List.class).forEach(string -> System.out.println("- " + string));
        }

        if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            System.out.println(response.readEntity(String.class));
            return false;
        }

        return true;
    }

    //GET
    private static void listeDicos() {

        //Appel REST
        Response response = client
                .target(BASEURL)
                .path("/motus/dicos")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if(response.getStatus() == HttpStatus.OK.value()) {
            System.out.println("Liste des dictionnaires disponibles :");
            response.readEntity(Collection.class).forEach(string -> System.out.println("- " + string));
        }

    }

}
