package com.bsg5.chapter3;

import com.bsg5.chapter3.mem03.CapLeadingNormalizer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

public class TestCapLeadingNormalizer {
    Normalizer normalizer=new CapLeadingNormalizer();

    static Stream<Arguments> stringProvider() {
        return Stream.of(
                Arguments.of("This Is A Test","this is a test"),
                Arguments.of("This Is A Test"," This IS a test "),
                Arguments.of("This Is A Test", "this is a test")
        );
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    public void testNormalization(String expected, String input) {
        assertEquals(expected, normalizer.transform(input));
    }
}
