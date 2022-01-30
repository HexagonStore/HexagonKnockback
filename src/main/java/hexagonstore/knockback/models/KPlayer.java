package hexagonstore.knockback.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class KPlayer {

    private final String playerName;
    private double kills, deaths;

    private boolean inJumpCooldown, inBowCooldown;
    private double jumpCooldown, bowCooldown;
}
