package mysqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;

/**
 * @author HappierGore
 */
public abstract class MySQLite {

    private final String path;
    public final String table;

    public MySQLite(String path, String table) {
        this.path = "jdbc:sqlite:" + path;
        this.table = table;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void updateTotalMobs(String UUID, int totalMobs) {

        String sql = "INSERT INTO " + table + "(uuid, totalMobs) VALUES(?, ?) ON CONFLICT(uuid) DO UPDATE SET totalMobs=excluded.totalMobs";
        //INSERT INTO UsersKills(uuid, totalMobs) VALUES("8d43c05c-2a42-38d4-ae7e-4a83036327ed",100) ON CONFLICT(uuid) DO UPDATE SET totalMobs=excluded.totalMobs

        try (Connection conn = connect();
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

    public void updateZombies(String UUID, int totalZombies) {
        String sql = "INSERT INTO " + table + "(uuid, zombies) VALUES(?, ?) ON CONFLICT(uuid) DO UPDATE SET zombies=excluded.zombies";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, UUID);
            pstmt.setInt(2, totalZombies);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePlayers(String UUID, int totalPlayers) {
        String sql = "INSERT INTO " + table + "(uuid, players) VALUES(?, ?) ON CONFLICT(uuid) DO UPDATE SET players=excluded.players";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, UUID);
            pstmt.setInt(2, totalPlayers);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(String UUID) {

        String sql = "DELETE FROM " + table + " WHERE uuid = \"" + UUID + "\"";

        System.out.println(sql);

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract void loadData(String UUID);
}
