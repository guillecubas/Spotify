package es.ulpgc.spotify.downloader;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static es.ulpgc.spotify.downloader.SpotifyController.getArtistIds;
import static es.ulpgc.spotify.downloader.SpotifyController.processArtist;


public class Main {
    public static void main(String[] args) throws Exception {
        SQLiteDBHelper sqLiteDBHelper = new SQLiteDBHelper();
        List<String> artistId = getArtistIds();

        for (String artistValue : artistId) {
            processArtist(artistValue, sqLiteDBHelper);
        }
    }
}