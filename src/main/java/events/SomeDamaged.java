/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import de.tr7zw.nbtapi.NBTItem;
import kills.ItemKills;
import mysqlite.ItemDB;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author HappierGore
 */
public final class SomeDamaged {

    public static void untrackItem(EntityDamageEvent e, String dbPath) {
        if (e.getEntityType() == EntityType.DROPPED_ITEM) {
            NBTItem nbti = new NBTItem(((Item) e.getEntity()).getItemStack());

            if (!nbti.hasKey("UUID")) {
                return;
            }

            String uuid = nbti.getString("UUID");

            if (ItemKills.itemKills.containsKey(uuid)) {
                new ItemDB().remove(uuid);
                ItemKills.itemKills.remove(uuid);
            }
        }
    }
}
