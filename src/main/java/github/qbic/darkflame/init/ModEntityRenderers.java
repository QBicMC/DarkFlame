/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.client.renderer.*;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.SMILEY.get(), SmileyRenderer::new);
		event.registerEntityRenderer(ModEntities.WATCHER.get(), WatcherRenderer::new);
		event.registerEntityRenderer(ModEntities.OBSERVER.get(), ObserverRenderer::new);
		event.registerEntityRenderer(ModEntities.SKIN_WALKER.get(), SkinWalkerRenderer::new);
		event.registerEntityRenderer(ModEntities.HEROBRINE.get(), HerobrineRenderer::new);
		event.registerEntityRenderer(ModEntities.CORRUPTED.get(), CorruptedRenderer::new);
		event.registerEntityRenderer(ModEntities.BEACON.get(), BeaconRenderer::new);
		event.registerEntityRenderer(ModEntities.SINGULARITY.get(), SingularityRenderer::new);
		event.registerEntityRenderer(ModEntities.FAKE_COW.get(), CowRenderer::new);
		event.registerEntityRenderer(ModEntities.FAKE_CHICKEN.get(), ChickenRenderer::new);
		event.registerEntityRenderer(ModEntities.FAKE_PIG.get(), PigRenderer::new);
		event.registerEntityRenderer(ModEntities.FAKE_VILLAGER.get(), VillagerRenderer::new);
		event.registerEntityRenderer(ModEntities.SMILER.get(), SmilerRenderer::new);

		event.registerEntityRenderer(ModEntities.INTRUSION_JUMPSCARE.get(), JumpscareRenderer::new);
		event.registerEntityRenderer(ModEntities.EXCLUSION_JUMPSCARE.get(), JumpscareRenderer::new);
	}
}