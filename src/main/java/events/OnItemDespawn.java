/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import de.tr7zw.nbtapi.NBTItem;
import kills.ItemKills;
import mysqlite.ItemDB;
import org.bukkit.event.entity.ItemDespawnEvent;

/**
 *
 * @author HappierGore
 */
public class OnItemDespawn {

    public static void untrackItem(ItemDespawnEvent e, String dbPath) {

        NBTItem nbti = new NBTItem(e.getEntity().getItemStack());

        if (!nbti.hasKey("UUID")) {
            return;
        }

        String uuid = nbti.getString("UUID");

        if (ItemKills.itemKills.containsKey(uuid)) {
            ItemKills.itemKills.remove(uuid);
            new ItemDB().remove(uuid);
        }
    }

}
