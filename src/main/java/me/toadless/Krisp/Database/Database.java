package me.toadless.Krisp.Database;

import com.mongodb.*;

public class Database {
    public static MongoClient mongoClient;
    public static DBCollection guild;
    public static DB database;
    public static void main() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://toadless:ss77yWRX2za1DWkn@cluster0.hfsfa.mongodb.net"));
        database = mongoClient.getDB("Krisp");
        guild = database.getCollection("guilds");
    }
}
