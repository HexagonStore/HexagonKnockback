package hexagonstore.knockback.models;

import hexagonstore.knockback.KnockbackPlugin;
import hexagonstore.knockback.utils.LocationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor
public class KMap {

    private final String id;
    private String displayName, worldName;
    private Location spawnLoc;

    public void delete(){
        File data = new File(KnockbackPlugin.getPlugin().getDataFolder(), "/mapas/" + id);
        data.delete();
    }

    public void save() {

        File data = new File(KnockbackPlugin.getPlugin().getDataFolder(), "/mapas/" + id);
        YamlConfiguration config = new YamlConfiguration();

        config.set("displayName", displayName);
        config.set("world", worldName);
        config.set("spawnLoc", LocationSerializer.getSerializedLocation(spawnLoc));

        try {
            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        File data = new File(KnockbackPlugin.getPlugin().getDataFolder(), "/mapas/" + id);
        reload(data);
    }

    public void reload(File data){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(data);

        this.displayName = config.getString("displayName");
        this.worldName = config.getString("world");
        this.spawnLoc = LocationSerializer.getDeserializedLocation(config.getString("spawnLoc"));
    }
}
