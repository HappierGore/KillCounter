/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import de.tr7zw.nbtapi.NBTItem;
import kills.ItemKills;
import mysqlite.MySQLite;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author HappierGore
 */
public class SomeDamaged {

    public static void onDamaged(EntityDamageEvent e, String dbPath) {
        if (e.getEntityType() == EntityType.DROPPED_ITEM) {
            NBTItem nbti = new NBTItem(((Item) e.getEntity()).getItemStack());

            if (!nbti.hasKey("UUID")) {
                return;
            }

            String uuid = nbti.getString("UUID");

            if (nbti.hasKey("UUID") && ItemKills.itemKills.containsKey(uuid)) {
                MySQLite sql = new MySQLite(dbPath.replace('\\', '/') + "/KillCounter.db");
                ItemKills.itemKills.remove(uuid);
                sql.removeItem(uuid);
            }
        }
    }
}
