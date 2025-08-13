package github.qbic.darkflame.client.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

// does not work in linux or mac
public class Window {
    public static final int MB_SYSTEMMODAL = 0x00001000;
    public static boolean shouldShow = true;

    public static void show(String msg, String title, boolean modalFlag) {
        if (!shouldShow) return;
        if (isWindows()) {
            User32.INSTANCE.MessageBoxA(0, msg, title, modalFlag ? MB_SYSTEMMODAL : 0);
        } else {
            System.err.println("[" + title + "] " + msg);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class);
        void MessageBoxA(int hwnd, String text, String caption, int type);
    }
}
