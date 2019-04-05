package metrics;

import java.util.ArrayList;
import java.util.Iterator;

public class EuclideanMetrics implements Metrics {

    // ArrayList<Object> = Point in space
    // Object = one-dimensional value for a given point
    @Override
    public Double calculate(ArrayList<Double> a, ArrayList<Double> b) throws Exception {
        validatePoints(a,b);
        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double sum = 0.0, difference, dimA, dimB;

        while (aIterator.hasNext()) {
            dimA = (Double) aIterator.next();
            dimB = (Double) bIterator.next();
            difference = dimA - dimB;
            sum += Math.pow(difference.doubleValue(), 2.0);
        }

        return Math.sqrt(sum);
    }

}
