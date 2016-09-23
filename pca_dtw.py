import numpy as np

# load data
dataset = 'Signal_1.txt'
datadir = '/media/sf_Signal/DCIM/'
dataMat1 = np.loadtxt(datadir+dataset, delimiter=' ')

dataset = 'Signal_2.txt'
datadir = '/media/sf_Signal/DCIM/'
dataMat2 = np.loadtxt(datadir+dataset, delimiter=' ')

# Zero mean
def zeroMean(dataMat):
    meanVal = np.mean(dataMat, axis=0)
    newData = dataMat - meanVal
    return newData, meanVal
'''
def percentage2n(eigVals,percentage):  
    sortArray=np.sort(eigVals)
    sortArray=sortArray[-1::-1]
    arraySum=sum(sortArray)  
    tmpSum=0  
    num=0  
    for i in sortArray:  
        tmpSum+=i  
        num+=1  
        if tmpSum>=arraySum*percentage:  
            return num  
'''
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

lowDDataMat1, reconMat = pca(dataMat1, 2)
lowDDataMat2, reconMat = pca(dataMat2, 2)
#print len(lowDDataMat1), lowDDataMat2.shape
#print lowDDataMat1[10,0],lowDDataMat1[10,1]

def dtw(dataMat1, dataMat2):
    dtw = np.zeros((len(dataMat1), len(dataMat2)), dtype=float)
    for i in xrange (0, len(dataMat1)):
        dtw[i,0] = 2000.0
    for i in xrange (0, len(dataMat2)):
        dtw[0,i] = 2000.0
    dtw[0,0] = 0
    for i in xrange (0, len(dataMat1)):
        for j in xrange (0, len(dataMat2)):
            cost = abs(dataMat1[i,0]-dataMat2[j,0]) + abs(dataMat1[i,1]-dataMat2[j,1])
            dtw[i,j] = cost + min(dtw[i-1,j-1], min(dtw[i-1,j], dtw[i,j-1]))
    return dtw[len(dataMat1)-1,len(dataMat2)-1]

print dtw(lowDDataMat1, lowDDataMat2)
