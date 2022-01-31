package hexagonstore.knockback.dao;

import hexagonstore.knockback.KnockbackPlugin;
import hexagonstore.knockback.models.KMap;

import java.io.File;
import java.util.HashMap;

public class MapsDao {

    private HashMap<String, KMap> maps;

    public MapsDao() {
        maps = new HashMap<>();

        load();
    }

    private void load() {
        File pasta = new File(KnockbackPlugin.getPlugin().getDataFolder(), "/mapas/");

        pasta.mkdirs();
        for (File data : pasta.listFiles()) {
            KMap kMap = new KMap(data.getName().replace(".yml", ""));

            kMap.reload(data);
            maps.put(kMap.getId().toLowerCase(), kMap);
        }
    }

    public void add(KMap kMap) {
        maps.put(kMap.getId().toLowerCase(), kMap);
    }

    public void remove(KMap kMap) {
        remove(kMap.getId());
    }

    public void remove(String mapID) {
        maps.remove(mapID.toLowerCase());
    }

    public boolean contains(String mapID) {
        return maps.containsKey(mapID.toLowerCase());
    }
}
