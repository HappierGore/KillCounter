package events;

import kills.UsersKills;
import mysqlite.PlayerDB;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author HappierGore
 */
public class PlayerJoin {
    
    public static void loadPlayerData(PlayerJoinEvent e, String dbPath) {
        
        String uuid = e.getPlayer().getUniqueId().toString();
        
        if (UsersKills.playerKills.containsKey(uuid)) {
            return;
        }
        
        new PlayerDB().loadData(uuid);
    }
    
}
