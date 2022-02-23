/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import de.tr7zw.nbtapi.NBTItem;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class HeldItem {

    public static void loadItemData(PlayerItemHeldEvent e, String dbPath) {
        ItemDB sql = new ItemDB(dbPath.replace('\\', '/') + "/KillCounter.db");

        final ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());
        
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        NBTItem nbti = new NBTItem(item);
        
        if (!nbti.hasKey("UUID")) {
            return;
        }

        String uuid = nbti.getString("UUID");

        sql.loadData(uuid);
    }
}
