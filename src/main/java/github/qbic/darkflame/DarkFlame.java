package github.qbic.darkflame;

import com.mojang.serialization.MapCodec;
import github.qbic.darkflame.dimension.Dims;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.init.*;
import github.qbic.darkflame.networking.ModVariables;
import github.qbic.darkflame.networking.S2C.VolumePayload;
import github.qbic.darkflame.util.TestCommand;
import github.qbic.darkflame.util.compat.CompatUtils;
import github.qbic.darkflame.util.voicechat.filters.BlankEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(DarkFlame.MOD_ID)
public class DarkFlame {
    public static final Logger LOGGER = LogManager.getLogger(DarkFlame.class);
    public static final String MOD_ID = "dark_flame";
    public static final boolean DEBUG = true;

    public DarkFlame(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::registerNetworking);

        ModBlocks.REGISTRY.register(modEventBus);
        ModItems.REGISTRY.register(modEventBus);
        ModEntities.REGISTRY.register(modEventBus);
        ModTabs.REGISTRY.register(modEventBus);
        ModVariables.ATTACHMENT_TYPES.register(modEventBus);
        ModSounds.REGISTRY.register(modEventBus);
        ModFluids.REGISTRY.register(modEventBus);
        ModFluidTypes.REGISTRY.register(modEventBus);
        ModParticleTypes.REGISTRY.register(modEventBus);
        ModMenus.REGISTRY.register(modEventBus);

        registerTests();
        CompatUtils.registerCompatEvents();

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static void registerDimGenerator(RegisterEvent event, String name, MapCodec<? extends ChunkGenerator> codec) {
        event.register(
                Registries.CHUNK_GENERATOR,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                () -> codec
        );
    }

    private void registerTests() {
        TestCommand.addTest("vc", () -> Brain.VOICE_CHAT.playRandomClipAt((ServerLevel) Brain.getTarget().level(), Brain.getTarget().getOnPos(), Brain.getTarget().getUUID(), new BlankEffect()), TestCommand.Side.SERVER);
        TestCommand.addTest("deafen", () -> VolumePayload.send(Brain.getTarget(), 0), TestCommand.Side.SERVER);
        TestCommand.addTest("smly_behind", ModEvents.SPAWN_SMILEY_BEHIND_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("writing", ModEvents.WRITING_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("shader", () -> ModEvents.APPLY_SHADER_EVENT.execute(MOD_ID + ":pixelate"), TestCommand.Side.SERVER);
        TestCommand.addTest("labyrinth", Dims::teleportTargetToLabyrinth, TestCommand.Side.SERVER);
        TestCommand.addTest("start", Brain::begin, TestCommand.Side.SERVER);
        TestCommand.addTest("no_escape", () -> Brain.setNoEscapeFor(100), TestCommand.Side.SERVER);
        TestCommand.addTest("window_change", ModEvents.CHANGE_WINDOW_EVENT::execute, TestCommand.Side.CLIENT);
        TestCommand.addTest("singularity", ModEvents.SPAWN_SINGULARITY_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("event_sequence", Brain::triggerEventSequence, TestCommand.Side.SERVER);
        TestCommand.addTest("the_hallways", Dims::teleportTargetToTheHallways, TestCommand.Side.SERVER);
        TestCommand.addTest("megalophobia", Dims::teleportTargetToMEGALOPHOBIA, TestCommand.Side.SERVER);
        TestCommand.addTest("fake_villager", Brain::spawnFakeVillager, TestCommand.Side.SERVER);
        TestCommand.addTest("countdown_night", ModEvents.COUNT_DOWN_TO_NIGHT_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("name_sign", ModEvents.SIGN_WITH_NAME_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("block_door", ModEvents.BLOCK_DOOR_EVENT::execute, TestCommand.Side.SERVER);
        TestCommand.addTest("sign", ModEvents.SIGN_EVENT::execute, TestCommand.Side.SERVER);
    }

    private static boolean networkingRegistered = false;
    private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

    private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
    }

    public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
        if (networkingRegistered)
            throw new IllegalStateException("Cannot register new network messages after networking has been registered");
        MESSAGES.put(id, new NetworkMessage<>(reader, handler));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
        networkingRegistered = true;
    }

    private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new Tuple<>(action, tick));
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setB(work.getB() - 1);
            if (work.getB() == 0) {
                actions.add(work);
            }
        });

        actions.forEach(e -> e.getA().run());
        workQueue.removeAll(actions);
    }
}
