package github.qbic.darkflame.events.client;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.util.StringClientModEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ApplyShaderEvent extends StringClientModEvent {
    @Override
    public void execute(String data) {
        if (data == null || data.equals("none") || data.isEmpty()) {
            Minecraft.getInstance().gameRenderer.clearPostEffect();
            return;
        }

        if (data.contains(":")) {
            ClientUtil.applyShader(ResourceLocation.parse(data));
        } else {
            ClientUtil.applyShader(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, data));
        }
    }

    @Override
    protected String getDefault() {
        return "none";
    }

    @Override
    public String clientEventID() {
        return "shader";
    }
}
