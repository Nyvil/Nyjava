package uk.co.nyvil.database;

/**
 * Created by Nyvil on 11/06/2021 at 21:39
 * in project Discord Base
 */

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.Getter;
import lombok.Setter;
import uk.co.nyvil.Bot;

@Getter
@Setter
public class DatabaseConnection {

    @Getter
    private static MongoClient client;

    private String db = Bot.getInstance().getDb();
    private String dbUser = Bot.getInstance().getDbUser();
    private String dbPwd = Bot.getInstance().getDbPwd();
    private String dbPort = Bot.getInstance().getDbPort();
    private String host = Bot.getInstance().getHost();


    public DatabaseConnection() {
        client = new MongoClient(new MongoClientURI(String.format("mongodb://%s:%s@%s:%s/%s?authSource=admin", dbUser, dbPwd, host, dbPort, db))); // -> authSource=admin needs to be changed if you created the account in another database
        System.out.println(client);
    }
}