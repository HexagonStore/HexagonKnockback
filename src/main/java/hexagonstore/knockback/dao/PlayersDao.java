package hexagonstore.knockback.dao;

import hexagonstore.knockback.database.Database;
import hexagonstore.knockback.database.providers.MySQL;
import hexagonstore.knockback.database.providers.SQLite;
import hexagonstore.knockback.models.KPlayer;
import hexagonstore.knockback.utils.EC_Config;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Getter
public class PlayersDao {

    private HashMap<String, KPlayer> players;
    private Database database;

    public PlayersDao(EC_Config config) {
        players = new HashMap<>();

        String user = config.getString("MySQL.user");
        String host = config.getString("MySQL.host");
        String pass = config.getString("MySQL.pass");
        String db = config.getString("MySQL.database");

        String table = "create table if not exists knockback_users(`playerName` TEXT, `kills` DOUBLE, `deaths` DOUBLE)";

        if (config.getBoolean("MySQL.ativar")) {
            database = new MySQL(table, host, user, pass, db);
        } else database = new SQLite(table);

        database.open();
        load();
    }

    public void close() {
        database.close();
    }

    private void load() {
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM knockback_users")) {
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next())
                        save(new KPlayer(rs.getString("playerName"), rs.getDouble("kills"), rs.getDouble("deaths")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(KPlayer kPlayer) {
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement stm = connection.prepareStatement("INSERT INTO knockback_users(playerName, kills, deaths) VALUES(?,?,?)")) {
                stm.setString(1, kPlayer.getPlayerName());
                stm.setDouble(2, kPlayer.getKills());
                stm.setDouble(3, kPlayer.getDeaths());

                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        players.put(kPlayer.getPlayerName().toLowerCase(), kPlayer);
    }

    public void update(KPlayer kPlayer) {
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement stm = connection.prepareStatement("UPDATE knockback_users SET kills = ?, SET deaths = ? WHERE playerName = ?")) {
                stm.setDouble(1, kPlayer.getKills());
                stm.setDouble(2, kPlayer.getDeaths());
                stm.setString(3, kPlayer.getPlayerName());

                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(KPlayer kPlayer) {
        remove(kPlayer.getPlayerName().toLowerCase());
    }

    public void remove(String playerName) {
        if (contains(playerName.toLowerCase())) {
            try (Connection connection = database.getConnection()) {
                try (PreparedStatement stm = connection.prepareStatement("DELETE FROM knockback_users WHERE playerName = ?")) {
                    stm.setString(1, playerName);
                    stm.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            players.remove(playerName.toLowerCase());
        }
    }

    public boolean contains(String playerName) {
        return players.containsKey(playerName.toLowerCase());
    }
}
