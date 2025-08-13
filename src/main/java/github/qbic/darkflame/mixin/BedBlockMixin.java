package github.qbic.darkflame.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin {

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void darkFlame$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide) {
            if (state.getValue(BedBlock.PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(BedBlock.FACING));
                state = level.getBlockState(pos);
                if (!(state.getBlock() instanceof BedBlock)) {
                    cir.setReturnValue(InteractionResult.CONSUME);
                    return;
                }
            }

            if (BedBlock.canSetSpawn(level)) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.setRespawnPosition(level.dimension(), pos, player.getYRot(), false, true);
                player.displayClientMessage(Component.translatable("dark_flame.bed.stopSleep"), true);
            }
        }

        cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
    }
}
