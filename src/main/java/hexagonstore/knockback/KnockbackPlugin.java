package hexagonstore.knockback;

import hexagonstore.knockback.dao.PlayersDao;
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
    private EC_Config cfg;

    @Override
    public void onEnable() {
        plugin = this;

        cfg = new EC_Config(this, "", "config.yml", false);
        playersDao = new PlayersDao(cfg);
    }

    @Override
    public void onDisable() {
        playersDao.close();
    }
}
