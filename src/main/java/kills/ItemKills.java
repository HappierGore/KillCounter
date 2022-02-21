package kills;

import java.util.HashMap;
import java.util.Map;
import mysqlite.MySQLite;
import org.bukkit.entity.EntityType;

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
        for (Map.Entry<String, ItemKills> entry : itemKills.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue().toString() + "\n";
        }
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

}
