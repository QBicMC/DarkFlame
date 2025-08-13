/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import github.qbic.darkflame.client.model.ModelWatcher;
import github.qbic.darkflame.client.model.ModelSmiley;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class DarkFlameModModels {

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelSmiley.LAYER_LOCATION, ModelSmiley::createBodyLayer);
		event.registerLayerDefinition(ModelWatcher.LAYER_LOCATION, ModelWatcher::createBodyLayer);
	}
}