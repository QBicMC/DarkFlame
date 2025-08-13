/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.entity.*;
import github.qbic.darkflame.entity.fake.FakeChicken;
import github.qbic.darkflame.entity.fake.FakeCow;
import github.qbic.darkflame.entity.fake.FakePig;
import github.qbic.darkflame.entity.fake.FakeVillager;
import github.qbic.darkflame.entity.jumpscare.ExclusionJumpscareEntity;
import github.qbic.darkflame.entity.jumpscare.IntrusionJumpscareEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, DarkFlame.MOD_ID);
	public static final DeferredHolder<EntityType<?>, EntityType<SmileyEntity>> SMILEY = register("smiley",
			EntityType.Builder.<SmileyEntity>of(SmileyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune()
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<WatcherEntity>> WATCHER = register("watcher",
			EntityType.Builder.<WatcherEntity>of(WatcherEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<ObserverEntity>> OBSERVER = register("observer",
			EntityType.Builder.<ObserverEntity>of(ObserverEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<SkinWalkerEntity>> SKIN_WALKER = register("skin_walker",
			EntityType.Builder.<SkinWalkerEntity>of(SkinWalkerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<BeaconEntity>> BEACON = register("beacon",
			EntityType.Builder.<BeaconEntity>of(BeaconEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<SingularityEntity>> SINGULARITY = register("singularity",
			EntityType.Builder.<SingularityEntity>of(SingularityEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(2.0f, 2.0f));

	public static final DeferredHolder<EntityType<?>, EntityType<FakeCow>> FAKE_COW = register("fake_cow",
			EntityType.Builder.<FakeCow>of(FakeCow::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.9f, 1.4f).eyeHeight(1.3f));

	public static final DeferredHolder<EntityType<?>, EntityType<FakeChicken>> FAKE_CHICKEN = register("fake_chicken",
			EntityType.Builder.<FakeChicken>of(FakeChicken::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.9f, 1.4f).eyeHeight(1.3f));

	public static final DeferredHolder<EntityType<?>, EntityType<FakePig>> FAKE_PIG = register("fake_pig",
			EntityType.Builder.<FakePig>of(FakePig::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.9f, 1.4f).eyeHeight(1.3f));

	public static final DeferredHolder<EntityType<?>, EntityType<FakeVillager>> FAKE_VILLAGER = register("fake_villager",
			EntityType.Builder.<FakeVillager>of(FakeVillager::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.9f, 1.4f).eyeHeight(1.3f));

	public static final DeferredHolder<EntityType<?>, EntityType<IntrusionJumpscareEntity>> INTRUSION_JUMPSCARE = register("intrusion_jumpscare",
			EntityType.Builder.<IntrusionJumpscareEntity>of(IntrusionJumpscareEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<ExclusionJumpscareEntity>> EXCLUSION_JUMPSCARE = register("exclusion_jumpscare",
			EntityType.Builder.<ExclusionJumpscareEntity>of(ExclusionJumpscareEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, registryname))));
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SMILEY.get(), SmileyEntity.createAttributes().build());
		event.put(WATCHER.get(), WatcherEntity.createAttributes().build());
		event.put(OBSERVER.get(), ObserverEntity.createAttributes().build());
		event.put(SKIN_WALKER.get(), SkinWalkerEntity.createAttributes().build());
		event.put(BEACON.get(), BeaconEntity.createAttributes().build());
		event.put(SINGULARITY.get(), SingularityEntity.createAttributes().build());
		event.put(FAKE_COW.get(), FakeCow.createAttributes().build());
		event.put(FAKE_CHICKEN.get(), FakeChicken.createAttributes().build());
		event.put(FAKE_PIG.get(), FakePig.createAttributes().build());
		event.put(FAKE_VILLAGER.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).build());
		event.put(INTRUSION_JUMPSCARE.get(), IntrusionJumpscareEntity.createAttributes().build());
		event.put(EXCLUSION_JUMPSCARE.get(), ExclusionJumpscareEntity.createAttributes().build());
	}
}