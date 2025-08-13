package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, DarkFlame.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> HAGGSTROM_FLATLINE = register("haggstrom_flatline");
    public static final DeferredHolder<SoundEvent, SoundEvent> BELLS = register("bells");
    public static final DeferredHolder<SoundEvent, SoundEvent> KNOCKING = register("knocking");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_BOX = register("music_box");

    public static final DeferredHolder<SoundEvent, SoundEvent> ATTACK = register("attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> OBSERVER_SPAWN = register("observer_spawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHER_SPAWN = register("watcher_spawn");

    public static final DeferredHolder<SoundEvent, SoundEvent> OBSERVER_AMBIENT = register("observer_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHER_AMBIENT = register("watcher_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SMILEY_AMBIENT = register("smiley_ambient");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, name);
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
