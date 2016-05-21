BT-200 Display and Audio Control Library Package
                                                           SEIKO EPSON CORPORATION
■ Summary of Functions
This library enables Android Apps to control BT-200’s display, audio and sensors.
 - 2D / 3D switching for display and display Toast view.
 - Setting/Getting display brightness
 - ON/OFF switching for display mute
 - ON/OFF switching for audio mute
 - Sensor switching (Controller / Headset)

■ Note
 - Display mute is for minimizing background brightness of display, not for display off.
 - Sensor switching should be performed when the sensor is not in use. Switching sensor settings while in use may cause unexpected results.
 - Sensor values can be obtained through standard Android API. However, App cannot use sensors on both the controller and headset at the same time. 

■ Environment
Device: BT-200
OS    : LinuxKernel 3.0.21、Android 4.0.4(API Lv:15 or later)

■ Contents
 - libBT200Ctrl.zip    Library
 - BT200CtrlDemo.zip   Sample App
 - ReadMe.txt          This file

■ How to use this library
 In the case of using eclipse as an IDE, the following applies: 
1. Create AndroidProject with eclipse
2. Create “libs” folder under AndroidProject root folder
3. Unzip libBt2Ctrl.zip, then copy the library into libs folder
 -libs\Bt2Ctrl.jar
4. import the library package in Application source code
   Package Name:jp.epson.moverio.bt200.DisplayControl
   Package Name:jp.epson.moverio.bt200.AudioControl
   Package Name:jp.epson.moverio.bt200.SensorControl
5. Add permission in AndroidManifest.xml
   Permission:android.permission.MODIFY_AUDIO_SETTINGS

See Sample App for detail

■ API
<Display Control>
Display 2D/3D Switching
   Class       :DisplayControl
   Function    :setMode
   Summary     :Switch BT-200 display mode
   Prototype   :int setMode(int displayMode,boolean toast)
   Argument    :Display Mode Number DISPLAY_MODE_2D, DISPLAY_MODE_3D
                toast true(Display), false(not display)
   Return Value:result 0(success), other(error value)

Setting Display Brightness
   Class       :DisplayControl
   Function    :setBacklight
   Summary     :Set BT-200 display brightness
   Prototype   :int setBacklight(int backlight)
   Argument    :Display Brightness 0-20
   Return Value:result 0(success), other(error value)

Getting Display Brightness
   Class       :DisplayControl
   Function    :getBacklight
   Summary     :Get BT-200 display brightness value
   Prototype   :int getBacklight()
   Argument    :none
   Return Value:Brightness value 0-20(success), other(error value)

Set ON/OFF for Display Mute
   Class       :DisplayControl
   Function    :setMute
   Summary     :Set BT-200 display mute ON/OFF
   Prototype   :int setMute(boolean mute)
   Argument    :mute ON(TRUE), OFF(FALSE)
   Return Value:result 0(success), other(error value)

<Audio Control>
Set ON/OFF for Audio Mute
   Class       :AudioControl
   Function    :setMute
   Summary     :Set BT-200 Audio mute ON/OFF
   Prototype   :int setMute(boolean mute)
   Argument    :mute ON(TRUE), OFF(FALSE)
   Return Value:result 0(success), other(error value)

<Sensor Control>
Switch sensor (Controller/Headset)
   Class       :SensorControl
   Function    :setMode
   Summary     :Switch BT-200 sensor mode
   Prototype   :int setMode(int sensorMode)
   Argument    :Sensor Mode Number SENSOR_MODE_CONTROLLER(Controller), SENSOR_MODE_HEADSET(Headset)
   Return Value:result 0(success), other(error value)





