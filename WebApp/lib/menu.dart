import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'dart:convert';

import 'SettingsScreen.dart';
import 'LoginScreen.dart';
import 'SessionsScreen.dart';
import 'GraphicsScreen.dart';
import 'ListsScreen.dart';
import 'SessionData.dart';

enum Screen { LOGIN, MAIN_MENU, SETTINGS, SESSIONS, GRAPHICS, LISTS, MENU_4 }

class AppContent extends StatefulWidget {
  @override
  _AppContentState createState() => _AppContentState();
}

class _AppContentState extends State<AppContent> {
  Screen currentScreen = Screen.LOGIN;
  bool loginError = false;
  String? selectedSessionId;
  
  SessionData sessionData = SessionData.empty(); // Initialize with empty data


  void _logout() {
    FirebaseAuth.instance.signOut().then((_) {
      setState(() {
        currentScreen = Screen.LOGIN;
        loginError = false;
        selectedSessionId = null;
		sessionData.clear();
      });
    });
  }

  void _selectSession(String sessionId) {
    setState(() {
      selectedSessionId = sessionId;
      currentScreen = Screen.MAIN_MENU; // Navigate back to the main menu
    });
  }


	@override
	Widget build(BuildContext context) {
	  return Scaffold(
		body: _buildBody(),
		bottomNavigationBar: selectedSessionId != null
			? BottomAppBar(
				child: Padding(
				  padding: const EdgeInsets.all(16.0),
				  child: Text('Session ID: $selectedSessionId'),
				),
			  )
			: null,
	  );
	}

	Widget _buildBody() {
	  switch (currentScreen) {
		case Screen.LOGIN:
		  return LoginScreen(
			onLoginSuccess: () => setState(() => currentScreen = Screen.MAIN_MENU),
			onLoginFailed: () => setState(() => loginError = true),
			loginError: loginError,
		  );
		case Screen.MAIN_MENU:
		  return MainMenuScreen(
			onScreenSelected: (selectedScreen) {
			  if (selectedScreen == Screen.LISTS && selectedSessionId != null) {
				// Navigate to ListsScreen when LISTS is selected and session ID is not null
				Navigator.push(
				  context,
					MaterialPageRoute(
					  builder: (context) => ListsScreen(sessionData: sessionData),
					),
				);
			  } else if (selectedScreen != Screen.SESSIONS && selectedSessionId == null && (selectedScreen == Screen.GRAPHICS || selectedScreen == Screen.LISTS || selectedScreen == Screen.MENU_4)) {
				// If GRAPHICS, LISTS, or MENU_4 are selected without a session, do not navigate
				return;
			  } else {
				setState(() => currentScreen = selectedScreen);
			  }
			},
			onLogout: _logout,
			isSessionSelected: selectedSessionId != null,
		  );
		case Screen.SETTINGS:
		  return SettingsScreen(onBack: () => setState(() => currentScreen = Screen.MAIN_MENU));
		case Screen.SESSIONS:
		  return SessionsScreen(
			onBack: () => setState(() => currentScreen = Screen.MAIN_MENU),
			onSessionSelected: _selectSession,
			onSessionDataFetched: (fetchedSessionData) {
			  // Update sessionData with the fetched data
			  setState(() {
				sessionData = fetchedSessionData;
			  });
			},
		  );
		default:
		  return DefaultScreen(onBack: () => setState(() => currentScreen = Screen.MAIN_MENU));
	  }
	}
}

class MainMenuScreen extends StatelessWidget {
  final Function(Screen) onScreenSelected;
  final VoidCallback onLogout;
  final bool isSessionSelected;
  final String? selectedSessionId;

  MainMenuScreen({
    required this.onScreenSelected,
    required this.onLogout,
    required this.isSessionSelected,
    this.selectedSessionId,
  });

  @override
  Widget build(BuildContext context) {
    // Initial list of widgets, which are ListTiles
    List<Widget> menuItems = Screen.values
        .where((screen) => screen != Screen.LOGIN && screen != Screen.MAIN_MENU)
        .map((screen) {
          bool isDisabled = !isSessionSelected && (screen == Screen.GRAPHICS || screen == Screen.LISTS || screen == Screen.MENU_4);
          return ListTile(
            title: Text(screen.toString().split('.').last),
            onTap: isDisabled ? null : () => onScreenSelected(screen),
            tileColor: isDisabled ? Colors.grey[300] : null,
          );
        }).toList();

    // Properly add a logout ListTile to the list
    menuItems.add(ListTile(
      title: Text('Logout'),
      onTap: onLogout,
    ));

    // If a session ID is selected, add a properly formatted Text widget wrapped in a Padding widget to the list
    if (selectedSessionId != null) {
      menuItems.add(Padding(
        padding: const EdgeInsets.all(16.0),
        child: Text('Session ID: ${selectedSessionId}'),
      ));
    }

    // Use the menuItems list to build the ListView
    return ListView(children: menuItems);
  }
}



class DefaultScreen extends StatelessWidget {
  final VoidCallback onBack;

  const DefaultScreen({Key? key, required this.onBack}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Unimplemented Screen"),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: onBack,
        ),
      ),
      body: const Center(child: Text("This screen has not been implemented yet.")),
    );
  }
}

