package github.qbic.darkflame.client.consentneeded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

public class NameFetcher {

    public static String getSystemName() {
        if (!ConsentManager.getConsent()) return "§kREDACTED§r";

        return System.getProperty("user.name");
    }

    public static Optional<String> getFirstName() {
        if (!ConsentManager.getConsent()) return Optional.of("§kREDACTED§r");

        String fullName = getRealName().isPresent() ? getRealName().get() : getSystemName();
        String[] parts = fullName.split("[._\s]");
        return parts.length > 0 ? Optional.of(parts[0]) : Optional.empty();
    }

    public static Optional<String> getLastName() {
        if (!ConsentManager.getConsent()) return Optional.of("§kREDACTED§r");

        String fullName = getRealName().isPresent() ? getRealName().get() : getSystemName();
        String[] parts = fullName.split("[._\s]");
        return parts.length > 1 ? Optional.of(parts[parts.length - 1]) : Optional.empty();
    }

    public static Optional<String> getRealName() {
        if (!ConsentManager.getConsent()) return Optional.of("§kREDACTED§r");

        try {

            Process process = new ProcessBuilder("powershell", "-Command",
                    "(Get-WmiObject Win32_UserAccount -Filter \\\"Name='$env:USERNAME'\\\").FullName 2>$null").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            return line != null && !line.isEmpty() ? Optional.of(line) : Optional.empty();
        } catch (Exception e) {

            System.out.println("Error retrieving sign-in display name: " + e.getMessage());
            return Optional.empty();
        }
    }
}