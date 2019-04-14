package metrics;

public class MetricsFactory {

    public static Metrics createFrom(String name){
        switch (name){
            case "e": return new EuclideanMetrics();
            case "c": return new ChebyshevMetrics();
            case "m": return new ManhattanMetrics();
            default:
                System.out.println("Metric not found. Switching to default - Euclidean.");
                return  new EuclideanMetrics();
        }
    }

}
