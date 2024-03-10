import 'package:flutter/material.dart';
import 'SessionData.dart';

@immutable
class ListsScreen extends StatelessWidget {
  final SessionData sessionData; 

  ListsScreen({required this.sessionData});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('List View'),
      ),
      body: SingleChildScrollView( // Use SingleChildScrollView to accommodate long lists
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            // Display Methane Percentage List
            Text('Methane Percentage:'),
            ListView.builder(
              shrinkWrap: true, 
              physics: NeverScrollableScrollPhysics(),
              itemCount: sessionData.methanePercentage.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text('${sessionData.methanePercentage[index]}'),
                );
              },
            ),
            // Display Methane Volume List
            Text('Methane Volume:'),
            ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: sessionData.methaneVolume.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text('${sessionData.methaneVolume[index]}'),
                );
              },
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomAppBar(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Text('Session ID: ${sessionData.sessionId}'),
        ),
      ),
    );
  }
}
