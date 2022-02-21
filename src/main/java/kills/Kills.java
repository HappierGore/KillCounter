/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package kills;

import java.util.Map;
import org.bukkit.entity.EntityType;

/**
 *
 * @author HappierGore
 */
public interface Kills {

    public int get(String request);

    public void add(EntityType entityType);

    public void updateDB(String dbPath);
}
