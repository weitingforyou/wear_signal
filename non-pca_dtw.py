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

    def dtw(dataMat1, dataMat2):
        dtw = np.zeros((len(dataMat1), len(dataMat2)), dtype=float)
        for i in xrange (0, len(dataMat1)):
            dtw[i,0] = 2000.0
        for i in xrange (0, len(dataMat2)):
            dtw[0,i] = 2000.0
        dtw[0,0] = 0
        for i in xrange (0, len(dataMat1)):
            for j in xrange (0, len(dataMat2)):
                cost = abs(dataMat1[i,0]-dataMat2[j,0]) + abs(dataMat1[i,1]-dataMat2[j,1]) + abs(dataMat1[i,1]-dataMat2[j,2])
                dtw[i,j] = cost + min(dtw[i-1,j-1], min(dtw[i-1,j], dtw[i,j-1]))
        return dtw[len(dataMat1)-1,len(dataMat2)-1]

    def correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat6):
        ct = 0
        sum1 = (dtw(DataMat1, DataMat2) + dtw(DataMat1, DataMat3) + dtw(DataMat1, DataMat4) + dtw(DataMat1, DataMat5) + dtw(DataMat2, DataMat3) + dtw(DataMat2, DataMat4) + dtw(DataMat2, DataMat5) + dtw(DataMat3, DataMat4) + dtw(DataMat3, DataMat5) + dtw(DataMat4, DataMat5)) / 10 + (len(DataMat1) + len(DataMat2) + len(DataMat3) + len(DataMat4) + len(DataMat5)) / 5 * 1.75 
        sum2 = sum1 - 2*(len(DataMat1) + len(DataMat2) + len(DataMat3) + len(DataMat4) + len(DataMat5)) / 5 * 1.75
    
        if dtw(DataMat1, DataMat6)<sum1 and dtw(DataMat1, DataMat6)>sum2:
            ct = ct + 1
        if dtw(DataMat2, DataMat6)<sum1 and dtw(DataMat2, DataMat6)>sum2:
            ct = ct + 1
        if dtw(DataMat3, DataMat6)<sum1 and dtw(DataMat3, DataMat6)>sum2:
            ct = ct + 1
        if dtw(DataMat4, DataMat6)<sum1 and dtw(DataMat4, DataMat6)>sum2:
            ct = ct + 1
        if dtw(DataMat5, DataMat6)<sum1 and dtw(DataMat5, DataMat6)>sum2:
            ct = ct + 1
        if ct==5:
            return 1
        else:
            return 0

    accuracy = accuracy + correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat6)
    accuracy = accuracy + correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat7)
    accuracy = accuracy + correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat8)
    accuracy = accuracy + correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat9)
    accuracy = accuracy + correct(DataMat1, DataMat2, DataMat3, DataMat4, DataMat5, DataMat10)
    
    print "episode: ", i, "accuracy: ", accuracy



