package metrics;

import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;

public class CosineSimilarity implements Metrics {

    @Override
    public Double calculate(TDoubleArrayList a, TDoubleArrayList b) throws Exception {
        return 1-similarity(a,b);
    }

    private Double similarity(TDoubleArrayList a, TDoubleArrayList b){
        TDoubleIterator aIterator = a.iterator();
        TDoubleIterator bIterator = b.iterator();

        Double counter = 0.0, dimA, dimB, denominator = 0.0;

        while (aIterator.hasNext()) {
            dimA = aIterator.next();
            dimB = bIterator.next();
            counter+=dimA*dimB;
            denominator+=Math.abs(dimA)*Math.abs(dimB);
        }

        return counter/denominator;
    }

    @Override
    public MetricsType getMetricsType() {
        return MetricsType.EUCLIDEAN;
    }

}
