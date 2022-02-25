package kills;

import com.happiergore.killcounter.EventListener;
import helper.IOHelper;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class KillControl {

    public static final Map<Player, Map<String, KillControl>> killerHistory = new HashMap<>();

    public int maxKills = EventListener.configYML.getInt("PlayerKills.maxKills");
    public int penalizeTime = EventListener.configYML.getInt("PlayerKills.delayTime");

    public Map<String, KillControl> killHistory = new HashMap<>();

    private final Player killer;
    boolean alreadyPenalized = false;

    private final String victimUUID;
    int totalKills;
    Calendar lastDeath;
    Calendar delayTime;

    //Constructor
    public KillControl(String victimUUID, Player killer) {
        this.victimUUID = victimUUID;
        this.killer = killer;
    }

    public static KillControl getObj(Player killer, String victimUUID) {

        KillControl control;

        if (KillControl.killerHistory.containsKey(killer) && KillControl.killerHistory.get(killer).containsKey(victimUUID)) {
            control = KillControl.killerHistory.get(killer).get(victimUUID);
        } else if (KillControl.killerHistory.containsKey(killer)) {
            control = new KillControl(victimUUID, killer);
            KillControl.killerHistory.get(killer).put(victimUUID, control);
        } else {
            control = new KillControl(victimUUID, killer);
            Map<String, KillControl> killHistory = new HashMap<>();
            killHistory.put(victimUUID, control);
            KillControl.killerHistory.put(killer, killHistory);
        }
        return control;

    }

    public boolean checkPlayerKill(Player killer, Player victim) {
        KillControl killControl = KillControl.getObj(killer, victim.getUniqueId().toString());
        System.out.println(killControl.toString());
        killControl.addKill();
        return !killControl.alreadyPenalized;
    }

    public void addKill() {
        lastDeath = Calendar.getInstance();
        if (++totalKills > maxKills) {
            penalize();
        }
    }

    private void penalize() {

        if (!alreadyPenalized) {
            alreadyPenalized = true;
        } else {
            if (unPenalized()) {
                return;
            }
            int remainingTime = delayTime.get(Calendar.MINUTE) - lastDeath.get(Calendar.MINUTE);
            killer.sendMessage("You have killed this user " + totalKills + " times, you have to wait " + remainingTime + " minutes to count the kill again");
            return;
        }

        delayTime = Calendar.getInstance();
        delayTime.add(Calendar.MINUTE, penalizeTime);

        int remainingTime = delayTime.get(Calendar.MINUTE) - lastDeath.get(Calendar.MINUTE);

        killer.sendMessage("You have killed this user " + totalKills + " times, you have to wait " + remainingTime + " minutes to count the kill again");
    }

    private boolean unPenalized() {
        int remainingTime = delayTime.get(Calendar.MINUTE) - lastDeath.get(Calendar.MINUTE);
        if (remainingTime <= 0) {
            alreadyPenalized = false;
            totalKills = 0;
            killer.sendMessage("You have been unpenalized for killing the same user multiple times. Don't cheat.");
            return true;
        }
        return false;
    }

    //Getter
    public String getVictimUUID() {
        return victimUUID;
    }

    public Player getKiller() {
        return this.killer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KillControl{killHistory=").append(killHistory);
        sb.append(", killer=").append(killer);
        sb.append(", alreadyPenalized=").append(alreadyPenalized);
        sb.append(", victimUUID=").append(victimUUID);
        sb.append(", totalKills=").append(totalKills);
        sb.append(", lastDeath=").append(lastDeath);
        sb.append(", delayTime=").append(delayTime);
        sb.append('}');
        return sb.toString();
    }

}
