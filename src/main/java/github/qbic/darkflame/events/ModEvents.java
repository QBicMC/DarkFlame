package github.qbic.darkflame.util;

import github.qbic.darkflame.events.buildup.DespawnMobsAroundEvent;
import github.qbic.darkflame.events.buildup.SpawnVillagerInAirEvent;
import github.qbic.darkflame.events.buildup.WritingEvent;
import github.qbic.darkflame.events.client.ApplyShaderEvent;
import github.qbic.darkflame.events.client.ChangeWindowEvent;
import github.qbic.darkflame.events.major.SpawnActiveEntityEvent;
import github.qbic.darkflame.events.major.SpawnSmileyBehindEvent;
import github.qbic.darkflame.events.minor.DamagePlayerEvent;
import github.qbic.darkflame.events.minor.PullDownEvent;

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

    // client
    public static final ApplyShaderEvent APPLY_SHADER_EVENT = new ApplyShaderEvent();
    public static final ChangeWindowEvent CHANGE_WINDOW_EVENT = new ChangeWindowEvent();
}
