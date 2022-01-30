package hexagonstore.knockback.dao;

import hexagonstore.knockback.database.Database;
import hexagonstore.knockback.database.providers.MySQL;
import hexagonstore.knockback.database.providers.SQLite;
import hexagonstore.knockback.models.KPlayer;
import hexagonstore.knockback.utils.EC_Config;
import lombok.Getter;

import java.util.HashMap;

//COLOCAR DATABASE NOS SAVE E REMOVE;
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

        if(config.getBoolean("MySQL.ativar")) {
            database = new MySQL(table, host, user, pass, db);
        }else database = new SQLite(table);

        database.open();
    }

    public void save() {
        database.close();
    }

    public void add(KPlayer kPlayer) {
        players.put(kPlayer.getPlayerName().toLowerCase(), kPlayer);
    }

    public void remove(KPlayer kPlayer) {
        remove(kPlayer.getPlayerName().toLowerCase());
    }

    public void remove(String playerName) {
        players.remove(playerName.toLowerCase());
    }

    public boolean contains(String playerName) {
        return players.containsKey(playerName.toLowerCase());
    }

    public boolean contains(KPlayer kPlayer) {
        return contains(kPlayer.getPlayerName().toLowerCase());
    }
}
