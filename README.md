# EZ NFC
## ABSTRACT
Near Field Communication (NFCs) technology is in its basic form, automation wedded to smartphone capabilities. NFC enables a subset of the Radio Frequency Identification (RFID) technology that works over a wide range of frequencies with three distinct bands — low, high, and ultra-high frequencies. NFC is a short-range half duplex communication technology which provide secure communication between devices in near field region. Near field communication, is a technology that allows two devices in close range to securely exchange data wirelessly. NFC is a short- range (¡ 10 cm) wireless connectivity technology that operates at high frequency (HF) range with low bandwidth of radio waves, mainly at 13.56 MHz [13]. NFC comprised of three basic components - an antenna, a reader, and a tag. 

## AIM
Our aim is to create an app called NFC communication for the smartphone devices that have NFC capability to do variety of tasks for the user. There is a myriad of NFC based transactions and tasks through automation and we aim to include many NFC functions into this one app. There are mainly 8 real-world applications for NFC enabled smartphones that we are aiming to implement. 

1) Reading Tags
2) Writing Tags
3) Reminders
4) Key for NFC enabled smart doors and locks
5) Connectivity for WIFI and Bluetooth 
6) Smart home automation 
7) Data Transfer
8) Automation of Tasks 

## FEATURES AVAILABLE 
This project is still in its early stages of development I have included only read and write functions of the tag. As of now the design and layout of the app is kept to a bare minimum in order to concentrate more on the betterment of features. The planned design for this app is the another repo called nfc_design. For now this repo concentrates on making the available features work and to make room for future updates

## REQUIREMENTS
Smartphone with NFC compatibility 
Running on Android 9 or greater
NFC Tags or cards for testing 

## HOW TO USE
This app must be run only on a real device which has NFC compatibility and running on Android 9 or higher. I use Samsung Galaxy S20 FE 5G to test this app. 

Follow the following steps to run the app on your smartphone 

1) Turn on the Developer Mode in your phone for better access of the phone’s log and Debugging process. Different brands have different ways of activating the Developer Mode, so you will need to access the official websites of the manufacturer for more information 
2) After activating Developer Mode, Go to Settings, scroll down to the bottom where now the section “Developer Options” will be visible. Tap that and scroll down till you see “USB Debugging” turn the toggle on.
3) Now connect the phone to your computer. You have to have your phone unlocked during this entire process. Once connected select “USB Tethering “ (In Samsung devices sometimes it appears in the notification center. Make sure to check there too)
4) Once the phone has been connected, wait for the **Device Manager** from the Android Studio to detect the device. Once the device is detected, it is ready to use
5) Before running the app on your phone directly, make sure you click **Build** and there are no reported errors. Most importantly turn on NFC on your phone as it is off by default. 
6) Run the app. To close the app, press the red, square button “Stop” in Android Studio because that’s how the app is really stopped and exiting through the phone’s navigation keeps the app active in the background.

**NOTE:**
1) **The app will be installed on your phone and if you check the list of apps, this app will be present even after disconnecting from the PC. This is because Android Studio installs the app in the phone storage locally before launching it. You can uninstall it without any hazard like one would uninstall any other app**
2) **Make sure you remove the back case of your phone for better and quicker results, especially if the case is a very thick or a metallic one**