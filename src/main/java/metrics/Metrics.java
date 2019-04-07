package metrics;

import gnu.trove.list.array.TDoubleArrayList;

import java.util.Collection;

public interface Metrics {

    /* W tym przypadku a i b są punktami w przestrzeni (kolekcja zawiera wartości cech - n wymiarów)
    Double jest wartością jednowymiarową opisującą jedną cechę dla danego punktu
    Liczona jest odgległość w przestrzeni pomiędzy dwoma punktami (brane pod uwagę so wszystkie wymiary)
    W implementacji sprawdzane jest czy punkty mają taką samą ilość wymiarów (punkt musi mieć min. 1 wymiar) */
    Double calculate(TDoubleArrayList a, TDoubleArrayList b) throws Exception;

    default Boolean validatePoints(TDoubleArrayList a, TDoubleArrayList b) throws Exception {
        if (a.isEmpty() || b.isEmpty()) {
            throw new Exception("The point is dimensionless.");
        }
        if (a.size() != b.size()) {
            throw new Exception("Points are not specified in the same dimension.");
        }
        return true;
    }

    MetricsType getMetricsType();

}
