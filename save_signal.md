### wear signal
1. 用手錶簽名(按start開始，save結束) <br>

### move file
1. open cmd(administrator) <br>
2. cd C:\Users\Carine\AppData\Local\Android\sdk  (see Android Studio → Project Structure → Android SDK location) <br>
3. adb pull /storage/emulated/legacy/Signal C:\Users\Carine\Documents

### rename
1. open AntRenamer
2. 新增資料夾 → 動作 → 執行

### delete file and new a folder in wear
1. adb shell
2. cd storage/emulated/legacy
3. rm -r Signal/
4. mkdir -p Signal
