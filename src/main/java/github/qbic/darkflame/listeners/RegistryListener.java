package github.qbic.darkflame.listeners;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.dimension.generation.LabyrinthDimGen;
import github.qbic.darkflame.dimension.generation.MEGALOPHOBIADimGen;
import github.qbic.darkflame.dimension.generation.TheHallwaysDimGen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber
public class RegistryListener {

    @SubscribeEvent
    public static void onRegisterChunkGenerators(RegisterEvent event) {
        DarkFlame.registerDimGenerator(event, "labyrinth", LabyrinthDimGen.CODEC);
        DarkFlame.registerDimGenerator(event, "the_hallways", TheHallwaysDimGen.CODEC);
        DarkFlame.registerDimGenerator(event, "megalophobia", MEGALOPHOBIADimGen.CODEC);
    }
}
