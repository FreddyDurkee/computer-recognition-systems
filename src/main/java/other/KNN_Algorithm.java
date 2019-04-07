package other;

import article.FeaturedArticle;
import com.google.common.primitives.Doubles;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;
import history.ClassificationHistory;
import history.ClassifiedSample;
import jdk.internal.org.objectweb.asm.tree.InnerClassNode;
import lombok.Data;
import metrics.Metrics;

import java.util.*;

@Data
public class KNN_Algorithm {

    // treningData - dane treningowe, na podstawie których klasyfikujemy artykuł (wektor opisujący obiekt)
    // Na przykład:     Collection [ List [ Double, Double, Double ] ]
    //                  Kolekcja pełna list przechowujących cechy
    //                  W tym przypadku mamy 3 cechy w każdej liście
    // [
    //   [ [5.0] [6.0] [2.0] ],
    //   [ [6.3] [2.5] [2.5] ],
    //   [ ... ],
    // ]
    // Dane treningowe definiujemy na samym początku
    private ArrayList<FeaturedArticle> treningData;

    private ClassificationHistory classificationHistory;

    public KNN_Algorithm(ArrayList<FeaturedArticle> treningData) {
        this.treningData = treningData;
        this.classificationHistory = new ClassificationHistory();
    }

    public KNN_Algorithm(ArrayList<FeaturedArticle> treningData, ClassificationHistory classificationHistory) {
        this.treningData = treningData;
        this.classificationHistory = classificationHistory;
    }

    public KNN_Algorithm() {
        this.treningData = new ArrayList<>();
        this.classificationHistory = new ClassificationHistory();
    }

    // sample - artykuł, który chcemy zaklasyfikować
    // k - ilość sąsiadujących artykułów, które chcemy wziąć pod uwagę
    // metrics - używana metryka (Euklidesowa, Manhattan, Czebyszewa)
    public String KNN(FeaturedArticle sample, int k, Metrics metrics) throws Exception {

        TDoubleArrayList distances = new TDoubleArrayList();
        // Liczymy dystans pomiędzy naszą próbką, a wszystkimi danymi treningowymi
        for (FeaturedArticle data : treningData) {
            distances.add(metrics.calculate(
                    data.getFeatureVector(),
                    sample.getFeatureVector()
            ));
        }

        // Wyszukujemy etykiety dla k najbliższych artykułów (etykiety dublują się)
        ArrayList<String> foundLabels = new ArrayList<>();
        for (int i : getLowestIndexes(distances, k)) {
            foundLabels.addAll(treningData.get(i).getLabel());
        }

        String winnerLabel = calculateMedianLabel(foundLabels);

        sample.getPredictedLabel().add(winnerLabel);
        classificationHistory.add(new ClassifiedSample(sample, k, metrics.getMetricsType()));

        return winnerLabel;
    }

    // Wyliczamy ilość wystąpień dla etykiet i wybieramy tą najczęściej występującą
    // Jeśli jest więcej niż jedna wygrywająca losujemy jedną ze zwycięzkich
    public String calculateMedianLabel(ArrayList<String> labels) {
        HashMap<String, Integer> labelCounter = new HashMap<>();
        for (String label : labels) {
            if (labelCounter.containsKey(label)) {
                // Jeśli zawiera etykietę to włoż etykietę z wartością +1 (nadpisana zostanie ilość wystąpień)
                labelCounter.put(label, labelCounter.get(label) + 1);
            } else {
                labelCounter.put(label, 1);
            }
        }

        // Szukaj najczęściej występujących i wrzuć je do nowej listy
        ArrayList<String> winners = getCommonestValues(labelCounter);

        return randLabel(winners);
    }

    public ArrayList<String> getCommonestValues(HashMap<String, Integer> labelCounter) {
        // Licz ilość najczęściej występującej labelki
        int maxValue = Collections.max(
                labelCounter.entrySet(),
                (o1, o2) -> o1.getValue() > o2.getValue() ? 1 : -1).getValue();

        ArrayList<String> winners = new ArrayList<>();

        // Wrzucaj do winners labelki, ktore wystepuja maxValue razy.
        for (Map.Entry<String, Integer> entry : labelCounter.entrySet()) {
            if (entry.getValue().equals(maxValue)) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }

    // Zwraca k najmniejszych wartości z kolekcji (nie patrzeć na tą metodę, bo aż wstyd i w oczy kłuje)
    // PS. ważne że działa
    public ArrayList<Integer> getLowestIndexes(TDoubleArrayList collection, int k) throws Exception {
        if (collection.size() < k) {
            throw new Exception("Invalid collection size.");
        }

        TDoubleIterator iterator = collection.iterator();
        ArrayList<Double> copy = new ArrayList<>();
        ArrayList<Double> copy2 = new ArrayList<>();

        Double temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            copy.add(temp);
            copy2.add(temp);
        }

        Collections.sort(copy); // Od najmniejszej do największej
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; result.size() != k; i++) {
            // Bierzemy pod uwagę pierwsze wystapienie indexu
            result.add(copy2.indexOf(copy.get(i)));
            copy2.set(copy2.indexOf(copy.get(i)), null);
        }
        return result;
    }

    // Losuje losową labelkę z arrayList
    public String randLabel(ArrayList<String> labels) {
        Random r = new Random();
        int randomValue = r.nextInt(labels.size());
        return labels.get(randomValue);
    }

}
