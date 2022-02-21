package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.MySQLite;
import org.bukkit.entity.EntityType;

/**
 *
 * @author HappierGore
 */
public final class ItemKills implements Kills {

    private int players = 0;
    private int zombies = 0;

    private int totalMobs = 0;

    private final String UUID;

    //*****************************
    // STATIC
    //*****************************
    public final static Map<String, ItemKills> itemKills;

    static {
        itemKills = new HashMap<>();
    }

    public static String getItemMapStr() {
        String result = "Item stats:\n";
        for (Map.Entry<String, ItemKills> entry : itemKills.entrySet()) {
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
        sql.updateTotalMobs(this.UUID, this.totalMobs, true);
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
    public static ItemKills getObj(String UUID) {
        ItemKills iKills = ItemKills.itemKills.containsKey(UUID) ? ItemKills.itemKills.get(UUID) : new ItemKills(UUID);

        if (!ItemKills.itemKills.containsKey(UUID)) {
            ItemKills.itemKills.put(UUID, iKills);
        }
        return iKills;
    }

    //Constructor
    public ItemKills(String UUID) {
        this.UUID = UUID;
    }

}
