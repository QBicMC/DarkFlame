package github.qbic.darkflame.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientHooks.class)
@OnlyIn(Dist.CLIENT)
public class FogRendererMixin {
    @Inject(method = "onFogRender", at = @At("RETURN"), cancellable = true)
    private static void darkFlame$onFogRender(
            FogRenderer.FogMode mode,
            FogType type,
            Camera camera,
            float partialTick,
            float renderDistance,
            FogParameters fogParameters,
            CallbackInfoReturnable<FogParameters> cir
    ) {
        FogParameters original = cir.getReturnValue();
        FogParameters modified = original;

        if (fogParameters.start() > 5 && fogParameters.end() > 50) {
            modified = new FogParameters(
                    original.start() * 0.2f,
                    original.end() * 0.8f,
                    original.shape(),
                    original.red(),
                    original.green(),
                    original.blue(),
                    original.alpha()
            );
        }

        cir.setReturnValue(modified);
    }
}
