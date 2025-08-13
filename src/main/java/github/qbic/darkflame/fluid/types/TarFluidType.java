package github.qbic.darkflame.fluid.types;

import com.mojang.blaze3d.shaders.FogShape;
import github.qbic.darkflame.init.ModFluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector4f;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class TarFluidType extends FluidType {
    public TarFluidType() {
        super(FluidType.Properties.create().fallDistanceModifier(0.5f).canExtinguish(true).supportsBoating(true).canHydrate(true).motionScale(0.0095d).density(2000).viscosity(10000).temperature(400).canConvertToSource(true)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    @SubscribeEvent
    public static void registerFluidTypeExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = ResourceLocation.parse("dark_flame:block/tar");
            private static final ResourceLocation FLOWING_TEXTURE = ResourceLocation.parse("dark_flame:block/tar");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                return new Vector4f(0f, 0f, 0f, fluidFogColor.w);
            }

            @Override
            public FogParameters modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, FogParameters fogParameters) {
                float nearDistance = fogParameters.start();
                float farDistance = fogParameters.end();
                Entity entity = camera.getEntity();
                Level world = entity.level();
                return new FogParameters(-5f, 5f, FogShape.SPHERE, fogParameters.red(), fogParameters.green(), fogParameters.blue(), fogParameters.alpha());
            }
        }, ModFluidTypes.TAR_TYPE.get());
    }
}