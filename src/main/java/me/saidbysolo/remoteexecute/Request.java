package me.saidbysolo.remoteexecute;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.bukkit.Bukkit;

public class Request {
    private final RemoteExecute plugin;

    public Request(RemoteExecute plugin) {
        this.plugin = plugin;
    }

    public byte[] makeJsonForm(String id, int point, String game, String key) {
        byte[] out = String.format("{\"id\": %s, \"point\": %d, \"game\": %s, \"key\":%s}", id, point, game, key)
                .getBytes(StandardCharsets.UTF_8);
        return out;
    }

    public int requestPostJson(String stringUrl, byte[] out) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(out.length);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();
            try (OutputStream os = connection.getOutputStream()) {
                os.write(out);
            } catch (Exception e) {
                System.out.println(e);
            }
            return connection.getResponseCode();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public int post(String id, int point, String game, String key) {
        byte[] out = makeJsonForm(id, point, game, key);
        return requestPostJson("https://webhook.site/82d39c46-11e8-4482-9126-093eb3ff49d5", out);
    }
}
