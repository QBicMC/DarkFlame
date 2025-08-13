package github.qbic.darkflame.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class HerobrineEntity extends HorrorEntity {
    public HerobrineEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }
}
