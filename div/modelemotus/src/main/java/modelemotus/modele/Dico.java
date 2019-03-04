package modelemotus.modele;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Fred on 02/06/2016.
 */
@Entity
@Data
@NoArgsConstructor
public class Dico {

    @Id
    @GeneratedValue
    private long idDico;

    public static final String DEFAULT_FILENAME = "dicosimple7lettres";

    private volatile static Map<String, Dico> instanceByFile = new HashMap<>();

    @ElementCollection
    private List<String> listMots = new ArrayList<>();



    private Dico(String filePath) {
        BufferedReader buffer = null;
//        filePath = (Dico.class).getResource(filePath+".txt").getFile();
        //   = new BufferedReader(new FileReader(filePath));

        InputStream in = (InputStream) (Dico.class.getResourceAsStream(filePath+".txt"));
        buffer = new BufferedReader(new InputStreamReader(in));
        try (Stream<String> stream = buffer.lines()) {
            listMots = stream
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        }
    }

    public static Dico getInstance(String filePath) {
        if (!instanceByFile.containsKey(filePath)) {
            synchronized (Dico.class) {
                if (!instanceByFile.containsKey(filePath)) {
                    instanceByFile.put(filePath, new Dico(filePath));
                }
            }
        }
        return instanceByFile.get(filePath);
    }

    public String getRandomMot() {
        int num = (int) (Math.random() * listMots.size());
        return listMots.get(num);
    }

    public boolean isMot(String mot) {
        return listMots.contains(mot);
    }

    public List<String> getListMots() {
        return listMots;
    }
}
