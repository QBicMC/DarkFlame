package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, DarkFlame.MOD_ID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_DOT = REGISTRY.register("black_dot", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SUMMONING_ENERGY = REGISTRY.register("summoning_energy", () -> new SimpleParticleType(true));
}