package uk.co.nyvil.database.utils;

import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import uk.co.nyvil.database.DatabaseConnection;

import java.lang.reflect.Type;

/**
 * Util class to access the Database
 *
 * @since 1.0 / 1/22/2021 by Nyvil
 */
public final class DatabaseUtils {

    // Prevent construction :/
    private DatabaseUtils() {
        throw new AssertionError("no uk.co.nyvil.database.utils.DatabaseUtils instance for you!");
    }

    /**
     * Get's the Database you want to write to if it exists
     *
     * @param name Database you're accessing
     * @return database
     * @since 1.0
     */
    public static DB getDatabase(String name) {
        return DatabaseConnection.getClient().getDB(name);
    }

    /**
     * Method to use a MongoDatabase instead of a DB, as in {@link #getDatabase(String)}
     * @param name name of database
     * @return MongoDatabase
     * @since 1.7
     */
    public static MongoDatabase getMongoDatabase(String name) {
        return DatabaseConnection.getClient().getDatabase(name);
    }

    /**
     * Returns the collection if it exists
     *
     * @param db db the collection is in
     * @param name the name of the collection
     * @return Collection
     * @since 1.0
     */
    public static DBCollection getCollection(DB db, String name) {
        return db.getCollection(name);
    }

    /**
     * method to insert objects into the database, typically used for single Objects (DBObject)
     *
     * @param collection collection to insert to
     * @param dbObject object the database can insert
     * @since 1.0
     */
    public static void insertInto(DBCollection collection, DBObject dbObject) {
        collection.insert(dbObject);
    }

    /**
     * Inserts the document into the database, typically moved for bulk inserts (e.g. Arrays)
     *
     * @param db Database you want to insert into
     * @param collection collection you want to insert into
     * @param document the document you want to insert
     * @since 1.2
     */
    public static void insertInto(MongoDatabase db, String collection, Document document) {
        db.getCollection(collection).insertOne(document);
    }

    /**
     * method to modify an existing entry
     *
     * @param collection collection
     * @param oldData old object
     * @param newData object you want to replace with
     * @since 1.1
     */
    public static void findAndModify(DBCollection collection, DBObject oldData, DBObject newData) {
        collection.findAndModify(oldData, newData);
    }

    /**
     * method to modify an existing entry with a Document instead of a DBObject as third parameter
     *
     * @param collection collection
     * @param oldData old object
     * @param document document you want to replace the old object with
     * @since 1.5
     */
    public static void findAndModify(DBCollection collection, DBObject oldData, Document document) {
        collection.findAndModify(oldData, BasicDBObject.parse(document.toJson()));
    }

    /**
     * method to remove objects from the database
     *
     * @param collection collection to remove from
     * @param dbObject object the database can remove
     * @since 1.0
     */
    public static void removeFrom(DBCollection collection, DBObject dbObject) {
        collection.remove(dbObject);
    }

    /**
     * Returns the method as String
     *
     * @param key key the value is assigned to, example given: "_id"
     * @param value your input, for example the user's id, 480294716764848128
     * @param collection location it's stored in
     * @return Value
     * @since 1.0
     */
    public static String get(String key, String value, DBCollection collection) {
        DBObject query = new BasicDBObject(key, value);
        DBCursor cursor = collection.find(query);
        DBObject result = cursor.one();
        if (result != null) {
            return result.toString();
        }
        return "";
    }

    /**
     * Returns the Query as JsonObject so it can be read more easily
     *
     * @param key the value is assigned to, example given: "_id"
     * @param value the key is assigned to, for example a user's id, 480294716764848128
     * @param collection collection the key and value are stored in
     * @return JsonObject
     * @since 1.1
     */
    public static JsonObject getAsJsonObject(String key, String value, DBCollection collection) {
        DBObject query = new BasicDBObject(key, value);
        DBCursor cursor = collection.find(query);
        DBObject result = cursor.one();
        if (result != null) {
            return new GsonBuilder().setPrettyPrinting().create().fromJson(result.toString(), JsonObject.class);
        }
        return null;
    }

    /**
     * @see DatabaseUtils#getAsJsonObject(String, String, DBCollection) for explanation
     */
    public static JsonObject getAsJsonElement(String key, String value, DBCollection collection) {
        DBObject query = new BasicDBObject(key, value);
        DBCursor cursor = collection.find(query);
        DBObject result = cursor.one();
        if (result != null) {
            return new GsonBuilder().setPrettyPrinting().create().fromJson(result.toString(), (Type) JsonElement.class);
        }
        return null;
    }

    /**
     * Returns the query as BasicDBObject which then can be modified
     *
     * @param key key the value is assigned to, example given: "_id"
     * @param value value the key is assigned to, for example a user's id, 480294716764848128
     * @param collection collection the key and value are stored in
     * @return DatabaseObject
     * @since 1.3
     */
    public static BasicDBObject getAsBDBObject(String key, String value, DBCollection collection) {
        BasicDBObject query = new BasicDBObject(key, value);
        DBCursor cursor = collection.find(query);
        return (BasicDBObject) cursor.one();
    }

    /**
     * Validates whether an entry already exists
     *
     * @param key key the value is assigned to, example given: "_id"
     * @param value value the key is assigned to, for example a user's id, 480294716764848128
     * @param collection collection the key and value are stored in
     * @return true/false
     * @since 1.4
     */
    public static boolean exists(String key, String value, DBCollection collection) {
        DBObject query = new BasicDBObject(key, value);
        DBCursor cursor = collection.find(query);
        DBObject result = cursor.one();
        return result != null;
    }

    /**
     * method to deal with mongodb's failure of saving a jsonarray
     *
     * @param toParse JsonObject you want to parse from
     * @param member array you want to parse
     * @return JsonElement
     * @since 1.6 - 12/04/2021
     */
    public static JsonElement parse(JsonObject toParse, String member) {
        if(toParse != null) {
            String replacement = toParse.get(member).getAsString().replaceAll("\\\\", "");
            return new JsonParser().parse(replacement);
        } else {
            throw new IllegalStateException("Object is not a JsonObject or null");
        }
    }
}
