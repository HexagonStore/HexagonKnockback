package hexagonstore.knockback;

import hexagonstore.knockback.dao.MapsDao;
import hexagonstore.knockback.dao.PlayersDao;
import hexagonstore.knockback.events.JoinEvent;
import hexagonstore.knockback.manager.GameManager;
import hexagonstore.knockback.utils.EC_Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class KnockbackPlugin extends JavaPlugin {

    private static KnockbackPlugin plugin;

    public static KnockbackPlugin getPlugin() {
        return plugin;
    }

    private PlayersDao playersDao;
    private MapsDao mapsDao;

    private GameManager gameManager;
    private EC_Config cfg;

    @Override
    public void onEnable() {
        plugin = this;

        cfg = new EC_Config(this, "", "config.yml", false);
        playersDao = new PlayersDao(cfg);

        mapsDao = new MapsDao();
        gameManager = new GameManager(cfg, playersDao, mapsDao);

        new JoinEvent(playersDao, this);
    }

    @Override
    public void onDisable() {
        playersDao.close();
    }
}
