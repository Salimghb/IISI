package fr.orleans.miage.iisi.modele;

import fr.orleans.miage.iisi.modele.facade.FacadeMotus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class InitDico implements ApplicationRunner {

    private FacadeMotus facade;

    @Autowired
    public InitDico(FacadeMotus facade) {
        this.facade = facade;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.facade.initDico("dico7lettres");
        this.facade.initDico("dicosimple7lettres");
    }
}
