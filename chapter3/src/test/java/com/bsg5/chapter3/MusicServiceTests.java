package com.bsg5.chapter3;

import com.bsg5.chapter3.model.Song;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MusicServiceTests {
    private Object[][] model = new Object[][]{
            {"Threadbare Loaf", "Someone Stole the Flour", 4},
            {"Threadbare Loaf", "What Happened To Our First CD?", 17},
            {"Therapy Zeppelin", "Medium", 4},
            {"Clancy in Silt", "Igneous", 5}
    };

    void iterateOverModel(Consumer<Object[]> consumer) {
        for (Object[] data : model) {
            consumer.accept(data);
        }
    }

    void populateService(MusicService service) {
        iterateOverModel(data -> {
            for (int i = 0; i < (Integer) data[2]; i++) {
                service.voteForSong((String) data[0], (String) data[1]);
            }
        });
    }

    void reset(MusicService service) {
        if (service instanceof Resettable) {
            ((Resettable) service).reset();
        } else {
            throw new RuntimeException(service +
                    " does not implement Resettable.");
        }
    }

    void testSongVoting(MusicService service) {
        reset(service);
        populateService(service);
        iterateOverModel(data ->
                assertEquals(
                        ((Integer) data[2]).intValue(),
                        service.getSong((String) data[0],
                                (String) data[1]).getVotes()
                ));
    }

    void testSongsForArtist(MusicService service) {
        reset(service);
        populateService(service);
        List<Song> songs = service.getSongsForArtist("Threadbare Loaf");
        assertEquals(2,songs.size());
        assertEquals("What Happened To Our First CD?", songs.get(0).getName());
        assertEquals(17, songs.get(0).getVotes());
        assertEquals("Someone Stole the Flour", songs.get(1).getName());
        assertEquals(4, songs.get(1).getVotes());
    }

    void testMatchingArtistNames(MusicService service) {
        reset(service);
        populateService(service);
        List<String> names = service.getMatchingArtistNames("Th");
        assertEquals(2, names.size());
        assertEquals("Therapy Zeppelin", names.get(0));
        assertEquals("Threadbare Loaf", names.get(1));
    }

    void testMatchingSongNamesForArtist(MusicService service) {
        reset(service);
        populateService(service);
        List<String> names = service.getMatchingSongNamesForArtist(
                "Threadbare Loaf", "W"
        );
        assertEquals(1, names.size());
        assertEquals("What Happened To Our First CD?", names.get(0));
    }
}
