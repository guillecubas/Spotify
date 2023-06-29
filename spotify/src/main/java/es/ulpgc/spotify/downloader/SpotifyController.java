package es.ulpgc.spotify.downloader;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SpotifyController {
    static List<String> getArtistIds() {
        List<String> artistId = new ArrayList<>();
        artistId.add("5C4PDR4LnhZTbVnKWXuDKD");
        artistId.add("4q3ewBCX7sLwd24euuV69X");
        artistId.add("1bAftSH8umNcGZ0uyV7LMg");
        artistId.add("7eLcDZDYHXZCebtQmVFL25");
        artistId.add("3bvfu2KAve4lPHrhEFDZna");
        return artistId;
    }

    static void processArtist(String artistId, SQLiteDBHelper sqLiteDBHelper) throws Exception {
        processArtistsTable(artistId, sqLiteDBHelper);
        processAlbumsTable(artistId, sqLiteDBHelper);
    }

    private static void processArtistsTable(String artistId, SQLiteDBHelper sqLiteDBHelper) throws Exception {
        sqLiteDBHelper.createTable("Artists", "name TEXT, id TEXT, Followers TEXT");
        List<String> artistMap = new ArrayList<>();
        Artists artists = new Artists(artistId, artistMap);
        artists.artistMap();
        String insertStatement = buildInsertStatement(artistMap);
        sqLiteDBHelper.insertData("Artists", "name, id, Followers", insertStatement);
    }

    private static void processAlbumsTable(String artistId, SQLiteDvlll  l , ,                                                                                                                       BHelper sqLiteDBHelper) throws Exception {
        List<List<String>> albumMap = new ArrayList<>();
        Albums albums = new Albums(artistId, albumMap);
        albums.AlbumsMap();
        sqLiteDBHelper.createTable("Albums", "name TEXT, id TEXT, TotalTracks TEXT, ReleaseDate TEXT, Type TEXT");

        for (List<String> innerList : albumMap) {
            String insertStatement = buildInsertStatement(innerList);
            sqLiteDBHelper.insertData("Albums", "name, id, TotalTracks, ReleaseDate, Type", insertStatement);

            ResultSet tracksId = sqLiteDBHelper.selectData("Albums", "id", null);
            while (tracksId.next()) {
                processTracksTable(tracksId.getString("id"), sqLiteDBHelper);
            }
        }
    }

    private static void processTracksTable(String albumId, SQLiteDBHelper sqLiteDBHelper) throws Exception {
        List<List<String>> tracksMap = new ArrayList<>();
        Tracks tracks = new Tracks(albumId, tracksMap);
        tracks.TracksMap();
        sqLiteDBHelper.createTable("Tracks", "name TEXT, id TEXT, track_number TEXT, duration_ms TEXT, explicit TEXT");
        for (List<String> stringList : tracksMap) {
            String insertStatement = buildInsertStatement(stringList);
            sqLiteDBHelper.insertData("Tracks", "name, id, track_number, duration_ms, explicit", insertStatement);
        }
    }

    private static String buildInsertStatement(List<String> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : values) {
            stringBuilder.append(String.format("'%s', ", value));
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }
}

