import article.Article;
import article.DictionarizedArticle;
import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import other.TextFeaturesExtractor;
import other.TextFeaturesExtractorBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TextFeaturesExtractorTest {
    @Test
    void extractKeyWords() {
        // Given
        ArrayList<String> labels1 = new ArrayList<>();
        labels1.add("usa");

        ArrayList<String> labels2 = new ArrayList<>();
        labels2.add("japan");
        labels2.add("usa");

        ArrayList<String> labels3 = new ArrayList<>();
        labels2.add("canada");

        ArrayList<String> labels4 = new ArrayList<>();
        labels2.add("poland");


        Article a1 = new Article(labels1, "", "text is about music");
        Article a2 = new Article(labels2, "", "text is about love");
        Article a3 = new Article(labels3, "", "text is about dreams");
        Article a4 = new Article(labels4, "", "text is about mystery");
        Article a5 = new Article(labels1, "", "text is about happiness");

        Set<Article> articleSet = new HashSet<>();
        articleSet.add(a1);
        articleSet.add(a2);
        articleSet.add(a3);
        articleSet.add(a4);
        articleSet.add(a5);

        TextFeaturesExtractor featuresExtractor = new TextFeaturesExtractorBuilder(articleSet, articleSet)
                .setTfIdfTresholdVal(0.6)
                .buid();

        List<String> keyWords = featuresExtractor.extractKeyWords();

        assertThat(keyWords, IsCollectionWithSize.hasSize(5));
        assertThat(keyWords, containsInAnyOrder("music", "love", "dream", "mysteri", "happi"));
    }


    @Test
    void ifWordsOccursFeature() {
        // Given
        ArrayList<String> label = new ArrayList<>();
        label.add("usa");

        Set<String> listOfWordsInArticle = new HashSet<>();
        listOfWordsInArticle.add("snow");
        listOfWordsInArticle.add("ten");
        listOfWordsInArticle.add("rabbit");
        listOfWordsInArticle.add("dog");

        DictionarizedArticle dictArticle = new DictionarizedArticle(label, listOfWordsInArticle);

        List<String> keyWords = new ArrayList<>();
        keyWords.add("dog");
        keyWords.add("cat");
        keyWords.add("pig");
        keyWords.add("rabbit");


        double[] wordsOccuresVector = TextFeaturesExtractor.ifWordsOccursFeature(dictArticle, keyWords);
        double[] expectedWordsOccuresVector = {1, 0, 0, 1};
        assertArrayEquals(expectedWordsOccuresVector, wordsOccuresVector);
    }


    @Test
    void numberOfKeyWordsFeature() {
        // Given
        ArrayList<String> label = new ArrayList<>();
        label.add("usa");

        Set<String> listOfWordsInArticle = new HashSet<>();
        listOfWordsInArticle.add("snow");
        listOfWordsInArticle.add("ten");
        listOfWordsInArticle.add("rabbit");
        listOfWordsInArticle.add("dog");

        DictionarizedArticle dictArticle = new DictionarizedArticle(label, listOfWordsInArticle);

        List<String> keyWords = new ArrayList<>();
        keyWords.add("dog");
        keyWords.add("cat");
        keyWords.add("pig");
        keyWords.add("rabbit");


        double[] numberOfKeyWords = TextFeaturesExtractor.numberOfKeyWordsFeature(dictArticle, keyWords);
        double expectedNumberOfKeyWords = 2;
        assertEquals(expectedNumberOfKeyWords, numberOfKeyWords[0]);
    }


    @Test
    void frequencyOfKeyWordsFeature() {
        // Given
        ArrayList<String> label = new ArrayList<>();
        label.add("usa");

        Set<String> articleDict = new HashSet<>();
        articleDict.add("snow");
        articleDict.add("ten");
        articleDict.add("rabbit");
        articleDict.add("dog");

        List<String> listOfWordsInArticle = new ArrayList<>();
        listOfWordsInArticle.add("snow");
        listOfWordsInArticle.add("ten");
        listOfWordsInArticle.add("rabbit");
        listOfWordsInArticle.add("dog");
        listOfWordsInArticle.add("rabbit");

        DictionarizedArticle dictArticle = new DictionarizedArticle(label, articleDict, listOfWordsInArticle);

        List<String> keyWords = new ArrayList<>();
        keyWords.add("dog");
        keyWords.add("cat");
        keyWords.add("pig");
        keyWords.add("rabbit");


        double[] wordsOccuresVector = TextFeaturesExtractor.frequencyOfKeyWordsFeature(dictArticle, keyWords);
        double[] expectedWordsOccuresVector = {1, 0, 0, 2};
        assertArrayEquals(expectedWordsOccuresVector, wordsOccuresVector);
    }

    @Test
    void extract() {
        // Given
        ArrayList<String> labels1 = new ArrayList<>();
        labels1.add("usa");

        ArrayList<String> labels2 = new ArrayList<>();
        labels2.add("poland");

        ArrayList<String> labels3 = new ArrayList<>();
        labels2.add("canada");

        Article a1 = new Article(labels1, "", "dog cat pig rabbit");
        Article a2 = new Article(labels2, "", "text about love");
        Article a3 = new Article(labels2, "", "dogs love music about love");

        Set<Article> articleTestSet = new HashSet<>();
        articleTestSet.add(a1);
        articleTestSet.add(a2);

        Set<Article> articleTrainSet = new HashSet<>();
        articleTrainSet.add(a3);

        TextFeaturesExtractor featuresExtractor = new TextFeaturesExtractorBuilder(articleTestSet, articleTrainSet)
                .setTfIdfTresholdVal(0.3)
                .buid();
        List<FeaturedArticle> featuredArticles = featuresExtractor.extractTestSet();

        double[] array1 = {0,1,1,0,0,1,1,4,0,1,1,0,0,1,1};
        TDoubleArrayList expectedFeatureVectorForFirstArticle1 = new TDoubleArrayList(array1);

        double[] array2 = {1,0,0,1,1,0,0,3,1,0,0,1,1,0,0};
        TDoubleArrayList expectedFeatureVectorForFirstArticle2 = new TDoubleArrayList(array2);

        double[] array3 = {1,0,0,1,0,1,0,3,2,0,0,1,0,1,0};
        TDoubleArrayList expectedFeatureVectorForTrainArticle = new TDoubleArrayList(array3);
        List<FeaturedArticle> featuredTrainArticles = featuresExtractor.extractTrainSet();

        assertEquals(expectedFeatureVectorForFirstArticle1, featuredArticles.get(0).getFeatureVector());
        assertEquals(expectedFeatureVectorForFirstArticle2, featuredArticles.get(1).getFeatureVector());
        assertEquals(expectedFeatureVectorForTrainArticle, featuredTrainArticles.get(0).getFeatureVector());

    }


}
