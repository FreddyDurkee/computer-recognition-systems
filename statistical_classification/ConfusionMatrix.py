import sys
import os
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import precision_score, accuracy_score, recall_score
from sklearn.metrics import confusion_matrix
from sklearn.utils.multiclass import unique_labels
import getopt, sys

def extractLabelsFromFile(path):
    labels = []
    file = open(path, "r")
    for line in file.readlines():
        labels.append(line)
        print line
    return labels

def extractClassificationCases(pathToClassificationData):
    trueLabels = []
    predictedLabels = []
    file = open(pathToClassificationData, "r")
    for line in file.readlines():
        splitedLine = line.split(";")
        trueLabelsCase = splitedLine[0].split(",")
        predictedLabelsCase = splitedLine[1].split(",")
        for trueLab, predictedLab in zip(trueLabelsCase, predictedLabelsCase):
            trueLabels.append(trueLab)
            predictedLabels.append(predictedLab)
    return trueLabels, predictedLabels

def plot_confusion_matrix(y_true, y_pred, classes,
                          normalize=False,
                          title=None,
                          cmap=plt.cm.Blues):
    """
    This function prints and plots the confusion matrix.
    Normalization can be applied by setting `normalize=True`.
    """
    if not title:
        if normalize:
            title = 'Normalized confusion matrix'
        else:
            title = 'Confusion matrix, without normalization'

    # Compute confusion matrix
    cm = confusion_matrix(y_true, y_pred)
    # Only use the labels that appear in the data

    if normalize:
        cm = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
        print("Normalized confusion matrix")
    else:
        print('Confusion matrix, without normalization')

    # print(cm)

    fig, ax = plt.subplots()
    im = ax.imshow(cm, interpolation='nearest', cmap=cmap)
    ax.figure.colorbar(im, ax=ax)
    # We want to show all ticks...
    ax.set(xticks=np.arange(cm.shape[1]),
           yticks=np.arange(cm.shape[0]),
           # ... and label them with the respective list entries
           xticklabels=classes, yticklabels=classes,
           title=title,
           ylabel='True label',
           xlabel='Predicted label')

    # Rotate the tick labels and set their alignment.
    plt.setp(ax.get_xticklabels(), rotation=45, ha="right",
             rotation_mode="anchor")

    # Loop over data dimensions and create text annotations.
    fmt = '.2f' if normalize else 'd'
    thresh = cm.max() / 2.
    for i in range(cm.shape[0]):
        for j in range(cm.shape[1]):
            ax.text(j, i, format(cm[i, j], fmt),
                    ha="center", va="center",
                    color="white" if cm[i, j] > thresh else "black")
    fig.tight_layout()
    return cm

def printClassificationQuality(trueLabels, predictedLabels):
    # Overall accuracy
    ACC = accuracy_score(trueLabels, predictedLabels)
    PRECISION = precision_score(trueLabels, predictedLabels, average='micro')
    RECALL = recall_score(trueLabels, predictedLabels, average='micro')

    print "ACC = ", ACC
    print "PRECISION = ", PRECISION
    # czulosc
    print "RECALL = ", RECALL


def main():
    try:
        opts, args = getopt.getopt(sys.argv[1:], "f:d:", ["filePath=", "dir="])
    except getopt.GetoptError:
        sys.exit(2)
    for opt, arg in opts:
        if opt in ("-f", "--file"):
            filePath = arg
            pathToFileWithClassificationData = os.path.abspath(filePath)

            trueLabels, predictedLabels = extractClassificationCases(pathToFileWithClassificationData)

            trueLabels = [x.strip() for x in trueLabels]
            predictedLabels = [x.strip() for x in predictedLabels]

            plot_confusion_matrix(trueLabels, predictedLabels, classes=unique_labels(trueLabels, predictedLabels),
                                  normalize=True,
                                  title='Normalized confusion matrix')
            printClassificationQuality(trueLabels, predictedLabels)
            plt.show()


        elif opt in ("-d", "--dir"):
            dir = arg

            for r, d, f in os.walk(dir):
                for file in f:
                    if '.txt' in file:
                        if 'config' in file:
                            continue
                        pathToFileWithClassificationData = os.path.abspath(dir)

                        trueLabels, predictedLabels = extractClassificationCases(pathToFileWithClassificationData+"\\"+file)

                        trueLabels = [x.strip() for x in trueLabels]
                        predictedLabels = [x.strip() for x in predictedLabels]
                        #
                        # plot_confusion_matrix(trueLabels, predictedLabels, classes=unique_labels(trueLabels,predictedLabels), normalize=True,
                        #                       title='Normalized confusion matrix')
                        print(file)
                        printClassificationQuality(trueLabels, predictedLabels)



if __name__ == "__main__":
    main()