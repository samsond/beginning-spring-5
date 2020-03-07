package com.bsg5.chapter3;

import com.bsg5.chapter3.mem03.CapLeadingNormalizer;
import com.bsg5.chapter3.mem03.SimpleNormalizer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/config-04.xml")
public class TestConfigurationImport {
    @Autowired
    ApplicationContext context;

    static Stream<Arguments> resources() {
        return Stream.of(
                Arguments.of("musicServiceTests"),
                Arguments.of(MusicService.class),
                Arguments.of("foo"),
                Arguments.of("bar"),
                Arguments.of(SimpleNormalizer.class),
                Arguments.of(CapLeadingNormalizer.class),
                Arguments.of("musicService4")
        );
    }

    @ParameterizedTest
    @MethodSource("resources")
    public void validateResourceExistence(Object resource) {
        if (resource instanceof String) {
            assertNotNull(context.getBean(resource.toString()));
        } else {
            if (resource instanceof Class<?>) {
                assertNotNull(context.getBean((Class<?>) resource));
            } else {
                fail("Invalid resource type");
            }
        }
    }
}
