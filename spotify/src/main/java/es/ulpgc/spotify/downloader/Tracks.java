package es.ulpgc.spotify.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
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
        String json = getAlbumTracksJson();
        parseTracksJson(json);
    }

    private String getAlbumTracksJson() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        return accessor.get(String.format("/albums/%s/tracks", albumId), Map.of());
    }

    private void parseTracksJson(String json) {
        JsonArray items = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            JsonObject trackObject = items.get(i).getAsJsonObject();
            String trackId = trackObject.get("id").getAsString();
            String trackName = trackObject.get("name").getAsString();
            String trackNumber = trackObject.get("track_number").getAsString();
            String trackDurationMs = trackObject.get("duration_ms").getAsString();
            String trackExplicit = trackObject.get("explicit").getAsString();

            tracks.add(createTrackEntry(trackName, trackId, trackNumber, trackDurationMs, trackExplicit));
        }
    }

    private List<String> createTrackEntry(String name, String id, String trackNumber, String durationMs, String explicit) {
        List<String> trackEntry = new ArrayList<>();
        trackEntry.add(name.replaceAll("[^a-zA-Z0-9 ]", ""));
        trackEntry.add(id.replaceAll("[^a-zA-Z0-9 ]", ""));
        trackEntry.add(trackNumber.replaceAll("[^a-zA-Z0-9 ]", ""));
        trackEntry.add(durationMs.replaceAll("[^a-zA-Z0-9 ]", ""));
        trackEntry.add(explicit.replaceAll("[^a-zA-Z0-9 ]", ""));
        return trackEntry;
    }
}
