/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import github.qbic.darkflame.client.renderer.WatcherRenderer;
import github.qbic.darkflame.client.renderer.SmileyRenderer;
import github.qbic.darkflame.client.renderer.ObserverRenderer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DarkFlameModEntityRenderers {

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DarkFlameModEntities.SMILEY.get(), SmileyRenderer::new);
		event.registerEntityRenderer(DarkFlameModEntities.WATCHER.get(), WatcherRenderer::new);
		event.registerEntityRenderer(DarkFlameModEntities.OBSERVER.get(), ObserverRenderer::new);
	}
}