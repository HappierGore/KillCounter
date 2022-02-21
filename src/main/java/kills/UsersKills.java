package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.MySQLite;
import org.bukkit.entity.EntityType;

/**
 *
 * @author HappierGore
 */
public final class UsersKills implements Kills {

    private int players = 0;
    private int zombies = 0;

    private int totalMobs = 0;

    private final String UUID;

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

    //*****************************
    //INTERFACE
    //*****************************
    @Override
    public int get(String request) {
        switch (request.toLowerCase()) {
            case "players" -> {
                return this.players;
            }
            case "zombies" -> {
                return this.zombies;
            }
            case "totalmobs" -> {
                return this.totalMobs;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public void add(EntityType entityType) {
        switch (entityType) {
            case PLAYER -> {
                totalMobs++;
                players++;
            }
            case ZOMBIE -> {
                totalMobs++;
                zombies++;
            }
            default -> {
                totalMobs++;
            }
        }
    }

    @Override
    public void updateDB(String dbPath) {
        MySQLite sql = new MySQLite(dbPath.replace('\\', '/') + "/KillCounter.db");
        sql.updateTotalMobs(this.UUID, this.totalMobs, false);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kills{");
        sb.append("totalMobs=").append(totalMobs);
        sb.append(", players=").append(players);
        sb.append(", zombies=").append(zombies);
        sb.append(",Memory=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    //*****************************
    // CLASS
    //*****************************
    public static UsersKills getObj(String UUID) {
        UsersKills pKills = UsersKills.playerKills.containsKey(UUID) ? UsersKills.playerKills.get(UUID) : new UsersKills(UUID);

        if (!UsersKills.playerKills.containsKey(UUID)) {
            UsersKills.playerKills.put(UUID, pKills);
        }
        return pKills;
    }

    //Constructor
    public UsersKills(String UUID) {
        this.UUID = UUID;
    }

}
