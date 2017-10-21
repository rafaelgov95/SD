import br.Login;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Main {

    public static void main(String[]args ){
        final Morphia morphia = new Morphia();
//        morphia.getMapper().getOptions().setMapSubPackages(true);
        morphia.mapPackage("br");
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "BuscaCao");
        datastore.getDB().dropDatabase();
        datastore.ensureIndexes();

        final Login l = new Login("azul", "31231231");

        final Login l2 = new Login("azul", "31231231");
        datastore.save(l);
        datastore.save(l2);
    }
}
