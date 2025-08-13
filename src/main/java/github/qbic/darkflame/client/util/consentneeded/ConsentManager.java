package github.qbic.darkflame.client.util.consentneeded;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ConsentManager {

    private static final Path CONSENT_FILE = FMLPaths.CONFIGDIR.get().resolve("df_consent.txt");

    private static boolean consent = false;

    static {
        load();
    }

    public static boolean hasConsentFile() {
        return Files.exists(CONSENT_FILE);
    }

    public static boolean getConsent() {
        return consent;
    }

    public static void setConsent(boolean value) {
        consent = value;
        save();
    }

    private static void save() {
        try {
            List<String> lines = List.of(
                    "consent=" + consent
//                    "streamerMode=" + streamerMode
            );
            Files.write(CONSENT_FILE, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        if (!Files.exists(CONSENT_FILE)) return;

        try {
            List<String> lines = Files.readAllLines(CONSENT_FILE);
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;

                switch (parts[0].trim()) {
                    case "consent" -> consent = Boolean.parseBoolean(parts[1].trim());
//                    case "streamerMode" -> streamerMode = Boolean.parseBoolean(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
