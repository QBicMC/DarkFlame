package github.qbic.darkflame.mixin.client;

import github.qbic.darkflame.client.util.ClientUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.ClientLevelData.class)
public class ClientTimeMixin {
    @Inject(method = "getDayTime", at = @At("RETURN"), cancellable = true)
    private static void darkFlame$getDayTime(CallbackInfoReturnable<Long> cir) {
        Long time = ClientUtil.getModifiedDayTime();
        if (time != null) cir.setReturnValue(time);
    }
}
