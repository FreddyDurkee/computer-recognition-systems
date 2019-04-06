package metrics;

import java.util.Collection;
import java.util.Iterator;

public class ManhattanMetrics implements Metrics {

    // Suma wartości bezwzględnych z różnic wszystkich odpowiadających sobie wymiarów
    @Override
    public Double calculate(Collection<Double> a, Collection<Double> b) throws Exception {
        validatePoints(a, b);
        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double sum = 0.0, difference, dimA, dimB;

        while (aIterator.hasNext()) {
            dimA = (Double) aIterator.next();
            dimB = (Double) bIterator.next();
            difference = dimA - dimB;
            sum += Math.abs(difference);
        }

        return Math.sqrt(sum);
    }

    @Override
    public MetricsType getMetricsType() {
        return MetricsType.MANHATTAN;
    }
}
