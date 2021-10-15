package uk.co.nyvil.bot.listeners;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import uk.co.nyvil.Bot;
import uk.co.nyvil.database.utils.DatabaseUtils;
import uk.co.nyvil.utils.PermissionUtils;

public class ReactionListener extends ListenerAdapter {


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getMember() == null) {
            return;
        }
        if(event.getMember().getUser().isBot()) {
            return;
        }

        if(!PermissionUtils.isHighStaff(event.getMember().getUser())) {
            return;
        }

        JsonObject jsonObject = DatabaseUtils.getAsJsonObject("_id", event.getMessageId(), DatabaseUtils.getCollection(DatabaseUtils.getDatabase(Bot.getInstance().getDb()), "messages"));
        if(event.getMessageId().equals(jsonObject.get("_id").getAsString())) {
            DBObject obj = DatabaseUtils.getAsBDBObject("_id", jsonObject.get("account").getAsString(), DatabaseUtils.getCollection(DatabaseUtils.getDatabase(Bot.getInstance().getDb()), "customers"));
            DBObject newObj = new BasicDBObject("_id", obj.get("_id"))
                    .append("accounts", obj.get("accounts"))
                    .append("price", obj.get("price"))
                    .append("paid", obj.get("paid"))
                    .append("date", obj.get("date"))
                    .append("given", obj.get("given"))
                    .append("notified", false);

            DBObject messageObject = new BasicDBObject("_id", event.getMessageId()).append("account", obj.get("_id"));
            DatabaseUtils.removeFrom(DatabaseUtils.getCollection(DatabaseUtils.getDatabase(Bot.getInstance().getDb()), "messages"), messageObject);
            DatabaseUtils.findAndModify(DatabaseUtils.getCollection(DatabaseUtils.getDatabase(Bot.getInstance().getDb()), "customers"), obj, newObj);
        }
    }
}
