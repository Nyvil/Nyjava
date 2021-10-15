package uk.co.nyvil.utils;

import com.google.gson.JsonObject;
import com.mongodb.DBCollection;
import net.dv8tion.jda.api.entities.User;
import uk.co.nyvil.Bot;
import uk.co.nyvil.database.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nyvil on 11/06/2021 at 21:48
 * in project Discord Base
 */

public final class PermissionUtils {

    public static List<Long> staff = new ArrayList<>();

    private PermissionUtils() {
        throw new AssertionError("We don't instantiate util classes, bad, don't!");
    }

    /**
     * Returns whether the user is a bot-staff
     *
     * @param user - user to check permission level for
     * @return permission level
     * @since 1.0
     */
    public static boolean isHighStaff(User user) {
        if(staff.contains(user.getIdLong())) {
            return true;
        }
        DBCollection dbCollection = DatabaseUtils.getCollection(DatabaseUtils.getDatabase(Bot.getInstance().getDb()), "staff_team");
        JsonObject obj = DatabaseUtils.getAsJsonObject("_id", user.getId(), dbCollection);
        if(obj != null) {
            return obj.get("permission_level").getAsInt() == 0;
        } else {
            return false;
        }
    }
}

