import 'package:flutter/material.dart';
import 'theme_data.dart';
import 'main.dart';
import 'AppBar.dart';

class SettingsScreen extends StatefulWidget {
  final VoidCallback onBack;

  SettingsScreen({required this.onBack});

  @override
  _SettingsScreenState createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Settings',
        backgroundColor: Theme.of(context).colorScheme.surface,
        titleColor: Theme.of(context).colorScheme.onSurface,
        titleFontSize: 20,
        iconSize: 24,
        onBack: widget.onBack,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            SwitchListTile(
              title: Text('Toggle Dark Mode', style: Theme.of(context).textTheme.headline6),
              value: ThemeManager.isDarkMode,
              onChanged: (bool value) {
                ThemeManager.isDarkMode = value;
                MyApp.restartApp(context); // Restart app to apply theme globally
              },
              activeColor: Theme.of(context).colorScheme.secondary,
              subtitle: Text('Turn on dark mode for better night visibility'),
            ),
          ],
        ),
      ),
    );
  }
}
