package fr.orleans.miage.iisi.modele;

import fr.orleans.miage.iisi.modele.model.Dico;
import fr.orleans.miage.iisi.modele.repositories.DicoRepository;
import fr.orleans.miage.iisi.modele.repositories.PartieRepository;
import fr.orleans.miage.iisi.modele.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitDico implements ApplicationRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartieRepository partieRepository;

    @Autowired
    private DicoRepository dicoRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        initDico("dico7lettres");
        System.out.println("oui");
        initDico("dicosimple7lettres");
    }

    public void initDico(String dicoName){
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(dicoName+".txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        List<String> liste = new ArrayList<>();
        buffer.lines().map(String::toUpperCase).forEach(liste::add);
        Dico dico = new Dico(dicoName);
        dico.setListMots(liste);
        dicoRepository.save(dico);
    }
}
