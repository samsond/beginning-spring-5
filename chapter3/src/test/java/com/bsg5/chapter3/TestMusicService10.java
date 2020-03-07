package com.bsg5.chapter3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class TestMusicService10 {
    @Autowired
    MusicServiceTests tests;

    static Stream<Arguments> configurations() {
        return Stream.of(
                Arguments.of("/config-01.xml"),
                Arguments.of("/config-02.xml"),
                Arguments.of("/config-03.xml"),
                Arguments.of("/config-04.xml"),
                Arguments.of("/config-05.xml"),
                Arguments.of("/config-06.xml"),
                Arguments.of(Configuration7.class),
                Arguments.of(Configuration8.class),
                Arguments.of(Configuration9.class),
                Arguments.of(Configuration10.class)
        );
    }

    // tag::runMethod[]
    private void runMethod(Object config, Consumer<MusicService> method) {
        ApplicationContext context;
        if (config instanceof String) {
            context = new ClassPathXmlApplicationContext(config.toString());
        } else {
            if (config instanceof Class<?>) {
                context = new AnnotationConfigApplicationContext((Class<?>) config);
            } else {
                throw new RuntimeException("Invalid configuration argument: " + config);
            }
        }
        MusicService service = context.getBean(MusicService.class);
        method.accept(service);
    }

    @Test
    public void testRunMethod() {

        RuntimeException throwException = assertThrows(RuntimeException.class, () -> runMethod(Boolean.TRUE, tests::testSongVoting));
        assertEquals("Invalid configuration argument: true", throwException.getMessage());
    }
    // end::runMethod[]

    @ParameterizedTest
    @MethodSource("configurations")
    public void testSongVoting(Object config) {
        runMethod(config, tests::testSongVoting);
    }

    @ParameterizedTest
    @MethodSource("configurations")
    public void testGetMatchingArtistNames(Object config) {
        runMethod(config, tests::testMatchingArtistNames);
    }

    @ParameterizedTest
    @MethodSource("configurations")
    public void testGetSongsForArtist(Object config) {
        runMethod(config, tests::testSongsForArtist);
    }

    @ParameterizedTest
    @MethodSource("configurations")
    public void testMatchingSongNamesForArtist(Object config) {
        runMethod(config, tests::testMatchingSongNamesForArtist);
    }
}
