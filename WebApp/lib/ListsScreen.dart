import 'package:flutter/material.dart';

@immutable
class ListsScreen extends StatelessWidget {
  final Function onBack;

  ListsScreen({required this.onBack});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('List View'),
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
            Text('Placeholder page for data list view'),
          ],
        ),
      ),
    );
  }
}


