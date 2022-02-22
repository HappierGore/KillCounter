package mysqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kills.ItemKills;
import kills.UsersKills;

/**
 * @author HappierGore
 */
public class MySQLite {

    String path;

    public MySQLite(String path) {
        this.path = "jdbc:sqlite:" + path;
    }

    /**
     * Connect to a sample database
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Update data of a warehouse specified by the id
     *
     * @param UUID      UUID
     * @param totalMobs Mob totales
     * @param isItem    It's an item?
     */
    public void updateTotalMobs(String UUID, int totalMobs, boolean isItem) {

        String table = isItem ? "ItemsKills" : "UsersKills";

        String sql = "INSERT OR REPLACE INTO " + table + "(uuid,totalMobs) VALUES(?,?)";
        //INSERT OR REPLACE INTO UsersKills(uuid,totalMobs) VALUES("TE",5)
        System.out.println(sql);

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, UUID);
            pstmt.setInt(2, totalMobs);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getIemData(String UUID) {
        if (ItemKills.itemKills.containsKey(UUID)) {
            return;
        }

        String table = "ItemsKills";

        String sql = "SELECT * FROM " + table + " WHERE uuid = \"" + UUID + "\"";
        //INSERT OR REPLACE INTO UsersKills(uuid,totalMobs) VALUES("TE",5)
        System.out.println(sql);

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

    public void removeItem(String UUID) {

        String table = "ItemsKills";

        String sql = "DELETE FROM " + table + " WHERE uuid = \"" + UUID + "\"";

        System.out.println(sql);

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
