package comandos;

import de.tr7zw.nbtapi.NBTItem;
import helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class SeeItemInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println(TextUtils.parseColor("&cEste comando sólo puede ser ejecutado por jugadores"));
            return false;
        }

        Player player = (Player) sender;

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("Necesitas tener un item en tu mano para ver la información");
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        NBTItem nbti = new NBTItem(item);
        String uuid = nbti.getString("UUID");

        player.sendMessage("UUID: " + uuid);

        return false;
    }

}
