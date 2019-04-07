package metrics;

import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;

import java.util.Collection;
import java.util.Iterator;

public class EuclideanMetrics implements Metrics {

    // Suma kwadratów różnic wszystkich odpowiadających sobie wymiarów
    @Override
    public Double calculate(TDoubleArrayList a, TDoubleArrayList b) throws Exception {
        validatePoints(a, b);
        TDoubleIterator aIterator = a.iterator();
        TDoubleIterator bIterator = b.iterator();

        Double sum = 0.0, difference, dimA, dimB;

        while (aIterator.hasNext()) {
            dimA = aIterator.next();
            dimB = bIterator.next();
            difference = dimA - dimB;
            sum += Math.pow(difference.doubleValue(), 2.0);
        }

        return Math.sqrt(sum);
    }

    @Override
    public MetricsType getMetricsType() {
        return MetricsType.EUCLIDEAN;
    }

}
