package es.ulpgc.spotify.downloader;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {

        List<String> artistId = new ArrayList<>();
        artistId.add("5C4PDR4LnhZTbVnKWXuDKD");
        artistId.add("4q3ewBCX7sLwd24euuV69X");
        artistId.add("1bAftSH8umNcGZ0uyV7LMg");
        artistId.add("7eLcDZDYHXZCebtQmVFL25");
        artistId.add("3bvfu2KAve4lPHrhEFDZna");
        SQLiteDBHelper sqLiteDBHelper = new SQLiteDBHelper();
        for (String value : artistId) {

            sqLiteDBHelper.createTable("Artists", "name TEXT, id TEXT, Followers TEXT");
            List<String> artistMap = new ArrayList<>();
            Artists artists = new Artists(value,artistMap);
            List<List<String>> albumMap = new ArrayList<>();
            artists.artistMap();
            StringBuilder stringBuilder = new StringBuilder();
            for (String artistValue : artistMap) {
                stringBuilder.append(String.format("'%s', ", artistValue));
            }
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 2));
            sqLiteDBHelper.insertData("Artists",
                    "name, id, Followers",
                    stringBuilder.toString());

            Albums albums = new Albums(value, albumMap);
            albums.AlbumsMap();
            sqLiteDBHelper.createTable("Albums",
                    "name TEXT, id TEXT, TotalTracks TEXT, ReleaseDate TEXT, Type TEXT");


            for (List<String> innerList : albumMap) {

                StringBuilder insertStatement = new StringBuilder();

                for (String string : innerList) insertStatement.append(String.format("'%s', ", string));

                insertStatement = new StringBuilder(insertStatement.substring(0, insertStatement.length() - 2));
                sqLiteDBHelper.insertData("Albums",
                        "name, id, TotalTracks, ReleaseDate, Type",
                        insertStatement.toString());

                ResultSet tracksId = sqLiteDBHelper.selectData("Albums", "id", null);
                while (tracksId.next()) {

                    List<List<String>> tracksMap = new ArrayList<>();

                    Tracks tracks = new Tracks(tracksId.getString("id"), tracksMap);
                    tracks.TracksMap();
                    sqLiteDBHelper.createTable("Tracks", "name TEXT, id TEXT, track_number TEXT, duration_ms TEXT, explicit TEXT");
                    for (List<String> stringList : tracksMap) {
                        StringBuilder s = new StringBuilder();
                        for (String string : stringList) s.append(String.format("'%s', ", string));
                        s = new StringBuilder(s.substring(0, s.length() - 2));
                        sqLiteDBHelper.insertData("Tracks",
                                "name, id, track_number, duration_ms, explicit",
                                s.toString());
                    }
                }


            }


        }
    }
}


//List<Artist> getArtists(...) filtering by some criteria
//List<Album> getAlbums(artist)
//List<Track> getTracks(artist),


