package events;

import kills.UsersKills;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author HappierGore
 */
public class PlayerLeave {

    public static void removePlayerFromMemory(PlayerQuitEvent e) {

        String uuid = e.getPlayer().getUniqueId().toString();

        if (!UsersKills.playerKills.containsKey(uuid)) {
            return;
        }

        UsersKills.playerKills.remove(uuid);

        System.out.println(UsersKills.getItemMapStr());
    }
}
