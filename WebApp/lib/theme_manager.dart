// theme_manager.dart
import 'package:flutter/material.dart';
import 'theme_data.dart';

class ThemeManager {
    static bool isDarkMode = false;  // Initial theme mode is light.

    static void toggleTheme() {
        isDarkMode = !isDarkMode;
    }

    static ThemeData get currentTheme => isDarkMode ? darkTheme : lightTheme;
}
