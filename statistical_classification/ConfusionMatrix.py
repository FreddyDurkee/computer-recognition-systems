import sys
import os
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import precision_score
from sklearn.metrics import confusion_matrix
from sklearn.utils.multiclass import unique_labels

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

    print(cm)

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


def main():

    pathToFileWithClassificationData = os.path.abspath(sys.argv[1])

    trueLabels, predictedLabels = extractClassificationCases(pathToFileWithClassificationData)

    trueLabels = [x.strip() for x in trueLabels]
    predictedLabels = [x.strip() for x in predictedLabels]

    plot_confusion_matrix(trueLabels, predictedLabels, classes=unique_labels(trueLabels,predictedLabels), normalize=True,
                          title='Normalized confusion matrix')

    cnf_matrix = confusion_matrix(trueLabels, predictedLabels)

    FP = cnf_matrix.sum(axis=0) - np.diag(cnf_matrix)
    FN = cnf_matrix.sum(axis=1) - np.diag(cnf_matrix)
    TP = np.diag(cnf_matrix)
    TN = cnf_matrix.sum() - (FP + FN + TP)

    FP = FP.astype(float)
    FN = FN.astype(float)
    TP = TP.astype(float)
    TN = TN.astype(float)

    # Sensitivity, hit rate, recall, or true positive rate
    TPR = TP / (TP + FN)
    # Specificity or true negative rate
    TNR = TN / (TN + FP)
    # Precision or positive predictive value PPV = TP / (TP + FP)
    PPV = precision_score(trueLabels, predictedLabels, average='micro')
    # Negative predictive value
    NPV = TN / (TN + FN)
    # Fall out or false positive rate
    FPR = FP / (FP + TN)
    # False negative rate
    FNR = FN / (TP + FN)
    # False discovery rate
    FDR = FP / (TP + FP)
    # Overall accuracy
    ACC = (TP + TN) / (TP + FP + FN + TN)

    print "FP = ",FP
    print "FN = ", FN
    print "TP = ", TP
    print "TN = ", TN
    print "TPR = ", TPR
    print "TNR = ", TNR
    print "PPV = ", PPV
    print "NPV = ", NPV
    print "FPR = ", FPR
    print "FNR = ", FNR
    print "FDR = ", FDR
    print "ACC = ", ACC

    plt.show()


if __name__ == "__main__":
    main()