package hexagonstore.knockback.events;

import hexagonstore.knockback.dao.PlayersDao;
import hexagonstore.knockback.manager.GameManager;
import hexagonstore.knockback.models.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEvent implements Listener {

    private PlayersDao playersDao;
    private GameManager gameManager;

    public JoinEvent(PlayersDao playersDao, GameManager gameManager, JavaPlugin main) {
        this.playersDao = playersDao;
        this.gameManager = gameManager;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void evento(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!playersDao.contains(player.getName())) playersDao.save(new KPlayer(player.getName(), 0, 0));

        player.getInventory().clear();
        gameManager.giveItems(player);
    }
}
