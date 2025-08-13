package github.qbic.darkflame.events;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.SmileyEntity;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpawnSmileyBehindEvent extends ModEvent {

    @Override
    public EventType getType() {
        return EventType.MAJOR;
    }

    @Override
    public void execute() {
        Player target = Brain.getTarget();
        Level level = target.level();
        if (!Util.isVanillaDimension(level)) return;

        Vec3 backDir = target.getLookAngle().scale(-1).normalize(); // direction behind the player
        Vec3 behindPos = target.position().add(backDir.scale(2)); // 2 blocks behind player

        BlockPos spawnPos = BlockPos.containing(behindPos.x, behindPos.y, behindPos.z);
        Util.spawnEntityAt(spawnPos, level, ModEntities.SMILEY.get());
//      SmileyEntity entity = new SmileyEntity(ModEntities.SMILEY.get(), level);
//      entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
//      level.addFreshEntity(entity);
    }
}
