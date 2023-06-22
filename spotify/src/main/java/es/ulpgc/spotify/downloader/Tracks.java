package es.ulpgc.spotify.downloader;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Tracks {
    private final String albumId;
    private final List<List<String>> tracks;


    public Tracks(String albumId, List<List<String>> tracks) {
        this.albumId = albumId;
        this.tracks = tracks;
    }


    void TracksMap() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        String json = accessor.get(String.format("/albums/%s/tracks", albumId), Map.of());
        int size = JsonParser.parseString(json)
                .getAsJsonObject().get("items")
                .getAsJsonArray().size();
        for (int i = 0; i < size; i++) {
            String trackId = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("id"));
            String trackName = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("name"));
            String trackNumber = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("track_number"));
            String trackDurationMs = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("duration_ms"));
            String trackExplicit = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("explicit"));
            tracks.add(new ArrayList<>(Arrays.asList(
                    trackName.replaceAll("[^a-zA-Z0-9 ]", ""),
                    trackId.replaceAll("[^a-zA-Z0-9 ]", ""),
                    trackNumber.replaceAll("[^a-zA-Z0-9 ]", ""),
                    trackDurationMs.replaceAll("[^a-zA-Z0-9 ]", ""),
                    trackExplicit.replaceAll("[^a-zA-Z0-9 ]", "")
            )));
        }

    }
}
