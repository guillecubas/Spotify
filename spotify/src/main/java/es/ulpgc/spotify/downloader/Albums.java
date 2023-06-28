package es.ulpgc.spotify.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
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
        String json = getArtistAlbumsJson();
        parseAlbumsJson(json);
    }

    private String getArtistAlbumsJson() throws Exception {
        SpotifyAccessor accessor = new SpotifyAccessor();
        return accessor.get(String.format("/artists/%s/albums", artistId), Map.of());
    }

    private void parseAlbumsJson(String json) {
        JsonArray items = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            JsonObject albumObject = items.get(i).getAsJsonObject();
            String albumId = albumObject.get("id").getAsString();
            String albumName = albumObject.get("name").getAsString();
            String albumTotalTracks = albumObject.get("total_tracks").getAsString();
            String albumReleaseDate = albumObject.get("release_date").getAsString();
            String albumType = albumObject.get("album_type").getAsString();

            albums.add(createAlbumEntry(albumName, albumId, albumTotalTracks, albumReleaseDate, albumType));
        }
    }

    private List<String> createAlbumEntry(String name, String id, String totalTracks, String releaseDate, String type) {
        List<String> albumEntry = new ArrayList<>();
        albumEntry.add(name.replaceAll("[^a-zA-Z0-9 ]", ""));
        albumEntry.add(id.replaceAll("[^a-zA-Z0-9 ]", ""));
        albumEntry.add(totalTracks.replaceAll("[^a-zA-Z0-9 ]", ""));
        albumEntry.add(releaseDate.substring(1, releaseDate.length() - 1));
        albumEntry.add(type.replaceAll("[^a-zA-Z0-9 ]", ""));
        return albumEntry;
    }
}
