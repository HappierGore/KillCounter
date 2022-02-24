package events;

import kills.UsersKills;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author HappierGore
 */
public final class OnSomeDeath {

    public static void registerKill(EntityDeathEvent e, String path) {

        if (!(e.getEntity().getKiller() instanceof Player)) {
            return;
        }

        //Register player kill
        final Player player = e.getEntity().getKiller();

        UsersKills pKills = UsersKills.getObj(player.getUniqueId().toString());

        pKills.add(e);
        pKills.updateDB();
//
//        player.sendMessage("Player Stats:");
//        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Mobs totales asesinados: %killCounter_totalMobs%"));
//        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Zombies totales asesinados: %killCounter_zombies%"));
//
        //Register item kill
//        final ItemStack item = player.getInventory().getItemInMainHand();
//
//        if (item.getType() == Material.AIR) {
//            return;
//        }
//
//        NBTItem nbti = new NBTItem(item);
//
//        if (!nbti.hasKey("UUID")) {
//            nbti.setString("UUID", UUID.randomUUID().toString());
//            player.getInventory().removeItem(item);
//            player.getInventory().addItem(nbti.getItem());
//        }
//
//        String uuid = nbti.getString("UUID");
//
//        ItemKills objKills = ItemKills.getObj(uuid);
//
//        objKills.add(e);
//        objKills.updateDB(path);

//        player.sendMessage("Weapon Stats:");
//        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Mobs totales asesinados: %killCounter_weapon_totalMobs%"));
//        player.sendMessage(PlaceholderAPI.setPlaceholders(player, "Zombies totales asesinados: %killCounter_weapon_zombies%"));
    }
}
