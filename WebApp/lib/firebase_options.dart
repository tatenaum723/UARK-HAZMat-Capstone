// File generated by FlutterFire CLI.
// ignore_for_file: lines_longer_than_80_chars, avoid_classes_with_only_static_members
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {
    if (kIsWeb) {
      return web;
    }
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for ios - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.macOS:
        return macos;
      case TargetPlatform.windows:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for windows - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.linux:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for linux - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      default:
        throw UnsupportedError(
          'DefaultFirebaseOptions are not supported for this platform.',
        );
    }
  }

  static const FirebaseOptions web = FirebaseOptions(
    apiKey: 'AIzaSyA8v5LXvx7VFQnctldhkiPVDlxc6j2h8ZY',
    appId: '1:876868125634:web:2a2caae125553e26368c5b',
    messagingSenderId: '876868125634',
    projectId: 'haz-mat-7ad0f',
    authDomain: 'haz-mat-7ad0f.firebaseapp.com',
    databaseURL: 'https://haz-mat-7ad0f-default-rtdb.firebaseio.com',
    storageBucket: 'haz-mat-7ad0f.appspot.com',
    measurementId: 'G-M2VZMCVECH',
  );

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyAox_S1RDvys8I6T43Lx-jNOtBgLrBY5eg',
    appId: '1:876868125634:android:cc452d9c4d9782f9368c5b',
    messagingSenderId: '876868125634',
    projectId: 'haz-mat-7ad0f',
    databaseURL: 'https://haz-mat-7ad0f-default-rtdb.firebaseio.com',
    storageBucket: 'haz-mat-7ad0f.appspot.com',
  );

  static const FirebaseOptions macos = FirebaseOptions(
    apiKey: 'AIzaSyB9_juEgv8ulyvv9CHkIDWDvTnZ7PL_zag',
    appId: '1:876868125634:ios:b39a3d7b87e4945d368c5b',
    messagingSenderId: '876868125634',
    projectId: 'haz-mat-7ad0f',
    databaseURL: 'https://haz-mat-7ad0f-default-rtdb.firebaseio.com',
    storageBucket: 'haz-mat-7ad0f.appspot.com',
    iosBundleId: 'com.example.capstone2DesktopApp.RunnerTests',
  );
}
