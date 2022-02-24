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
    /**
     * Todos los datos de los items válidos están almacenados aquí
     */
    public final static Map<String, ItemKills> itemKills;

    static {
        itemKills = new HashMap<>();
    }

    /**
     * Obtiene un string con la información de todos los items válidos en la
     * memoria
     *
     * @return String
     */
    public static String getItemMapStr() {
        String result = "Item stats:\n";
        result = itemKills.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().toString() + "\n").reduce(result, String::concat);
        return result;
    }

    /**
     * Obtener los datos de un item utilizando su UUID almacenado en su NBT
     *
     * @param UUID La UUID del item a buscar
     * @return ItemKills object, ya sea uno existente, o uno nuevo.
     */
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
    public void updateDB() {
        ItemDB sql = new ItemDB();
        sql.updateTotalMobs(this.getUUID(), this.getTotalMobs());
        sql.updateZombies(this.getUUID(), this.getZombies());
        sql.updatePlayers(this.getUUID(), this.getPlayers());
    }

}
