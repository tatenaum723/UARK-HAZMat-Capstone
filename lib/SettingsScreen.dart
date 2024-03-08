import 'package:flutter/material.dart';

@immutable
class SettingsScreen extends StatelessWidget {
  final Function onBack;

  SettingsScreen({required this.onBack});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Settings'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () => onBack(),
              child: Text('Back'),
            ),
            Text('Settings Screen via SettingsScreen.dart (non-default). This proves that we can build pages independently in their own .dart file'),
          ],
        ),
      ),
    );
  }
}


