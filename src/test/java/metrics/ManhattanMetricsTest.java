package metrics;

import gnu.trove.list.array.TDoubleArrayList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanMetricsTest {

    @Test
    void calculate() throws Exception {
        // Given
        TDoubleArrayList pointA = new TDoubleArrayList();
        pointA.add(5.0);
        pointA.add(7.0);

        TDoubleArrayList pointB = new TDoubleArrayList();
        pointB.add(2.0);
        pointB.add(2.0);

        Metrics metrics = new ManhattanMetrics();

        // When
        Double result = metrics.calculate(pointA, pointB);

        // Then
        assertEquals(Math.sqrt(8.0), result);
    }
}