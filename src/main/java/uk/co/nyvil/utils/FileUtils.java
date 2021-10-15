package uk.co.nyvil.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import uk.co.nyvil.Bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Created by Nyvil on 11/06/2021 at 21:19
 * in project Discord Base
 */

public final class FileUtils {


    @SneakyThrows
    public static void startup() {
        final File mongDbFile = new File(Bot.getInstance().getDirPath() + File.separator + "Bot", "MongoDB.json");

        if (!mongDbFile.exists()) {
            try {
                mongDbFile.createNewFile();
                try (BufferedWriter bw = Files.newBufferedWriter(mongDbFile.toPath())) {
                    final JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("dbStart", false);
                    jsonObject.addProperty("db", "dbname");
                    jsonObject.addProperty("dbUser", "user");
                    jsonObject.addProperty("dbPwd", "password");
                    jsonObject.addProperty("host", "ip mongo is running on");
                    jsonObject.addProperty("dbPort", "port mongo is listening on");
                    bw.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
                    Bot.getInstance().setDbStart(false);
                    System.out.println("Review the database file and insert the values (Restart afterwards)");
                }
            } catch (IOException e) {
                System.err.println("Couldn't get database");
            }
        }

        if (mongDbFile.length() != 0) {
            try (BufferedReader br = Files.newBufferedReader(mongDbFile.toPath())) {
                final JsonObject obj = new JsonParser().parse(br).getAsJsonObject();

                final boolean dbStart = obj.get("dbStart").getAsBoolean();
                final String db = obj.get("db").getAsString();
                final String dbUser = obj.get("dbUser").getAsString();
                final String dbPwd = obj.get("dbPwd").getAsString();
                final String host = obj.get("host").getAsString();
                final String dbPort = obj.get("dbPort").getAsString();
                Bot.getInstance().setDbStart(dbStart);
                Bot.getInstance().setDb(db);
                Bot.getInstance().setDbUser(dbUser);
                Bot.getInstance().setDbPwd(dbPwd);
                Bot.getInstance().setHost(host);
                Bot.getInstance().setDbPort(dbPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getToken() throws URISyntaxException {
        final File file = new File(Bot.getInstance().getDirPath() + File.separator + "Bot", "token.json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (file.length() != 0) {
            try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
                final JsonObject obj = new JsonParser().parse(br).getAsJsonObject();

                final String token = obj.get("token").getAsString();
                Bot.getInstance().setToken(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setToken(String token) throws URISyntaxException {
        final File file = new File(Bot.getInstance().getDirPath() + File.separator + "Bot", "token.json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);
            Bot.getInstance().setToken(token);
            bw.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
        } catch (IOException e) {
            System.err.println("Couldn't write token");
            e.printStackTrace();
        }
    }

}
