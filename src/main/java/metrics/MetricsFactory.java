package metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricsFactory {

    public static final Logger LOGGER = LogManager.getLogger(MetricsFactory.class);

    public static Metrics createFrom(String name){
        switch (name){
            case "e": return new EuclideanMetrics();
            case "ch": return new ChebyshevMetrics();
            case "cos": return new CosineSimilarity();
            case "m": return new ManhattanMetrics();
            default:
                LOGGER.debug("Metric not found. Switching to default - Euclidean.");
                return  new EuclideanMetrics();
        }
    }

}
