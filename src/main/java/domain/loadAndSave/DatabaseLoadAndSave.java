package domain.loadAndSave;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseLoadAndSave implements ILoadAndSaveAdapter{

    @Override
    public void saveGame(ArrayList<String> saveList, String username) {
        /* components of the list:
         elapsed time
         remaining chances
         current score
         currently unavailable obstacleTypeList
         currently unavailable giftList
         noble phantasm location rotation
         enchanted sphere isshot coordinates and speed
         obstacles on the screen (type location speed)
        */

        Document document= new Document();

        document.append("name",username);
        document.append("time",saveList.get(0));
        document.append("chance",saveList.get(1));
        document.append("score",saveList.get(2));
        document.append("NP",saveList.get(3));
        document.append("ES",saveList.get(4));

        for(int i = 0; i< saveList.size()-5;i++){
            document.append("body"+(i+1),saveList.get(i+5));
        }


        ConnectionString connectionString = new ConnectionString("mongodb+srv://runtimeterror:noblehabibi302@runtimeterror.vzhou.mongodb.net/runtimeterror?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("runtimeterror");
        MongoCollection<Document> collection =database.getCollection("needforspear");
        collection.deleteOne(eq("name", username));
        collection.insertOne(document);
        System.out.println("Game is saved to needforspear collection in runtimeterror database");
    }

    @Override
    public ArrayList<String> loadGame(String username) {
        ArrayList<String> loadList = new ArrayList<>();

        ConnectionString connectionString = new ConnectionString("mongodb+srv://runtimeterror:noblehabibi302@runtimeterror.vzhou.mongodb.net/runtimeterror?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("runtimeterror");
        MongoCollection<Document> collection =database.getCollection("needforspear");
        Document document = collection.find(eq("name", username)).first();

        if(document==null) {
            return loadList;
        }

        loadList.add(document.get("time").toString());
        loadList.add(document.get("chance").toString());
        loadList.add(document.get("score").toString());
        loadList.add(document.get("NP").toString());
        loadList.add(document.get("ES").toString());

        int bodyNumber = document.size()-7;
        for (int i=1;i<bodyNumber+1;i++){
            loadList.add(document.get("body"+i).toString());
        }
        System.out.println("Game is loaded from the needforspear collection of runtimeterror database");

        return loadList;
    }
}
