package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.init.ModSounds;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class FootStepsEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        ServerPlayer player = (ServerPlayer) target();
        ServerLevel level = (ServerLevel) player.level();
        Vec3 reversedLook = player.getLookAngle().reverse().scale(2);
        BlockPos pos = new BlockPos((int) (player.getOnPos().getX() + reversedLook.x),
                (int) (player.getOnPos().getY() + reversedLook.y),
                (int) (player.getOnPos().getZ() + reversedLook.z));

        int count = RANDOM.nextInt(3, 5);
        for (int i = 0; i < count; i++) {
            Util.schedule(() -> level.playSound(null, pos, SoundEvents.GRASS_STEP, SoundSource.HOSTILE), i * 7, "play footsteps");
        }
    }

    @Override
    public String name() {
        return "footsteps";
    }
}
