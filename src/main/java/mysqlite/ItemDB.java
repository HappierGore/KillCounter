package mysqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kills.ItemKills;

/**
 *
 * @author HappierGore
 */
public class ItemDB extends MySQLite {

    public ItemDB(String path) {
        super(path, "Itemskills");
    }

    @Override
    public void loadData(String UUID) {
        if (ItemKills.itemKills.containsKey(UUID)) {
            return;
        }

        String sql = "SELECT * FROM " + this.table + " WHERE uuid = \"" + UUID + "\"";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ItemKills itemKills = new ItemKills(rs.getString("uuid"));
                itemKills.loadFromDB("zombies", rs.getInt("zombies"));
                itemKills.loadFromDB("totalMobs", rs.getInt("totalMobs"));
                itemKills.loadFromDB("players", rs.getInt("players"));
                ItemKills.itemKills.put(itemKills.getUUID(), itemKills);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
