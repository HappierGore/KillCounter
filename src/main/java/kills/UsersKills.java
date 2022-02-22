package kills;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HappierGore
 */
public final class UsersKills extends Kills {

    //*****************************
    // STATIC
    //*****************************
    public final static Map<String, UsersKills> playerKills;

    static {
        playerKills = new HashMap<>();
    }

    public static String getItemMapStr() {
        String result = "Players stats:\n";
        for (Map.Entry<String, UsersKills> entry : playerKills.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue().toString() + "\n";
        }
        return result;
    }
    public static UsersKills getObj(String UUID) {
        UsersKills pKills = UsersKills.playerKills.containsKey(UUID) ? UsersKills.playerKills.get(UUID) : new UsersKills(UUID);

        if (!UsersKills.playerKills.containsKey(UUID)) {
            UsersKills.playerKills.put(UUID, pKills);
        }
        return pKills;
    }

    //Constructor
    public UsersKills(String UUID) {
        super(UUID);
    }

}
