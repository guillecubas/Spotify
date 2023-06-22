package es.ulpgc.spotify.downloader;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Artists {
    private String artistId;
    private List<String> artists;

    public Artists(String artistId, List<String> artists) throws Exception {
        this.artistId = artistId;
        this.artists = artists;
    }

    void artistMap() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        String json = accessor.get(String.format("/artists/%s", artistId), Map.of());
        String artistFollowers = String.valueOf(JsonParser.parseString(json)
                .getAsJsonObject().get("followers")
                .getAsJsonObject().get("total"));
        String artistName = String.valueOf(JsonParser.parseString(json)
                .getAsJsonObject().get("name"));
        artists.add(artistName);
        artists.add(artistId);
        artists.add(artistFollowers);
    }
}
