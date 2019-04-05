package metrics;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EuclideanMetricsTest {



    @Test
    void calculate() throws Exception {
        // Given
        ArrayList<Object> pointA = new ArrayList<>();
        pointA.add(5.0);
        pointA.add(7.0);

        ArrayList<Object> pointB = new ArrayList<>();
        pointB.add(2.0);
        pointB.add(2.0);

        Metrics metrics = new EuclideanMetrics();

        // When
        Double result = metrics.calculate(pointA, pointB);

        // Then
        assertEquals(Math.sqrt(34.0), result);
    }
}