import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureSelectorTest {

    @Test
    void termFrequency() {
        // Given
        ArrayList<String> words = new ArrayList<>(Arrays.asList("colombia", "open", "april", "opening", "coffe", "open"));

        // When Then
        assertEquals(1, FeaturesSelector.termFrequency(words, "colombia"));
        assertEquals(2, FeaturesSelector.termFrequency(words, "open"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "april"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "opening"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "coffe"));
        assertEquals(0, FeaturesSelector.termFrequency(words, "cat"));
    }
}
