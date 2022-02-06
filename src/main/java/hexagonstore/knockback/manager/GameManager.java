package hexagonstore.knockback.manager;

import hexagonstore.knockback.KnockbackPlugin;
import hexagonstore.knockback.dao.MapsDao;
import hexagonstore.knockback.dao.PlayersDao;
import hexagonstore.knockback.models.KMap;
import hexagonstore.knockback.utils.EC_Config;
import hexagonstore.knockback.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class GameManager {

    private EC_Config config;
    private PlayersDao playersDao;
    private MapsDao mapsDao;

    private KMap currentMap;
    private double changeMapDelay;

    private BukkitTask changeMapTask;

    public GameManager(EC_Config config, PlayersDao playersDao, MapsDao mapsDao) {
        this.config = config;
        this.playersDao = playersDao;
        this.mapsDao = mapsDao;

        this.changeMapDelay = config.getDouble("Cooldowns.change_map");
        ArrayList<KMap> kMaps = new ArrayList<>(mapsDao.getMaps().values());
        Random r = new Random();
        this.currentMap = kMaps.get(r.nextInt(kMaps.size()));
        if(this.currentMap == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[HexagonKnockback] Plugin desabilitado devido a não ter mapas suficientes.");
            Bukkit.getPluginManager().disablePlugin(KnockbackPlugin.getPlugin());
        }

        startTaskChangeMap();

    }

    public void giveItems(Player player) {
        ItemStack stickItem = new ItemBuilder(Material.valueOf(config.getString("Items.Stick.material")), config.getShort("Items.Stick.data"))
                .setName(config.getString("Items.Stick.name").replace("&", "§"))
                .setLore(config.getStringList("Items.Stick.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .glow(config.getBoolean("Items.Stick.glow"))
                .addUnsafeEnchantment(Enchantment.KNOCKBACK, config.getInt("Items.Stick.knockback-level"))
                .toItemStack();

        ItemStack blocksItem = new ItemBuilder(Material.valueOf(config.getString("Items.Blocos.material")), config.getShort("Items.Blocos.data"))
                .setName(config.getString("Items.Blocos.name").replace("&", "§"))
                .setLore(config.getStringList("Items.Blocos.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .setAmount(config.getInt("Items.Blocos.amount"))
                .glow(config.getBoolean("Items.Blocos.glow"))
                .toItemStack();

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        for (String s : config.getStringList("Items.Arco.enchants")) {
            String[] split = s.split(";");
            enchants.put(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
        }

        ItemStack bowItem = new ItemBuilder(Material.valueOf(config.getString("Items.Arco.material")), config.getShort("Items.Arco.data"))
                .setName(config.getString("Items.Arco.name").replace("&", "§"))
                .setLore(config.getStringList("Items.Arco.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .glow(config.getBoolean("Items.Arco.glow"))
                .addEnchantments(enchants)
                .toItemStack();

        ItemStack superJumpItem = new ItemBuilder(Material.valueOf(config.getString("Items.SuperJump.material")), config.getShort("Items.SuperJump.data"))
                .setName(config.getString("Items.SuperJump.name").replace("&", "§"))
                .setLore(config.getStringList("Items.SuperJump.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .glow(config.getBoolean("Items.SuperJump.glow"))
                .toItemStack();

        ItemStack enderPearlItem = new ItemBuilder(Material.ENDER_PEARL)
                .setName(config.getString("Items.EnderPearl.name").replace("&", "§"))
                .setLore(config.getStringList("Items.EnderPearl.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .glow(config.getBoolean("Items.EnderPearl.glow"))
                .toItemStack();

        ItemStack speedItem = new ItemBuilder(Material.valueOf(config.getString("Items.Speed.material")), config.getShort("Items.Speed.data"))
                .setName(config.getString("Items.Speed.name").replace("&", "§"))
                .setLore(config.getStringList("Items.Speed.lore").stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()))
                .glow(config.getBoolean("Items.Speed.glow"))
                .toItemStack();

        player.getInventory().setItem(config.getInt("Items.Stick.slot"), stickItem);
        player.getInventory().setItem(config.getInt("Items.Blocos.slot"), blok
        );
        player.getInventory().setItem(config.getInt("Items.Arco.slot"), bowItem);
        player.getInventory().setItem(config.getInt("Items.SuperJump.slot"), superJumpItem);
        player.getInventory().setItem(config.getInt("Items.EnderPearl.slot"), enderPearlItem);
        player.getInventory().setItem(config.getInt("Items.Speed.slot"), speedItem);
    }

    private void startTaskChangeMap() {
        changeMapTask = new BukkitRunnable() {
            Random r = new Random();
            ArrayList<KMap> kMaps = new ArrayList<>(mapsDao.getMaps().values());
            public void run() {
                if(changeMapDelay <= 0) {
                    currentMap = kMaps.get(r.nextInt(kMaps.size()));
                    if(currentMap == null) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[HexagonKnockback] Plugin desabilitado devido a não ter mapas suficientes.");
                        Bukkit.getPluginManager().disablePlugin(KnockbackPlugin.getPlugin());
                    }
                    changeMapDelay = config.getDouble("Cooldowns.change_map");
                }
                changeMapDelay--;
            }
        }.runTaskTimer(KnockbackPlugin.getPlugin(), 0L, 20L);
    }
}
