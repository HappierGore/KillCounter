package comandos;

import de.tr7zw.nbtapi.NBTItem;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public class NewItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("Ejecutando comando: " + command.getName());
        if (command.getName().equals("newitem")) {
            Player player = (Player) sender;

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return false;
            }

            ItemStack item = player.getInventory().getItemInMainHand();

            String addUUID = UUID.randomUUID().toString();

            ItemMeta meta = item.getItemMeta();

            System.out.println("Lore a crear: " + addUUID);
            System.out.println(item.getItemMeta());
            
            NBTItem nbti = new NBTItem(item);
            if(!nbti.hasKey("UUID")){
                nbti.setString("UUID", addUUID);
                player.getInventory().removeItem(item);
                player.getInventory().addItem(nbti.getItem());
            }
            
            
            
            System.out.println(nbti);
            
            //LORES
//            if (item.hasItemMeta()) {
//                if (meta.hasLore()) {
//                    meta.getLore().add(addUUID);
//                } else {
//                    meta.setLore(Arrays.asList("UUID: " + addUUID));
//                }
//            } else {
//                meta.setDisplayName("Espada con tracking");
//                meta.setLore(Arrays.asList("UUID: " + addUUID));
//                item.setItemMeta(meta);
//            }

            player.sendMessage("El objeto de tu mano ha sido bendecido con un Tracking, UUID: " + addUUID);

        }

        return false;
    }
}
