![](https://play-lh.googleusercontent.com/YUZrTtPNwzzuBa-Dkv9S_cqPChykJk63iu3qy0fzQ86NVCgfE-Dt4qAHBxYoLVgut54=w1052-h592-rw)

# Password Manager
Tired of memorizing all your passwords or struggling to keep them somewhere safe? This app is what you need!

## Application Install

***You can install the latest version of Password Manager from the link given below 👇***

[![Password Manager](https://img.shields.io/badge/DocuBox✅-APK-red.svg?style=for-the-badge&logo=android)](https://play.google.com/store/apps/details?id=com.ishant.passwordmanager&hl=en_US&gl=US)

## About

![](https://play-lh.googleusercontent.com/-Cos0V6VBmG5_L9u8qddkPTzJfczZAOGD7y8jlZl8xHdWe_qLViHHn0kvh5G4-XP6A=w1052-h592-rw)

Password manager is an app where you can store all your login credentials and other information in a secure and organized way. The app does not require any internet permission so all your information will securely stored inside your device.


## App Features

- All the emails, passwords and other credentials stored using this app are encrypted two times using two different keys. One of the key is always unique and is generated during the runtime where as the other key remains hidden deep inside the app and is secured using the power of C++ NDK and proguard.
- The app can also generate random passwords that can be copied to clipboard. A user can change the password generation criteria and also test the strength of his or her own passwords.
- This app does not require any internet permissions so all your passwords will remain securely stored inside your own device.
- The app comes with an inbuilt app lock feature with anti bruteforce mechanism that prevents others from accessing your information.
- If you have numerous of accounts to manage, you can add them to favourites, or you can categorize them and search later using the app.
- A user can also set a password hint and change their password whenever they want. 
- The app has a beautiful UI which makes it easy to use.

## Insights into the app

![](https://play-lh.googleusercontent.com/YOl0M8F-7dgMMh9ckoU6mF2MQ5gfbSfs94hpopJyarHXJiM0HqXGnq8lLPSImukxON1I=w1052-h592-rw)

![](https://play-lh.googleusercontent.com/JMACKIjf78MKeSNUanIMBpkx8o09RYHOirFGYkfeLHCi0mDlGO5AHfvrx7YcY4BuKbyn=w1052-h592-rw)

![](https://play-lh.googleusercontent.com/VfS77VnwwZ7l7N6jrDac9qSHF6Uo3EBNmuO-RSR-jT16i7rTuHKFbBTy_-YTq6JV8Q=w1052-h592-rw)

## Project Setup
Everything you want is included in this repository so all you need to do is to clone this repository on your computer and open it in android studio and then you are good to go!

## Project Architecture
This app is built using ![Model View ViewModel Architecture](https://developer.android.com/jetpack/docs/guide#recommended-app-arch).

![](https://github.com/Vaibhav2002/DocuBox-AndroidApp/raw/master/media/architecture.png)

## Project Structure

    com.passwordmanager     # Root Package
    .
    ├── adapters            # Contains all the adapters for recycleviews
    |
    ├── db                  # Contains files related to android's database storage like Dao and Database class
    │   └── entities        # Contains files which define an entity or table's schema in room database
    |
    ├── repository          # Contains all the functions defined in DAO object
    |
    ├── security            # Contains the logic to encrypt and decrypt the data
    |
    ├── ui                  # UI/View layer
    |   ├── activities      # Contains the UI implementation of all activities and fragments
    |   ├── factories       # Contains viewmodel factories that enable us to pass repositories in viewmodel
    |   └── viewmodels      # A communication gateway between our views and models 
    |
    └── util                # Utility / Helper classes


## Contact
For any queries, you can mail me at developerishant710@gmail.com
