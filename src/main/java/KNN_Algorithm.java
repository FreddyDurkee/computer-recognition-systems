import metrics.Metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class KNN_Algorithm {

    public void KNN(Collection<Object> data, int k, Metrics metrics) throws Exception {

        validateCollection(data);
        Iterator iterator = data.iterator();

        ArrayList<Object> classesPoints = generatePoints(getPointDimension((ArrayList<Object>) iterator.next()),k);

        // In this case, point is ArrayList<Object>
        for(Object point : data){
//            calculate
        }

    }

    public Boolean validateCollection(Collection<Object> collection) throws Exception {
        if(collection.size()==0){
            throw new Exception("The collection is empty.");
        }
        return true;
    }

    public int getPointDimension(ArrayList<Object> point) throws Exception {
        return point.size();
    }

    Double randDouble(double rangeMin, double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

    ArrayList<Object> generatePoint(int dimension) {
        ArrayList<Object> point = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            point.add(randDouble(-10, 10));
        }
        return point;
    }

    ArrayList<Object> generatePoints(int dimension, int quantity) {
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            list.add(generatePoint(dimension));
        }
        return list;
    }

}
