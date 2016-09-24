# wear_signal
4th grade project : Signal detection for android wear

## wear_signal.java
app name : wear_signal <br>
完成將signal的三軸寫進txt檔，並存在手錶內<br>

## save_signal.java
app name : save_signal <br>
專門存signal data的app，將signal data存在手表的/storage/emulated/0/Signal/資料夾中 <br>
以Signal_ct.txt的格式為檔名 <br>

## save_signal.md
save signal 的步驟 <br>
move files from wear to computer with adb command line and rename the file with Ant Renamer <br>
adb command line：http://adbshell.com/commands

## pca_dtw.py & non-pca_dtw.py
data：用smartwatch寫weiting for 100 times <br>
pca_dtw.py：將data降維成一維，並做dtw <br>
non-pca_dtw.py：data沒有做任何處理 <br>

| correct function 係數  | data 處理  | 準確率 |
| :------------ |:---------------:| -----:|
| *1                    | 降成一維  | 72.22% |
| *1.25                 | 降成一維  | 90.22% |
| *1.5                  | 降成一維  | 96.89% |
| *1.75                 | 降成一維  | 98.22% |
| *1                    | 不做處理  | 30.44% |
| *1.25                 | 不做處理  | 46.67% |
| *1.5                  | 不做處理  | 60.89% |
| *1.75                 | 不做處理  | 71.78% |
