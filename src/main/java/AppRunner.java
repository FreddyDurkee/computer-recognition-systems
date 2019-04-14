import article.Article;
import article.ArticleManager;
import article.FeaturedArticle;
import file_extractor.Category;
import file_extractor.ReutersExtractor;
import metrics.Metrics;
import metrics.MetricsFactory;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import other.KNN_Algorithm;
import other.TF_IDFExtractor;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AppRunner {

    public static final Logger LOGGER = LogManager.getLogger(AppRunner.class);
    private static final List<String> LABELS_TO_CLASSIFICATION = Arrays.asList(
            "west-germany", "usa", "france", "uk", "canada", "japan");
    private static final String REUTERS_SELECTOR = "classpath:reuters21578/*.sgm";

    private Options cmdOptions;
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();


    private AppRunner() {
        this.cmdOptions = new Options();
        Option kOpt = new Option("k", true, "KNN k parameter");
        kOpt.setRequired(true);
        cmdOptions.addOption(kOpt);

        Option mOpt = new Option("m", true, "KNN metric");
        mOpt.setRequired(true);
        cmdOptions.addOption(mOpt);

        Option outOpt = new Option("o", true, "Output path");
        outOpt.setRequired(true);
        cmdOptions.addOption(outOpt);

        Option percOpt = new Option("p", true, "Data train/test split percentage");
        percOpt.setRequired(false);
        cmdOptions.addOption(percOpt);
    }

    private CommandLine parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(cmdOptions, args);
        } catch (Exception e) {
            help();
            throw new RuntimeException("Error on parsing args.");
        }
    }

    private void help() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Main", cmdOptions);
    }

    public void run(String[] args) throws Exception {
        CommandLine cmd = parse(args);

        int k = Integer.parseInt(cmd.getOptionValue("k"));
        Metrics metrics = MetricsFactory.createFrom(cmd.getOptionValue("m"));
        String outputPath = cmd.getOptionValue("o");
        int splitPerc = Integer.parseInt(cmd.getOptionValue("p", "70"));
        LOGGER.info(MessageFormat.format("k={},m={},o={},splitPerc={}", k, metrics.getMetricsType(), outputPath, splitPerc));


        LOGGER.info("Starting article extraction.");
        ReutersExtractor reutersExtractor = new ReutersExtractor(resourcePatternResolver.getResources(REUTERS_SELECTOR));
        ArticleManager articleManager = new ArticleManager();

        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i == 1)
                .addLabelNameFilter(LABELS_TO_CLASSIFICATION)
                .extractAllFiles(articleManager);

        Map<String, Long> countedLabels = articleManager.numberOfLabels();
        LOGGER.debug("Counted labels = " + countedLabels);

        Pair<Set<Article>, Set<Article>> splitedArticles = articleManager.splitDataInProportion(splitPerc);

        Set<Article> trainSet = splitedArticles.getValue0();
        Set<Article> testSet = splitedArticles.getValue1();

        TF_IDFExtractor trainFeaturesExtractor = new TF_IDFExtractor(trainSet);
        TF_IDFExtractor testFeaturesExtractor = new TF_IDFExtractor(testSet, trainFeaturesExtractor.getDictionary());

        LOGGER.info("Starting features extraction.");
        List<FeaturedArticle> trainFeatures = trainFeaturesExtractor.extract();
        List<FeaturedArticle> testFeatures = testFeaturesExtractor.extract();

        LOGGER.info("Starting classification.");
        KNN_Algorithm knn = new KNN_Algorithm(trainFeatures);
        int articleCounter = 0;
        int testFeaturesSize = testFeatures.size();
        for (FeaturedArticle testFeature : testFeatures) {
            knn.KNN(testFeature, k, metrics);
            articleCounter++;
            if (articleCounter % 10 == 0) {
                LOGGER.info("Classification status: " + articleCounter + "/" + testFeaturesSize);
            }
        }

        knn.getClassificationHistory().saveToFileOnlyLabels(outputPath);
    }

    public static void main(String[] args) throws Exception {
        new AppRunner().run(args);
    }
}


