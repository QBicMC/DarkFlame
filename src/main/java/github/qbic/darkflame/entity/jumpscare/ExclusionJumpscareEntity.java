package github.qbic.darkflame.entity.jumpscare;

import github.qbic.darkflame.entity.HorrorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ExclusionJumpscareEntity extends JumpscareEntity {

    public ExclusionJumpscareEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    String jumpscareSound() {
        return "exclusion_hack";
    }

    @Override
    public ResourceLocation texture() {
        return ResourceLocation.parse("dark_flame:textures/entities/exclusion.png");
    }
}
