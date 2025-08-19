package github.qbic.darkflame.init;

import github.qbic.darkflame.client.particle.BlackDotParticle;
import github.qbic.darkflame.client.particle.SummoningEnergyParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.BLACK_DOT.get(), BlackDotParticle::provider);
        event.registerSpriteSet(ModParticleTypes.SUMMONING_ENERGY.get(), SummoningEnergyParticle::provider);
    }
}