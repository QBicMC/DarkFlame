/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import github.qbic.darkflame.entity.WatcherEntity;
import github.qbic.darkflame.entity.SmileyEntity;
import github.qbic.darkflame.entity.ObserverEntity;
import github.qbic.darkflame.DarkFlame;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DarkFlameModEntities {
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

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, registryname))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		SmileyEntity.init(event);
		WatcherEntity.init(event);
		ObserverEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SMILEY.get(), SmileyEntity.createAttributes().build());
		event.put(WATCHER.get(), WatcherEntity.createAttributes().build());
		event.put(OBSERVER.get(), ObserverEntity.createAttributes().build());
	}
}