package metrics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanMetricsTest {

    @Test
    void calculate() throws Exception {
        // Given
        ArrayList<Double> pointA = new ArrayList<>();
        pointA.add(5.0);
        pointA.add(7.0);

        ArrayList<Double> pointB = new ArrayList<>();
        pointB.add(2.0);
        pointB.add(2.0);

        Metrics metrics = new ManhattanMetrics();

        // When
        Double result = metrics.calculate(pointA, pointB);

        // Then
        assertEquals(Math.sqrt(8.0), result);
    }
}