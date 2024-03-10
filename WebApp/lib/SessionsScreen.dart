import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'dart:convert';
import 'SessionData.dart';

class SessionsScreen extends StatefulWidget {
  final VoidCallback onBack;
  final Function(String) onSessionSelected; 
  final Function(SessionData) onSessionDataFetched; 

  const SessionsScreen({
    Key? key, 
    required this.onBack,
    required this.onSessionSelected,
    required this.onSessionDataFetched,
  }) : super(key: key);

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
							onTap: () async {
							  // Clear previous session data if any
							  SessionData sessionData = SessionData.empty();
							  sessionData.clear(); // Reset before fetching new data
							  
							  widget.onSessionSelected(session['id']);
							  sessionData = await fetchSessionData(session['id']);
							  widget.onSessionDataFetched(sessionData);
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

	Future<SessionData> fetchSessionData(String sessionId) async {
	  final user = FirebaseAuth.instance.currentUser;
	  if (user != null) {
		DatabaseReference ref = FirebaseDatabase.instance.ref('users/${user.uid}/readings/$sessionId');
		final snapshot = await ref.get();

		if (snapshot.exists) {
		  return SessionData.fromSnapshot(sessionId, snapshot);
		}
	  }
	  // Handle the case when datasets are not available or an error occurs
	  return SessionData.empty();
	}

}