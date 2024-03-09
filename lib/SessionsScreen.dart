import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';

class SessionsScreen extends StatefulWidget {
  final VoidCallback onBack;

  const SessionsScreen({Key? key, required this.onBack}) : super(key: key);

  @override
  _SessionsScreenState createState() => _SessionsScreenState();
}

class _SessionsScreenState extends State<SessionsScreen> {
  bool isLoading = true;
  Map<String, List<Map<String, dynamic>>> sessionsByDate = {};

  @override
  void initState() {
    super.initState();
    fetchSessions();
  }

  Future<void> fetchSessions() async {
    final user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      DatabaseReference ref = FirebaseDatabase.instance.ref('users/${user.uid}/readings');
      DataSnapshot snapshot = await ref.get();

      if (snapshot.exists) {
        Map<String, dynamic> readings = Map<String, dynamic>.from(snapshot.value as Map);
        readings.forEach((key, reading) {
          final Map<String, dynamic> readingData = Map<String, dynamic>.from(reading);
          final String date = readingData['date'];
          sessionsByDate.putIfAbsent(date, () => []).add({
            'name': readingData['name'],
            'id': key, // Use the key from the database as the reading ID
            // Include other properties you may need
          });
        });
      }
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sessions'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: widget.onBack,
        ),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : sessionsByDate.isNotEmpty
              ? ListView.builder(
                  itemCount: sessionsByDate.keys.length,
                  itemBuilder: (context, index) {
                    String date = sessionsByDate.keys.elementAt(index);
                    List<Map<String, dynamic>> sessions = sessionsByDate[date]!;
                    return ExpansionTile(
                      title: Text(date),
                      children: sessions.map<Widget>((session) {
                        return ListTile(
                          title: Text(session['name'] ?? 'Unnamed Session'),
                          subtitle: Text(session['id']),
                          onTap: () {
                            // Handle tap, possibly navigate to a detail page
                          },
                        );
                      }).toList(),
                    );
                  },
                )
              : const Center(
                  child: Text('No sessions found'),
                ),
    );
  }
}
