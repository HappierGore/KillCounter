package mysqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kills.UsersKills;

/**
 *
 * @author HappierGore
 */
public class PlayerDB extends MySQLite {

    public PlayerDB(String path) {
        super(path, "UsersKills");
    }

    @Override
    public void loadData(String UUID) {
        if (UsersKills.playerKills.containsKey(UUID)) {
            return;
        }

        String sql = "SELECT * FROM " + this.table + " WHERE uuid = \"" + UUID + "\"";

        System.out.println("Player Data: " + sql);

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UsersKills usersKills = new UsersKills(rs.getString("uuid"));
                usersKills.loadFromDB("zombies", rs.getInt("zombies"));
                usersKills.loadFromDB("totalMobs", rs.getInt("totalMobs"));
                usersKills.loadFromDB("players", rs.getInt("players"));
                UsersKills.playerKills.put(usersKills.getUUID(), usersKills);
            }
            System.out.println("Users Data:\n " + UsersKills.getItemMapStr());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
