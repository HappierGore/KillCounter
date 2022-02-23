package placeholders;

import de.tr7zw.nbtapi.NBTItem;
import kills.ItemKills;
import kills.UsersKills;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class PlaceHolders extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "KillCounter";
    }

    @Override
    public String getAuthor() {
        return "HappierGore";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        //System.out.println("Params from Placeholders: " + params);
        if (player == null) {
            return "";
        }

        String pUUID = player.getUniqueId().toString();

        if (params.equals("players")) {
            return Integer.toString(UsersKills.playerKills.get(pUUID).getPlayers());
        }
        if (params.equals("totalMobs")) {
            return Integer.toString(UsersKills.playerKills.get(pUUID).getTotalMobs());
        }
        if (params.equals("zombies")) {
            return Integer.toString(UsersKills.playerKills.get(pUUID).getZombies());
        }
        if (params.contains("weapon")) {
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                return "Invalid item";
            }

            NBTItem nbti = new NBTItem(item);
            String uuid = nbti.getString("UUID");

            System.out.println("UUID DE ARMA DEL ASESINO " + uuid);

            System.out.println("Data: " + ItemKills.getItemMapStr());

            if (params.endsWith("players")) {
                return Integer.toString(ItemKills.itemKills.get(uuid).getPlayers());
            }
            if (params.endsWith("totalMobs")) {
                return Integer.toString(ItemKills.itemKills.get(uuid).getTotalMobs());
            }
            if (params.endsWith("zombies")) {
                return Integer.toString(ItemKills.itemKills.get(uuid).getZombies());
            }
            return "Invalid counter type.";
        }
        return null;
    }

}
