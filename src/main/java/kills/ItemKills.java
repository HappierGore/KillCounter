package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.ItemDB;

/**
 *
 * @author HappierGore
 */
public final class ItemKills extends Kills {

    //*****************************
    // STATIC
    //*****************************
    public final static Map<String, ItemKills> itemKills;

    static {
        itemKills = new HashMap<>();
    }

    public static String getItemMapStr() {
        String result = "Item stats:\n";
        result = itemKills.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().toString() + "\n").reduce(result, String::concat);
        return result;
    }

    public static ItemKills getObj(String UUID) {
        ItemKills iKills = ItemKills.itemKills.containsKey(UUID) ? ItemKills.itemKills.get(UUID) : new ItemKills(UUID);

        if (!ItemKills.itemKills.containsKey(UUID)) {
            ItemKills.itemKills.put(UUID, iKills);
        }
        return iKills;
    }

    //Constructor
    public ItemKills(String UUID) {
        super(UUID);
    }

    @Override
    public void updateDB(String dbPath) {
        ItemDB sql = new ItemDB(dbPath.replace('\\', '/') + "/KillCounter.db");
        sql.updateTotalMobs(this.getUUID(), this.getTotalMobs());
        sql.updateZombies(this.getUUID(), this.getZombies());
        sql.updatePlayers(this.getUUID(), this.getPlayers());
    }

}
