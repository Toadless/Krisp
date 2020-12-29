package me.toadless.Krisp.Database;

import com.mongodb.*;
import me.toadless.Krisp.Utils.Config;

public class Database {
    public static MongoClient mongoClient;
    public static DBCollection guild;
    public static DB database;
    public static void main() {
        mongoClient = new MongoClient(new MongoClientURI(Config.database));
        database = mongoClient.getDB("Krisp");
        guild = database.getCollection("guilds");
    }
}
