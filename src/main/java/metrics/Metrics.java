package metrics;

import java.util.ArrayList;

public interface Metrics {

    // In this case, the object is Double, which specifies the value in a given dimension for a given point.
    Double calculate(ArrayList<Object> a, ArrayList<Object> b) throws Exception;

    default Boolean validatePoints(ArrayList<Object> a, ArrayList<Object> b) throws Exception {
        if (a.isEmpty() || b.isEmpty()) {
            throw new Exception("The point is dimensionless.");
        }
        if (a.size() != b.size()) {
            throw new Exception("Points are not specified in the same dimension.");
        }
        return true;
    }

}
