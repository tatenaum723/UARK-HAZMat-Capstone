// theme_data.dart
import 'package:flutter/material.dart';

Color _getColorFromHex(String hexColor) {
  final hexCode = hexColor.toUpperCase().replaceAll('#', '');
  return Color(int.parse('FF$hexCode', radix: 16));
}

final Map<String, Color> darkModeColors = {
  'first': _getColorFromHex('1e1e29'), //background color
  'second': _getColorFromHex('181820'), // secondary background accent
  'third': _getColorFromHex('2d2d3c'), // lighter blue for lighter boxes, such as a login button or other important buttons and clickables
  'fourth': _getColorFromHex('0E1E40'), // darker blue for cards (paired with third option)
  'fifth': _getColorFromHex('d2d5dc'), // light bluish gray for accent colors such as muted text colors in boxes and such (or accents)
  'sixth': _getColorFromHex('e4e6ec'), //white for default text color (so it can be seen on dark mode. this should be black on light mode)
  'seventh': _getColorFromHex('B8C9E3'), //  LIGHT grayish blue for accents 
  'eighth': _getColorFromHex('FF8A65'), //for data points
  'ninth': _getColorFromHex('335577'), //for dashboard
  
};

final Map<String, Color> lightModeColors = {
  'first': _getColorFromHex('FFFFFF'), // Background color 
  'second': _getColorFromHex('ECECEC'), // Secondary background accent 
  'third': _getColorFromHex('F5F5F5'), // Primary buttons and clickable items
  'fourth': _getColorFromHex('F5F5F5'), // Secondary for cards
  'fifth': _getColorFromHex('6F6F6F'), // Muted text colors in boxes and accents 
  'sixth': _getColorFromHex('000000'), // Black for default text color 
  'seventh': _getColorFromHex('607D8B'), // Another grayish blue for accents to go with "fifth" color 
  'eighth': _getColorFromHex('1976D2'), //for data points and tertiary text
  'ninth': _getColorFromHex('4F4F4F'), //for dashboard
  
};


final ThemeData darkTheme = ThemeData(
  backgroundColor: darkModeColors['second']!, 
  colorScheme: ColorScheme.dark(
    primary: darkModeColors['second']!, 
    secondary: darkModeColors['third']!, 
	tertiary: darkModeColors['seventh'],
    surface: darkModeColors['first']!, 
    onSurface: darkModeColors['sixth']!,
    onPrimary: darkModeColors['fifth']!,
	onBackground: darkModeColors['sixth']!,
	onSecondary: darkModeColors['eighth']!,
	onTertiary: darkModeColors['third']!,
  onSecondaryContainer: darkModeColors['ninth']!,
  ),
  scaffoldBackgroundColor: darkModeColors['second']!, 
  cardColor: darkModeColors['first']!, 
  textTheme: TextTheme(
    bodyText1: TextStyle(color: darkModeColors['fifth']!), 
    bodyText2: TextStyle(color: darkModeColors['sixth']!), 
    caption: TextStyle(color: darkModeColors['seventh']!), 
  ),
  buttonTheme: ButtonThemeData(
    buttonColor: darkModeColors['second']!,  // Example button color for dark theme
    textTheme: ButtonTextTheme.primary, // Use the primary text theme for buttons
  ),
  brightness: Brightness.dark,
  
);

final ThemeData lightTheme = ThemeData(
  backgroundColor: lightModeColors['first']!, 
  colorScheme: ColorScheme.light(
    primary: lightModeColors['second']!, 
    secondary: lightModeColors['first']!, 
	tertiary: lightModeColors['fourth'],
    surface: lightModeColors['second']!, 
    onSurface: lightModeColors['sixth']!, 
    onPrimary: lightModeColors['sixth']!, 
	onBackground: lightModeColors['sixth']!,
	onSecondary: lightModeColors['eighth']!,
	onTertiary: darkModeColors['sixth']!,
  onSecondaryContainer: lightModeColors['ninth']!,
  ),
  scaffoldBackgroundColor: lightModeColors['first']!, 
  cardColor: lightModeColors['second']!, 
  textTheme: TextTheme(
    bodyText1: TextStyle(color: lightModeColors['fifth']!), 
    bodyText2: TextStyle(color: lightModeColors['sixth']!), 
    caption: TextStyle(color: lightModeColors['seventh']!), 
  ),
  buttonTheme: ButtonThemeData(
    buttonColor: lightModeColors['second']!,  // Example button color for dark theme
    textTheme: ButtonTextTheme.primary, // Use the primary text theme for buttons
  ),
  brightness: Brightness.light,
);



class ThemeManager {
  static bool isDarkMode = true;  // Initial theme mode is dark.

  static void toggleTheme() {
    isDarkMode = !isDarkMode;
  }

  static ThemeData get currentTheme => isDarkMode ? darkTheme : lightTheme;
}
