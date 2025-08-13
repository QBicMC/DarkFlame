package github.qbic.darkflame.events.client;

import com.mojang.blaze3d.platform.Window;
import github.qbic.darkflame.events.ClientModEvent;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;

public class ChangeWindowEvent extends ClientModEvent {
    public static final String[] TITLES = {
        "ERROR.ACCESS_DENIED.ADMIN_ONLY"
    };

    @Override
    public String clientEventID() {
        return "change_window";
    }

    @Override
    public void execute() {
        Window win = minecraft().getWindow();
        win.setTitle(Util.getRandom(TITLES, RandomSource.create()));
    }
}
