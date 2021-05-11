package me.saidbysolo.remoteexecute;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
public class Request {
    public byte[] makeJsonForm(String id, int point, String game, String key) {
        byte[] out = String.format("{\"id\": %s, \"point\": %d, \"game\": %s, \"key\":%s}", id, point, game, key)
                .getBytes(StandardCharsets.UTF_8);
        return out;
    }

    public int requestPostJson(String stringUrl, byte[] out) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // 메소드
            connection.setConnectTimeout(3000); // 타임아웃
            connection.setReadTimeout(3000);// 타임아웃
            connection.setDoOutput(true); //// 필수
            connection.setFixedLengthStreamingMode(out.length); // // 필수
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // 헤더 담을거라 보면될듯
            connection.connect(); // 연결
            try (OutputStream os = connection.getOutputStream()) { // 여기서 요청시작
                os.write(out); // 요청 씀
            } catch (Exception e) {
                System.out.println(e);
            }
            return connection.getResponseCode(); // 상태 코드 반환
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public int post(String id, int point, String game, String key) {
        byte[] out = makeJsonForm(id, point, game, key); // 폼 만들기
        return requestPostJson("https://webhook.site/82d39c46-11e8-4482-9126-093eb3ff49d5", out); // url 바꿔야함
    }
}
