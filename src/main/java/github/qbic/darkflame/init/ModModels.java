/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.client.model.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModModels {

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SmileyModel.LAYER_LOCATION, SmileyModel::createBodyLayer);
		event.registerLayerDefinition(SimpleHumanoidModel.LAYER_LOCATION, SimpleHumanoidModel::createBodyLayer);
		event.registerLayerDefinition(SingularityModel.LAYER_LOCATION, SingularityModel::createBodyLayer);
		event.registerLayerDefinition(BlankModel.LAYER_LOCATION, BlankModel::createBodyLayer);
		event.registerLayerDefinition(SmilerModel.LAYER_LOCATION, SmilerModel::createBodyLayer);
	}
}