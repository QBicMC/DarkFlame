package github.qbic.darkflame.client.model.animations;

import github.qbic.darkflame.entity.ObserverEntity;
import github.qbic.darkflame.entity.SmileyEntity;
import net.minecraft.world.entity.Entity;

public class AnimationConditions {
    public static boolean smileyAttack(Entity entity) {
        if (entity == null) return false;
        return (entity instanceof SmileyEntity datEntI ? datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) > 60;
    }

    public static boolean smileyReach(Entity entity) {
        if (entity == null) return false;
        return (entity instanceof SmileyEntity datEntI ? datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) > 0 && (entity instanceof SmileyEntity datEntI ? datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) < 60;
    }

    public static boolean disappear(Entity entity) {
        return switch (entity) {
            case ObserverEntity datEnt -> datEnt.getEntityData().get(ObserverEntity.despawning);
            case null, default -> false;
        };
    }
}
