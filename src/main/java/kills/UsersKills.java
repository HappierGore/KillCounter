package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.PlayerDB;

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
        result = playerKills.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().toString() + "\n").reduce(result, String::concat);
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

    @Override
    public void updateDB(String dbPath) {
         PlayerDB sql = new PlayerDB(dbPath.replace('\\', '/') + "/KillCounter.db");
        sql.updateTotalMobs(this.getUUID(), this.getTotalMobs());
        sql.updateZombies(this.getUUID(), this.getZombies());
        sql.updatePlayers(this.getUUID(), this.getPlayers());
    }

}
