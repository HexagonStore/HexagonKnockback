package hexagonstore.knockback.manager;

import hexagonstore.knockback.dao.MapsDao;
import hexagonstore.knockback.dao.PlayersDao;
import hexagonstore.knockback.utils.EC_Config;

public class GameManager {

    private EC_Config config;
    private PlayersDao playersDao;
    private MapsDao mapsDao;

    public GameManager(EC_Config config, PlayersDao playersDao, MapsDao mapsDao) {
        this.config = config;
        this.playersDao = playersDao;
        this.mapsDao = mapsDao;
    }
}
