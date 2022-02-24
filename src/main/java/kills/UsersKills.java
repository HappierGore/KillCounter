package kills;

import de.tr7zw.nbtapi.NBTItem;
import java.util.HashMap;
import java.util.Map;
import mysqlite.PlayerDB;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

/**
 *
 * @author HappierGore
 */
public final class UsersKills extends Kills {

    @Override
    public void add(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            Player killer = (Player) e.getEntity().getKiller();
            Player victim = (Player) e.getEntity();
            KillControl control =  KillControl.getObj(killer, victim.getUniqueId().toString());
            if(!control.checkPlayerKill(killer, victim)){
                return;
            }
        }

        Player killer = (Player) e.getEntity().getKiller();

        final ItemStack item = killer.getInventory().getItemInMainHand();

        if (item.getType() != Material.AIR) {

            NBTItem nbti = new NBTItem(item);

            if (!nbti.hasKey("UUID")) {
                nbti.setString("UUID", UUID.randomUUID().toString());
                killer.getInventory().removeItem(item);
                killer.getInventory().addItem(nbti.getItem());
            }

            String uuid = nbti.getString("UUID");

            ItemKills objKills = ItemKills.getObj(uuid);

            objKills.add(e);
            objKills.updateDB();
        }

        super.add(e);
    }

    //*****************************
    // STATIC
    //*****************************
    //
    /**
     * Todos los datos de los usuarios en línea están almacenados aquí
     */
    public final static Map<String, UsersKills> playerKills;

    static {
        playerKills = new HashMap<>();
    }

    /**
     * Obtiene un string con la información de los jugadores activos en la
     * memoria
     *
     * @return String con toda la información de todos los jugadores activos
     */
    public static String getItemMapStr() {
        String result = "Players stats:\n";
        result = playerKills.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().toString() + "\n").reduce(result, String::concat);
        return result;
    }

    /**
     * Obtener los datos de un usuario utilizando su UUID
     *
     * @param UUID La UUID del jugador a buscar
     * @return UsersKill object, ya sea uno existente, o uno nuevo.
     */
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
    public void updateDB() {
        PlayerDB sql = new PlayerDB();
        sql.updateTotalMobs(this.getUUID(), this.getTotalMobs());
        sql.updateZombies(this.getUUID(), this.getZombies());
        sql.updatePlayers(this.getUUID(), this.getPlayers());
    }

}
