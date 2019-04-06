package metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ChebyshevMetrics implements Metrics {

    // Największa wartość bezwzględna różnicy wszystkich odpowiadających sobie wymiarów
    @Override
    public Double calculate(Collection<Double> a, Collection<Double> b) throws Exception {
        validatePoints(a, b);
        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double difference, dimA, dimB;
        Collection<Double> distances = new ArrayList<>();

        while (aIterator.hasNext()) {
            dimA = (Double) aIterator.next();
            dimB = (Double) bIterator.next();
            difference = dimA - dimB;
            distances.add(Math.abs(difference));
        }

        return Collections.max(distances);
    }
}
