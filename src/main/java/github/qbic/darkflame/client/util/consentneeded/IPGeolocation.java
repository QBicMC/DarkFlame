package github.qbic.darkflame.client.util.consentneeded;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPGeolocation {
    public static void main(String[] args) throws Exception {
        // test da ip code
        String ip = getPublicIP();
        String apiURL = "http://ip-api.com/json/" + ip;

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) content.append(inputLine);
        in.close();

        System.out.println("Location info: " + content);
        System.out.println(getInfoFromIP(ip, "city"));
    }

    public static String getPublicIP() {
        if (!ConsentManager.getConsent()) return "REDACTED";

        try {
            URL url = new URL("https://api.ipify.org");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String ip = in.readLine();
            in.close();
            return ip;
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static String getInfoFromIP(String ip, String param) {

        if (!ConsentManager.getConsent()) return "REDACTED";

        try {
            URL url = new URL("http://ip-api.com/json/" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            return json.has(param) ? json.get(param).getAsString() : "";
        } catch (Exception e) {
            return "";
        }
    }
}