/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import de.tr7zw.nbtapi.NBTItem;
import java.util.UUID;
import kills.ItemKills;
import kills.UsersKills;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class OnSomeDeath{
    
    public static void registerKill(EntityDeathEvent e, String path) {

        if (!(e.getEntity().getKiller() instanceof Player)) {
            return;
        }

        //Register player kill
        final Player player = e.getEntity().getKiller();

        UsersKills pKills = UsersKills.getObj(player.getUniqueId().toString());

        pKills.add(e.getEntityType());
        pKills.updateDB(path);
        
        
        player.sendMessage("Player Stats:");
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Mobs totales asesinados: %killCounter_totalMobs%"));
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Zombies totales asesinados: %killCounter_zombies%"));
//
        //Register item kill
        final ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) {
            return;
        }

        NBTItem nbti = new NBTItem(item);

        if (!nbti.hasKey("UUID")) {
            nbti.setString("UUID", UUID.randomUUID().toString());
            player.getInventory().removeItem(item);
            player.getInventory().addItem(nbti.getItem());
        }

        String uuid = nbti.getString("UUID");
        
        ItemKills objKills = ItemKills.getObj(uuid);

        objKills.add(e.getEntityType());
        objKills.updateDB(path);

        player.sendMessage("Weapon Stats:");
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Mobs totales asesinados: %killCounter_weapon_totalMobs%"));
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Zombies totales asesinados: %killCounter_weapon_zombies%"));

    }
}
