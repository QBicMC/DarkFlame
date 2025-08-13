package github.qbic.darkflame.mixin.client;

import github.qbic.darkflame.networking.C2S.ChangePerspectivePayload;
import net.minecraft.client.CameraType;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin {

    @Inject(method = "setCameraType", at = @At("HEAD"))
    private static void darkFlame$setCameraType(CameraType pointOfView, CallbackInfo ci) {
        switch (pointOfView) {
            case FIRST_PERSON -> ChangePerspectivePayload.send(1);
            case THIRD_PERSON_FRONT -> ChangePerspectivePayload.send(2);
            case THIRD_PERSON_BACK -> ChangePerspectivePayload.send(3);
        }
    }
}
