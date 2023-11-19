// https://docs.tebex.io/plugin/

package lol.aabss.skripttebex;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TebexAPI {
    public static JsonObject api(String endpoint, String method) throws IOException {
        String secret = (String) SkriptTebex.getPlugin(SkriptTebex.class).getConfig().get("tebex-secret");
        URL url = new URL("https://plugin.tebex.io/" + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-Tebex-Secret", secret);
        if (connection.getResponseCode() == 200 || connection.getResponseCode() == 204 || connection.getResponseCode() == 201){
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return JsonParser.parseString(response.toString()).getAsJsonObject();
        }
        return null;
    }

    public static JsonObject api(String endpoint, String method, JsonObject body) throws IOException {
        String secret = (String) SkriptTebex.getPlugin(SkriptTebex.class).getConfig().get("tebex-secret");
        URL url = new URL("https://plugin.tebex.io/" + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-Tebex-Secret", secret);
        connection.setDoOutput(true);
        if (body != null) {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
        if (connection.getResponseCode() == 200 || connection.getResponseCode() == 204 || connection.getResponseCode() == 201) {
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return JsonParser.parseString(response.toString()).getAsJsonObject();
        }
        return null;
    }

    public static boolean isSecretValid(String secret) throws IOException{
        URL url = new URL("https://plugin.tebex.io/information");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Tebex-Secret", secret);
        return connection.getResponseCode() == 200;
    }

}