import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'dart:convert';
import 'SessionData.dart';
import 'AppBar.dart'; 
import 'package:table_calendar/table_calendar.dart';
import 'package:intl/intl.dart';

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
  Map<DateTime, List<Map<String, dynamic>>> sessionsByDateTime = {};
  Map<String, List<Map<String, dynamic>>> sessionsByDate = {};
  DateTime _selectedDay = DateTime.now();
  DateTime _focusedDay = DateTime.now();
  bool _showListView = false;

  @override
  void initState() {
    super.initState();
    fetchSessions();
  }
  Future<void> populateSessionsForCalendar(Map<String, dynamic> readings) async {
    readings.forEach((key, reading) {
      final Map<String, dynamic> readingData = Map<String, dynamic>.from(reading);
      final String dateString = readingData['date'];
      final DateTime parsedDate = parseDate(dateString);
      sessionsByDateTime.putIfAbsent(parsedDate, () => []).add({
        'name': readingData['name'],
        'id': key,
      });
    });
  }

	Future<void> fetchSessions() async {
	final user = FirebaseAuth.instance.currentUser;
	if (user != null) {
		DatabaseReference ref = FirebaseDatabase.instance.ref('users/${user.uid}/readings');
		DataSnapshot snapshot = await ref.get();
		if (snapshot.exists) {
		Map<String, dynamic> readings = Map<String, dynamic>.from(snapshot.value as Map);
		// Call the new helper method
		await populateSessionsForCalendar(readings);
		readings.forEach((key, reading) {
			final Map<String, dynamic> readingData = Map<String, dynamic>.from(reading);
			final String dateString = readingData['date'];  
			final DateTime parsedDate = parseDate(dateString);  

			// Store sessions by string date for list display
			sessionsByDate.putIfAbsent(dateString, () => []).add({
			'name': readingData['name'],
			'id': key,
			});
		});
		}
		if (mounted) {
		setState(() {
			isLoading = false;
		});
		}
	}
	}

DateTime parseDate(String dateString) {
  try {
    // Parse the date using the format "MM-dd-yyyy" and convert to UTC.
    DateTime localDate = DateFormat('MM-dd-yyyy').parse(dateString, true).toUtc();
    // Return only the date part (year, month, day) normalized to UTC.
    return DateTime.utc(localDate.year, localDate.month, localDate.day);
  } catch (e) {
    print('Error parsing date $dateString: $e');
    // Return current date in UTC as fallback
    return DateTime.now().toUtc();
  }
}

@override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: CustomAppBar(
      title: 'Sessions',
      backgroundColor: Theme.of(context).colorScheme.surface,
      titleColor: Theme.of(context).colorScheme.onSurface,
      titleFontSize: 20,
      iconSize: 24,
      onBack: widget.onBack,
      additionalActions: [
        IconButton(
          icon: Icon(_showListView ? Icons.calendar_today : Icons.list),
          onPressed: () {
            setState(() {
              _showListView = !_showListView;
            });
          },
          tooltip: 'Toggle Calendar/List View',
        ),
      ],
    ),
    body: isLoading
        ? const Center(child: CircularProgressIndicator())
        : _showListView ? _buildListView() : _buildCalendar(),
  );
}

Widget _buildListView() {
  return ListView.builder(
    itemCount: sessionsByDate.keys.length,
    itemBuilder: (context, index) {
      String date = sessionsByDate.keys.elementAt(index);
      List<Map<String, dynamic>> sessions = sessionsByDate[date]!;
      return Theme(
        data: Theme.of(context).copyWith(
          dividerColor: Colors.transparent,
          expansionTileTheme: ExpansionTileThemeData(
            backgroundColor: Theme.of(context).colorScheme.surface.withOpacity(0.90),
            collapsedBackgroundColor: Theme.of(context).colorScheme.surface.withOpacity(0.90),
            expandedAlignment: Alignment.centerLeft,
            iconColor: Theme.of(context).colorScheme.onSurface,
            collapsedIconColor: Theme.of(context).colorScheme.onSurface,
          ),
        ),
        child: ExpansionTile(
          title: Text(date, style: TextStyle(color: Theme.of(context).colorScheme.onSurface)),
          children: sessions.map<Widget>((session) {
            return ListTile(
              tileColor: Theme.of(context).colorScheme.background,
              title: Text(session['name'], style: TextStyle(color: Theme.of(context).colorScheme.onPrimary)),
              subtitle: Text(session['id'], style: TextStyle(color: Theme.of(context).colorScheme.onSecondary)),
              onTap: () {
                widget.onSessionSelected(session['id']);
                fetchSessionData(session['id']).then(widget.onSessionDataFetched);
              },
            );
          }).toList(),
        ),
      );
    },
  );
}

Widget _buildCalendar() {
  return TableCalendar(
    firstDay: DateTime.utc(2010, 10, 16),
    lastDay: DateTime.utc(2030, 3, 14),
    focusedDay: _focusedDay,
    selectedDayPredicate: (day) => isSameDay(_selectedDay, day),
    onDaySelected: (selectedDay, focusedDay) {
      setState(() {
        _selectedDay = selectedDay;
        _focusedDay = focusedDay;
      });
      _showSessionDialog(selectedDay); // Show dialog with sessions for the selected day
    },
    eventLoader: (day) {
      return sessionsByDateTime[day] ?? [];
    },
    calendarBuilders: CalendarBuilders(
      markerBuilder: (context, date, events) {
        if (events.isNotEmpty) {
          return Positioned(
            right: 50, // Adjusted for better alignment
            top: 5,
            child: Container(
              width: 16,
              height: 16,
              decoration: BoxDecoration(
                color: Color(0xFFFF8A65),
                shape: BoxShape.circle,
              ),
              alignment: Alignment.center,
              child: Text('${events.length}', style: TextStyle(color: Colors.white, fontSize: 12)),
            ),
          );
        }
        return null;
      },
    ),
    availableCalendarFormats: const {
      CalendarFormat.month: 'Month' // Only allow month view
    },
    onFormatChanged: (format) {
      print("Calendar format changed to: $format");
    }
  );
}

void _showSessionDialog(DateTime day) {
  var sessions = sessionsByDateTime[day];
  if (sessions != null && sessions.isNotEmpty) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text("Sessions on ${DateFormat('yyyy-MM-dd').format(day)}"),
          content: Container(
            width: double.minPositive,
            child: ListView.builder(
              shrinkWrap: true,
              itemCount: sessions.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(sessions[index]['name']),
                  subtitle: Text("ID: ${sessions[index]['id']}"),
                  onTap: () {
                    Navigator.of(context).pop(); 
                    widget.onSessionSelected(sessions[index]['id']); 
                    fetchSessionData(sessions[index]['id']).then(widget.onSessionDataFetched); // Fetch and handle session data
                  },
                );
              },
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Close'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}


  Future<SessionData> fetchSessionData(String sessionId) async {
    final user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      DatabaseReference ref = FirebaseDatabase.instance.ref('users/${user.uid}/readings/$sessionId');
      DataSnapshot snapshot = await ref.get();

      if (snapshot.exists) {
        return SessionData.fromSnapshot(sessionId, snapshot);
      }
    }
    return SessionData.empty();
  }
}
