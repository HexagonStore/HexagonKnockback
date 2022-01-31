package hexagonstore.knockback.events;

import hexagonstore.knockback.dao.PlayersDao;
import hexagonstore.knockback.models.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEvent implements Listener {

    private PlayersDao playersDao;

    public JoinEvent(PlayersDao playersDao, JavaPlugin main) {
        this.playersDao = playersDao;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void evento(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!playersDao.contains(player.getName())) playersDao.save(new KPlayer(player.getName(), 0, 0));

        player.getInventory().clear();
        //Dar items
    }
}
