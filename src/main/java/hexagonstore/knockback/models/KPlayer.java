package hexagonstore.knockback.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class KPlayer {

    private final String playerName;
    private double kills, deaths;

    private boolean inJumpCooldown, inBowCooldown, inSpeedCooldown;
    private double jumpCooldown, bowCooldown, speedCooldown;

    private int stickSlot, blocksSlot, bowSlot, superJumpSlot, speedSlot, enderPearlSlot;

    public KPlayer(String playerName, double kills, double deaths) {
        this.playerName = playerName;
        this.kills = kills;
        this.deaths = deaths;
    }
}
