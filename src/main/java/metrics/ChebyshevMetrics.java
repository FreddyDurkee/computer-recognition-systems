package metrics;

import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ChebyshevMetrics implements Metrics {

    // Największa wartość bezwzględna różnicy wszystkich odpowiadających sobie wymiarów
    @Override
    public Double calculate(TDoubleArrayList a, TDoubleArrayList b) throws Exception {
        validatePoints(a, b);
        TDoubleIterator aIterator = a.iterator();
        TDoubleIterator bIterator = b.iterator();

        Double difference, dimA, dimB;
        Collection<Double> distances = new ArrayList<>();

        while (aIterator.hasNext()) {
            dimA =  aIterator.next();
            dimB =  bIterator.next();
            difference = dimA - dimB;
            distances.add(Math.abs(difference));
        }

        return Collections.max(distances);
    }

    @Override
    public MetricsType getMetricsType() {
        return MetricsType.CHEBYSHEV;
    }
}
