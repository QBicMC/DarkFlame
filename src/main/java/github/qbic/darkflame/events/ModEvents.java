package github.qbic.darkflame.events;

import github.qbic.darkflame.events.buildup.*;
import github.qbic.darkflame.events.client.ApplyShaderEvent;
import github.qbic.darkflame.events.client.ChangeWindowEvent;
import github.qbic.darkflame.events.client.IAMINYOURSYSTEMEventClient;
import github.qbic.darkflame.events.client.SingularityAttackClient;
import github.qbic.darkflame.events.dimension.hallways.HallwaysSpawnHerobrineEvent;
import github.qbic.darkflame.events.jumpscares.SingularityAttack;
import github.qbic.darkflame.events.major.*;
import github.qbic.darkflame.events.minor.DamagePlayerEvent;
import github.qbic.darkflame.events.minor.SignEvent;
import github.qbic.darkflame.events.minor.PullDownEvent;
import github.qbic.darkflame.util.StringClientModEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModEvents {

    // registry
    public static final List<ModEvent> BUILDUP_EVENTS = new ArrayList<>();
    public static final List<ModEvent> MINOR_EVENTS = new ArrayList<>();
    public static final List<ModEvent> MAJOR_EVENTS = new ArrayList<>();

    public static final Map<String, ClientModEvent> CLIENT_EVENTS = new HashMap<>();
    public static final Map<String, StringClientModEvent> STRING_CLIENT_EVENTS = new HashMap<>();

    public static void registerClientEvent(String id, ClientModEvent event) {
        CLIENT_EVENTS.put(id, event);
    }

    // events
    public static final SpawnSmileyBehindEvent SPAWN_SMILEY_BEHIND_EVENT = new SpawnSmileyBehindEvent();
    public static final WritingEvent WRITING_EVENT = new WritingEvent();
    public static final SpawnActiveEntityEvent SPAWN_ACTIVE_ENTITY_EVENT = new SpawnActiveEntityEvent();
    public static final SpawnVillagerInAirEvent SPAWN_VILLAGER_IN_AIR_EVENT = new SpawnVillagerInAirEvent();
    public static final DespawnMobsAroundEvent DESPAWN_MOBS_AROUND_EVENT = new DespawnMobsAroundEvent();
    public static final DamagePlayerEvent DAMAGE_PLAYER_EVENT = new DamagePlayerEvent();
    public static final PullDownEvent PULL_DOWN_EVENT = new PullDownEvent();
    public static final SpawnSingularityEvent SPAWN_SINGULARITY_EVENT = new SpawnSingularityEvent();
    public static final SpawnFakeMobEvent SPAWN_FAKE_MOB_EVENT = new SpawnFakeMobEvent();
    public static final ArmorStandEvent ARMOR_STAND_EVENT = new ArmorStandEvent();
    public static final FootStepsEvent FOOT_STEPS_EVENT = new FootStepsEvent();
    public static final CountDownToNightEvent COUNT_DOWN_TO_NIGHT_EVENT = new CountDownToNightEvent();
    public static final FakeDownloadEvent DOWNLOAD_EVENT = new FakeDownloadEvent();
    public static final SignWithNameEvent SIGN_WITH_NAME_EVENT = new SignWithNameEvent();
    public static final BlockDoorEvent BLOCK_DOOR_EVENT = new BlockDoorEvent();
    public static final JumpMessageEvent JUMP_MESSAGE_EVENT = new JumpMessageEvent();
    public static final SignEvent SIGN_EVENT = new SignEvent();
    public static final ReplaceTorchEvent REPLACE_TORCH_EVENT = new ReplaceTorchEvent();
    public static final IAMINYOURSYSTEMActivatorEvent IAMINYOURSYSTEM_EVENT = new IAMINYOURSYSTEMActivatorEvent();

    // dimension-specific
    public static final HallwaysSpawnHerobrineEvent HALLWAYS_SPAWN_HEROBRINE_EVENT = new HallwaysSpawnHerobrineEvent();

    public static final BlankEvent BLANK = new BlankEvent();

    // jumpscare events
    public static final SingularityAttack SINGULARITY_ATTACK_EVENT = new SingularityAttack();

    // client
    public static final ApplyShaderEvent APPLY_SHADER_EVENT = new ApplyShaderEvent();
    public static final ChangeWindowEvent CHANGE_WINDOW_EVENT = new ChangeWindowEvent();
    public static final SingularityAttackClient SINGULARITY_ATTACK_CLIENT_EVENT = new SingularityAttackClient();
    public static final IAMINYOURSYSTEMEventClient IAMINYOURSYSTEM_CLIENT_EVENT = new IAMINYOURSYSTEMEventClient();
}
