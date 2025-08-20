package github.qbic.darkflame.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import github.qbic.darkflame.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Set;

public class ClientUtil {
    private static final ResourceLocation DEFAULT_WIDE_SKIN = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png");
    private static final ResourceLocation DEFAULT_SLIM_SKIN = ResourceLocation.withDefaultNamespace("textures/entity/player/slim/steve.png");

    public static void applyShader(ResourceLocation shader) {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
//        Util.printDBG(((Minecraft.getInstance().gameRenderer.currentPostEffect().toString() == null ? "" : Minecraft.getInstance().gameRenderer.currentPostEffect().toString())).toLowerCase(java.util.Locale.ENGLISH));
        renderer.setPostEffect(shader);
    }

    public static void applyShaderWithUniform(ResourceLocation shader, String uniformName, float value) {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        PostChain postChain = Minecraft.getInstance().getShaderManager().getPostChain(shader, Set.of());
//        Util.printDBG(((Minecraft.getInstance().gameRenderer.currentPostEffect().toString() == null ? "" : Minecraft.getInstance().gameRenderer.currentPostEffect().toString())).toLowerCase(java.util.Locale.ENGLISH));
        renderer.setPostEffect(shader);
    }

    public static ResourceLocation getSkinTexture(Player p) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || p == null) return DEFAULT_WIDE_SKIN;

        AbstractClientPlayer player = (AbstractClientPlayer) mc.level.getPlayerByUUID(p.getUUID());
        if (player == null) return DEFAULT_WIDE_SKIN;

        return player.getSkin().texture();
    }

    private static ResourceLocation currentImage = null;
    private static int posX, posY, width, height;

    public static void setImage(ResourceLocation tex, int x, int y, int w, int h) {
        currentImage = tex;
        posX = x;
        posY = y;
        width = w;
        height = h;
    }

    public static void setImageFullscreen(ResourceLocation tex) { // assumes 1280 x 720
        currentImage = tex;
        posX = 0;
        posY = 0;
        width = 1280;
        height = 720;
    }

    public static void clearImage() {
        currentImage = null;
    }

    public static void renderOverlay(GuiGraphics gfx) {
        if (currentImage == null) return;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        int w = gfx.guiWidth();
        int h = gfx.guiHeight();

        gfx.blit(
                RenderType::guiTextured,
                currentImage,
                posX, posY,
                0, 0,
                1280, 720,
                width, height
        );

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);

//        if (currentImage == null) return;

//        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//        RenderSystem.disableDepthTest();
//        RenderSystem.depthMask(false);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.blendFuncSeparate(
//                GlStateManager.SourceFactor.SRC_ALPHA,
//                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
//                GlStateManager.SourceFactor.ONE,
//                GlStateManager.DestFactor.ZERO
//        );

//        gfx.blit(
//                RenderType::guiTextured,
//                currentImage,
//                posX, posY,
//                0f, 0f,
//                1280, 720,
//                /*width*/w, /*height*/h,
//                1280, 720
//        );

//        RenderSystem.depthMask(true);
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
//        RenderSystem.disableBlend();
//        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

//    public static EntityModel getPlayerModel(Player p) {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.level == null) return new PlayerModel();
//
//        AbstractClientPlayer player = (AbstractClientPlayer) mc.level.getPlayerByUUID(p.getUUID());
//        String model = player.getSkin().model().id();
//
//        if (model.contains("slim")) return DEFAULT_SLIM_SKIN;
//        return DEFAULT_WIDE_SKIN;
//    }

    private static boolean modifyDayTime = false;
    private static long modifiedDayTime = 0;

    public static void modifyDayTime(long time) {
        modifyDayTime = true;
        modifiedDayTime = time;
    }

    public static Long getModifiedDayTime() {
        return modifyDayTime ? modifiedDayTime : null;
    }

    public static void backToNormalTime() {
        modifyDayTime = false;
    }

    public static void setWallpaper(ResourceLocation resource) {
        try {
            Resource res = Minecraft.getInstance().getResourceManager().getResource(resource).orElseThrow();
            try (InputStream stream = res.open()) {
                File tempFile = File.createTempFile("wallpaper", ".png");
                tempFile.deleteOnExit();
                Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String commandReg = "reg add \"HKCU\\Control Panel\\Desktop\" /v Wallpaper /t REG_SZ /d \"" + tempFile.getAbsolutePath() + "\" /f";
                Runtime.getRuntime().exec(commandReg);
                Runtime.getRuntime().exec("RUNDLL32.EXE user32.dll,UpdatePerUserSystemParameters");
            }
        } catch (Exception e) {
            Util.printDBG("could not change wallpaper");
        }
    }

    public static void openInDefaultApp(ResourceLocation resource) {
        try {
            Resource res = Minecraft.getInstance().getResourceManager().getResource(resource).orElseThrow();
            String path = resource.getPath();
            String ext = path.contains(".") ? path.substring(path.lastIndexOf('.')) : ".tmp";

            try (InputStream stream = res.open()) {
                File tempFile = File.createTempFile("resource", ext);
                tempFile.deleteOnExit();
                Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                new ProcessBuilder("cmd", "/c", "start", "", tempFile.getAbsolutePath()).inheritIO().start();
            }
        } catch (Exception e) {
            Util.printDBG("could not open " + resource.getPath() + ": " + e.getMessage());
        }
    }

    public static void openApp(String executable) {
        try {
            new ProcessBuilder(executable).start();
        } catch (IOException e) {
            Util.printDBG("could not open " + executable);
        }
    }
}
