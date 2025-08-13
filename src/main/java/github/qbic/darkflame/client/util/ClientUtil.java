package github.qbic.darkflame.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostChainConfig;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.io.IOException;

public class ShaderApplier {
    private static PostChain shaderChain = null;

    public static void onRenderTick(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc = Minecraft.getInstance();

        if (shaderChain == null && mc.getMainRenderTarget() != null) {
            try {
                shaderChain = PostChain.load()
                shaderChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        if (shaderChain != null) {
            shaderChain.process(mc.getPartialTick());
        }
    }

    public static void clear() {
        if (shaderChain != null) {
            shaderChain.close();
            shaderChain = null;
        }
    }
}
