package github.qbic.darkflame.mixin;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientHooks.class)
public class FogRendererMixin {
    @Inject(method = "onFogRender", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setupFog(
            FogRenderer.FogMode mode,
            FogType type,
            Camera camera,
            float partialTick,
            float renderDistance,
            FogParameters fogParameters,
            CallbackInfoReturnable<FogParameters> cir,
            FluidState state,
            ViewportEvent.RenderFog event
    ) {
        FogParameters modifiedFogParameters = fogParameters;
        float start = fogParameters.start();
        float end = fogParameters.end();

        if (fogParameters.start() > 20 && fogParameters.end() > 50) {
            modifiedFogParameters = new FogParameters(20, 50, fogParameters.color(), fogParameters.fogShape(), fogParameters.fogDensity());
        }

        cir.setReturnValue(modifiedFogParameters);
    }
}
