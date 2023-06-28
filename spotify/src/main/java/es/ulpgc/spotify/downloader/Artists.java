package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;
import java.util.Map;

public class Artists {
    private String artistId;
    private List<String> artists;

    public Artists(String artistId, List<String> artists) {
        this.artistId = artistId;
        this.artists = artists;
    }

    void artistMap() throws Exception {
        String json = getArtistJson();
        parseArtistJson(json);
    }

    private String getArtistJson() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        return accessor.get(String.format("/artists/%s", artistId), Map.of());
    }

    private void parseArtistJson(String json) {
        JsonObject artistObject = JsonParser.parseString(json).getAsJsonObject();
        String artistFollowers = artistObject.getAsJsonObject("followers")
                .get("total").getAsString();
        String artistName = artistObject.get("name").getAsString();

        artists.add(artistName);
        artists.add(artistId);
        artists.add(artistFollowers);
    }
}
