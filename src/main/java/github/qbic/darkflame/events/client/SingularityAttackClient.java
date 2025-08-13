package github.qbic.darkflame.events.client;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.events.ClientModEvent;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class SingularityAttackClient extends ClientModEvent {
    public static final int TIME_TICKS = 500;
    public static final int NUMBER_FRAMES = 50;
    private int timeModifier = 17000;

    public static final String[] OVERLAYS = {
            "obey/obey_black_red_bg.png",
            "obey/obey_red.png",
            "obey/obey_white.png",
            "obey/obey_white_red_bg.png",
            "giveup/giveup_white_red_bg.png",
            "giveup/giveup_black_red_bg.png",
            "giveup/giveup_red.png",
            "giveup/giveup_white.png",
            "givein/givein_red.png",
            "givein/givein_white.png",
            "rnd/rnd_control_red.png",
            "rnd/rnd_control_black.png",
            "rnd/rnd_givein_red.png",
            "rnd/rnd_givein_white.png",
            "rnd/rnd_infect_black.png",
            "rnd/rnd_infect_white.png"
    };

    @Override
    public void execute() {
        if (!(target().level() instanceof ClientLevel)) return;
        for (int i = 0; i < NUMBER_FRAMES; i++) {
            RandomSource random = RandomSource.create();

            float rndOffset = 0.5f;

            float wScale = random.nextFloat() * rndOffset + 1.0f;
            float hScale = random.nextFloat() * rndOffset + 1.0f;

            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();

            int width = (int) (w * wScale);
            int height = (int) (h * hScale);
//            int x = random.nextInt(0, (int) (1280 * wScale));
//            int y = random.nextInt(0, (int) (720 * hScale));
            int xMin = w - width;
            int yMin = h - height;

            int x = random.nextInt(xMin, 1);
            int y = random.nextInt(yMin, 1);

            if (random.nextFloat() < 0.1) {
                for (int j = 0; j < 5; j++) {
                    String loc = Util.getRandom(OVERLAYS, random);
                    github.qbic.darkflame.util.Util.scheduleClient(() -> ClientUtil.setImage(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "textures/img/" + loc), x, y, width, height), ((TIME_TICKS / NUMBER_FRAMES) * i) + (j * 2));
                }
            } else {
                String loc = Util.getRandom(OVERLAYS, random);
                github.qbic.darkflame.util.Util.scheduleClient(() -> {
                    if (github.qbic.darkflame.util.Util.gamble(0.5)) timeModifier += 12000;
                    ClientUtil.setImage(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "textures/img/" + loc), x, y, width, height);
                    ClientUtil.modifyDayTime(timeModifier);
                }, (TIME_TICKS / NUMBER_FRAMES) * i);
            }
        }

        github.qbic.darkflame.util.Util.scheduleClient(() -> {
            ClientUtil.clearImage();
            ClientUtil.backToNormalTime();
        }, TIME_TICKS + 5);
    }

    @Override
    public String clientEventID() {
        return "singularity_attack";
    }
}
