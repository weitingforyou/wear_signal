import numpy as np

accuracy = 0

for i in range (1, 92):
    # load data
    datadir = '/media/sf_Signal/weiting/'
    dataset = 'Signal_' + str(i) + '.txt'
    DataMat1 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+1) + '.txt'
    DataMat2 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+2) + '.txt'
    DataMat3 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+3) + '.txt'
    DataMat4 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+4) + '.txt'
    DataMat5 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+5) + '.txt'
    DataMat6 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+6) + '.txt'
    DataMat7 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+7) + '.txt'
    DataMat8 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+8) + '.txt'
    DataMat9 = np.loadtxt(datadir+dataset, delimiter=' ')
    dataset = 'Signal_' + str(i+9) + '.txt'
    DataMat10 = np.loadtxt(datadir+dataset, delimiter=' ')

    # zero mean
    def zeroMean(dataMat):
        meanVal = np.mean(dataMat, axis=0)
        newData = dataMat - meanVal
        return newData, meanVal

    # pca
    def pca(dataMat, n):
        newData, meanVal = zeroMean(dataMat)
        covMat = np.cov(newData, rowvar=0)

        eigVals, eigVects = np.linalg.eig(np.mat(covMat))
        eigValIndice = np.argsort(eigVals)
        n_eigValIndice = eigValIndice[-1:-(n+1):-1]
        n_eigVect = eigVects[:, n_eigValIndice]
        lowDDataMat = newData*n_eigVect
        reconMat = (lowDDataMat * n_eigVect.T) + meanVal
        return lowDDataMat, reconMat

    # dtw
    def pca_dtw(dataMat1, dataMat2):
        dtw = np.zeros((len(dataMat1), len(dataMat2)), dtype=float)
        for j in xrange (0, len(dataMat1)):
            dtw[j,0] = 2000.0
        for j in xrange (0, len(dataMat2)):
            dtw[0,j] = 2000.0
        dtw[0,0] = 0
        for k in xrange (0, len(dataMat1)):
            for j in xrange (0, len(dataMat2)):
                cost = abs(dataMat1[k,0]-dataMat2[j,0]) #+ abs(dataMat1[i,1]-dataMat2[j,1])
                dtw[k,j] = cost + min(dtw[k-1,j-1], min(dtw[k-1,j], dtw[k,j-1]))
        return dtw[len(dataMat1)-1,len(dataMat2)-1]

    num = 1
    lowDDataMat1, reconMat1 = pca(DataMat1, num)
    lowDDataMat2, reconMat2 = pca(DataMat2, num)
    lowDDataMat3, reconMat3 = pca(DataMat3, num)
    lowDDataMat4, reconMat4 = pca(DataMat4, num)
    lowDDataMat5, reconMat5 = pca(DataMat5, num)
    lowDDataMat6, reconMat6 = pca(DataMat6, num)
    lowDDataMat7, reconMat7 = pca(DataMat7, num)
    lowDDataMat8, reconMat8 = pca(DataMat8, num)
    lowDDataMat9, reconMat9 = pca(DataMat9, num)
    lowDDataMat10, reconMat10 = pca(DataMat10, num)

    def pca_correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat6):
        ct = 0
        sum1 = (pca_dtw(DataMat1, DataMat2) + pca_dtw(DataMat1, DataMat3) + pca_dtw(DataMat1, DataMat4) + pca_dtw(DataMat1, DataMat5) + pca_dtw(DataMat2, DataMat3) + pca_dtw(DataMat2, DataMat4) + pca_dtw(DataMat2, DataMat5) + pca_dtw(DataMat3, DataMat4) + pca_dtw(DataMat3, DataMat5) + pca_dtw(DataMat4, DataMat5)) / 10 + (len(DataMat1) + len(DataMat2) + len(DataMat3) + len(DataMat4) + len(DataMat5)) / 5 * 1.75
        sum2 = sum1 - 2*(len(DataMat1) + len(DataMat2) + len(DataMat3) + len(DataMat4) + len(DataMat5)) / 5 * 1.75
		
        if pca_dtw(DataMat1, DataMat6)<sum1 and pca_dtw(DataMat1, DataMat6)>sum2:
            ct = ct + 1
        if pca_dtw(DataMat2, DataMat6)<sum1 and pca_dtw(DataMat2, DataMat6)>sum2:
            ct = ct + 1
        if pca_dtw(DataMat3, DataMat6)<sum1 and pca_dtw(DataMat3, DataMat6)>sum2:
            ct = ct + 1
        if pca_dtw(DataMat4, DataMat6)<sum1 and pca_dtw(DataMat4, DataMat6)>sum2:
            ct = ct + 1
        if pca_dtw(DataMat5, DataMat6)<sum1 and pca_dtw(DataMat5, DataMat6)>sum2:
            ct = ct + 1
        if ct==5:
            return 1
        else:
            return 0

    accuracy = accuracy + pca_correct(lowDDataMat1, lowDDataMat2, lowDDataMat3, lowDDataMat4, lowDDataMat5, lowDDataMat6)
    accuracy = accuracy + pca_correct(lowDDataMat1, lowDDataMat2, lowDDataMat3, lowDDataMat4, lowDDataMat5, lowDDataMat7)
    accuracy = accuracy + pca_correct(lowDDataMat1, lowDDataMat2, lowDDataMat3, lowDDataMat4, lowDDataMat5, lowDDataMat8)
    accuracy = accuracy + pca_correct(lowDDataMat1, lowDDataMat2, lowDDataMat3, lowDDataMat4, lowDDataMat5, lowDDataMat9)
    accuracy = accuracy + pca_correct(lowDDataMat1, lowDDataMat2, lowDDataMat3, lowDDataMat4, lowDDataMat5, lowDDataMat10)
    
    print "episode: ", i, "accuracy: ", accuracy
accuracy = accuracy / (90*5) * 100
print accuracy, "%"
