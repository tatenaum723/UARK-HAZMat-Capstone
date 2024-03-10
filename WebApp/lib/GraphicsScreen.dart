import 'package:flutter/material.dart';

@immutable
class GraphicsScreen extends StatelessWidget {
  final Function onBack;

  GraphicsScreen({required this.onBack});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Graph View'),
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
            Text('Placeholder page for data graphical illustration'),
          ],
        ),
      ),
    );
  }
}


