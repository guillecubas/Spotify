package es.ulpgc.spotify.downloader;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Albums {
    private final String artistId;
    private final List<List<String>> albums;


    public Albums(String artistId, List<List<String>> albums) {
        this.artistId = artistId;
        this.albums = albums;
    }

    void AlbumsMap() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        String json = accessor.get(String.format("/artists/%s/albums", artistId), Map.of());
        int size = JsonParser.parseString(json)
                .getAsJsonObject().get("items")
                .getAsJsonArray().size();
        for (int i = 0; i < size; i++) {
            String albumId = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("id"));
            String albumName = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("name"));
            String albumTotalTracks = String.valueOf(String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("total_tracks")));
            String albumReleaseDate = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("release_date"));
            String albumType = String.valueOf(JsonParser.parseString(json)
                    .getAsJsonObject().get("items")
                    .getAsJsonArray().get(i).getAsJsonObject().get("album_type"));


            albums.add(new ArrayList<>(Arrays.asList(
                    albumName.replaceAll("[^a-zA-Z0-9 ]", ""),
                    albumId.replaceAll("[^a-zA-Z0-9 ]", ""),
                    albumTotalTracks.replaceAll("[^a-zA-Z0-9 ]", ""),
                    albumReleaseDate.substring(1, albumReleaseDate.length() -1),
                    albumType.replaceAll("[^a-zA-Z0-9 ]", "")
            )));

        }


    }


}
