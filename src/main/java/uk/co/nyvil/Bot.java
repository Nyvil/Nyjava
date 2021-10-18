package uk.co.nyvil;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import uk.co.nyvil.bot.BotConnection;
import uk.co.nyvil.bot.commands.manager.CommandHandler;
import uk.co.nyvil.database.DatabaseConnection;
import uk.co.nyvil.database.utils.DatabaseUtils;
import uk.co.nyvil.utils.FileUtils;
import uk.co.nyvil.utils.MessageUtils;
import uk.co.nyvil.utils.PermissionUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Nyvil on 11/06/2021 at 21:16
 * in project Discord Base
 */

@Getter
@Setter
public class Bot {

    @Getter
    private static List<String> log = new ArrayList<>();
    @Getter
    private CommandHandler commandHandler;
    @Getter
    private DatabaseConnection databaseConnection;
    @Getter
    private BotConnection botConnection;
    @Getter
    private DatabaseUtils databaseUtils;
    @Getter
    private String db, dbUser, dbPwd, dbPort, host, token;
    @Getter
    private boolean dbStart;
    @Getter
    private static final Bot instance = new Bot();

    public static void main(String[] args) {
        try {
            getInstance().createDir();
            FileUtils.getToken();
            FileUtils.startup();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        getInstance().start();
        getInstance().setStaff();
    }

    /**
     * Method to initialise the instance of the bot and the instances for the handlers
     */
    private void start() {
        if (this.getToken() == null) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Please enter your bot's token:");
                String token = scanner.nextLine();
                try {
                    FileUtils.setToken(token);
                    botConnection = new BotConnection();
                    commandHandler = new CommandHandler();
                    if (getInstance().dbStart) {
                        databaseConnection = new DatabaseConnection();
                    } else {
                        System.out.println("Couldn't connect to database, review the database file and restart.");
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        } else {
            botConnection = new BotConnection();
            commandHandler = new CommandHandler();
            if (getInstance().dbStart) {
                databaseConnection = new DatabaseConnection();
            } else {
                System.out.println("Couldn't connect to database, review the database file and restart.");
            }
        }
    }

    /**
     * File to get the path of the jar
     *
     * @return the path where the jar is located
     * @throws URISyntaxException if URI can't be parsed, so basically if the directory isn't found
     */
    public Path getDirPath() throws URISyntaxException {
        return new File(Bot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toPath();
    }

    /**
     * Creates the root dir of the bot
     *
     * @throws URISyntaxException {@link #getDirPath()} for reason
     */
    private void createDir() throws URISyntaxException {
        final File rootDir = new File(getDirPath() + File.separator + "Bot");
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
    }

    private void setStaff() {
        DatabaseUtils.getCollection(DatabaseUtils.getDatabase(db), "staff_team").find().forEach(p -> {
            JsonObject obj = DatabaseUtils.getAsJsonObject("_id", (String) p.get("_id"), DatabaseUtils.getCollection(DatabaseUtils.getDatabase(db), "staff_team"));
            PermissionUtils.staff.add(obj != null ? obj.get("_id").getAsLong() : 0);
            User user = BotConnection.getJda().retrieveUserById(obj.get("_id").getAsString()).complete();
            System.out.println("Added " + obj.get("_id").getAsString() + "(" + user.getName() + ") as staff!");
        });
    }
}
