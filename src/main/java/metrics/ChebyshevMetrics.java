package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ChebyshevMetrics implements Metrics {

    // ArrayList<Object> = Point in space
    // Object = one-dimensional value for a given point
    @Override
    public Double calculate(ArrayList<Double> a, ArrayList<Double> b) throws Exception {
        validatePoints(a, b);
        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double difference, dimA, dimB;
        ArrayList<Double> distances = new ArrayList<>();

        while (aIterator.hasNext()) {
            dimA = (Double) aIterator.next();
            dimB = (Double) bIterator.next();
            difference = dimA - dimB;
            distances.add(Math.abs(difference));
        }

        return Collections.max(distances);
    }
}
