BT-200ディスプレイ＆オーディオ制御ライブラリパッケージ
                                             SEIKO EPSON CORPORATION Feb/07/2014

■機能概要
本ライブラリは、AndroidアプリケーションよりBT-200のディスプレイとオーディオとセンサーを
制御する機能を提供します
- ディスプレイの2D/3D切替＆トースト表示/非表示
- ディスプレイ輝度の設定
- ディスプレイ輝度値の取得
- ディスプレイミュートのON/OFF切替
- オーディオミュートのON/OFF切替
- センサー切替（コントローラー/ヘッドセット）

■注意事項
・ディスプレイミュート機能はディスプレイOFFではなくバックライト輝度を最小にします。
・センサー切替機能は、センサー未使用時に行ってください。
　センサー使用時にセンサー切替を行った場合は、意図しない動作をする可能性があります。
・センサー値は、通常のAndroidのセンサーAPIで取得できます。
　ただし、コントローラー/ヘッドセットのセンサーを同時に利用することはできません。

■ライブラリ利用環境
機材：BT-200
OS  ：LinuxKernel 3.0.21、Android 4.0.4(API Lv:15以上)


■内容
- BT200CtrlDemo.zip   本ライブラリを使用したサンプルアプリケーション
- libBT200Ctrl.zip  　ライブラリ本体
- ReadMe.txt      　  本ファイル


■ライブラリ利用方法
  下記利用方法は、eclipseでのアプリケーション開発を前提としています
1.eclipseでAndroidProjectを作成します
2.AndroidProjectのルートフォルダ直下に"libs"フォルダを作成します
3.libBt2Ctrl.zipを解凍し、"libs"フォルダにライブラリをコピーします
 -libs\Bt2Ctrl.jar
4.アプリケーションソースコード内で、ライブラリパッケージをimportします
  パッケージ名：jp.epson.moverio.bt200.DisplayControl
  パッケージ名：jp.epson.moverio.bt200.AudioControl
  パッケージ名：jp.epson.moverio.bt200.SensorControl
5.AndroidManifest.xmlでパーミッションを付加します。
  パーミッション：android.permission.MODIFY_AUDIO_SETTINGS

　詳細はサンプルアプリケーションを参照ください。


■API仕様
<ディスプレイ制御>
ディスプレイの2D/3D切替
　クラス　　　：DisplayControl
  関数        ：setMode
  概要        ：BT-200のディスプレイモードを切替える
  プロトタイプ：int setMode(int displayMode,boolean toast)
  引数        ：ディスプレイモード番号　DISPLAY_MODE_2D(2Dモード)、DISPLAY_MODE_3D(3Dモード)
　　　　　　　　トースト　true(表示)、false(非表示)
  戻り値      ：実行結果　0（正常値）、その他（エラー値）

ディスプレイ輝度の設定
　クラス　　　：DisplayControl
  関数        ：setBacklight
  概要        ：BT-200のディスプレイ輝度を設定する
  プロトタイプ：int setBacklight(int backlight)
  引数        ：ディスプレイ輝度 0~20
  戻り値      ：実行結果　0（正常値）、その他（エラー値）

ディスプレイ輝度値の取得
　クラス　　　：DisplayControl
  関数        ：getBacklight
  概要        ：BT-200のディスプレイ輝度値を取得する
  プロトタイプ：int getBacklight()
  引数        ：なし
  戻り値      ：ディスプレイ輝度値　0~20（正常値）、その他（エラー値）

ディスプレイミュートのON/OFF機能
　クラス　　　：DisplayControl
  関数        ：setMute
  概要        ：BT-200のディスプレイミュートをON/OFFする
  プロトタイプ：int setMute(boolean mute)
  引数        ：ミュートON(TRUE)/OFF(FALSE)
  戻り値      ：実行結果　0（正常値）、その他（エラー値）


<オーディオ制御>
オーディオミュートのON/OFF切替
　クラス　　　：AudioControl
  関数        ：setMute
  概要        ：BT-200のオーディオミュートをON/OFFする
  プロトタイプ：setMute(boolean mute)
  引数        ：ミュートON(TRUE)/OFF(FALSE)
  戻り値      ：実行結果　0（正常値）、その他（エラー値）


<センサー制御>
センサーの切り替え（コントローラー/ヘッドセット）
　クラス　　　：SensorControl
  関数        ：setMode
  概要        ：BT-200のセンサーモードを切り替える
  プロトタイプ：setMode(int sensorMode)
  引数        ：センサーモード番号　SENSOR_MODE_CONTROLLER(コントローラーモード)、SENSOR_MODE_HEADSET(ヘッドセットモード)
  戻り値      ：実行結果　0（正常値）、その他（エラー値）


以上
