package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.MySQLite;
import org.bukkit.entity.EntityType;

/**
 *
 * @author HappierGore
 */
public class Kills {

    public final static Map<String, Kills> playerKills;
    public final static Map<String, Kills> objKills;

    private int totalMobs = 0;
    private int players = 0;
    private int zombies = 0;

    private String UUID = "";

    static {
        playerKills = new HashMap<>();
        objKills = new HashMap<>();
    }

    public Kills(String UUID) {
        this.UUID = UUID;
    }

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

    public int getTotalMobs() {
        return totalMobs;
    }

    public int getPlayers() {
        return players;
    }

    public int getZombies() {
        return zombies;
    }

    public static String getItemMapStr() {
        String result = "";
        for (Map.Entry<String, Kills> entry : objKills.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue().toString() + "\n";
        }
        return result;
    }

    public void updateDB(String path, boolean isItem) {
        MySQLite sql = new MySQLite(path.replace('\\', '/') + "/KillCounter.db");
        sql.updateTotalMobs(this.UUID, totalMobs, isItem);
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

}
