import 'package:flutter/material.dart';
import 'SettingsScreen.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'LoginScreen.dart';

enum Screen { LOGIN, MAIN_MENU, SETTINGS, MENU_1, MENU_2, MENU_3, MENU_4 }

class AppContent extends StatefulWidget {
  @override
  _AppContentState createState() => _AppContentState();
}

class _AppContentState extends State<AppContent> {
  Screen currentScreen = Screen.LOGIN;
  bool loginError = false;

  void _logout() {
    FirebaseAuth.instance.signOut().then((value) {
      setState(() {
        currentScreen = Screen.LOGIN;
        loginError = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    switch (currentScreen) {
      case Screen.LOGIN:
        return LoginScreen(
          onLoginSuccess: () => setState(() => currentScreen = Screen.MAIN_MENU),
          onLoginFailed: () => setState(() => loginError = true),
          loginError: loginError,
        );
      case Screen.MAIN_MENU:
        // Now correctly providing onLogout parameter
        return MainMenuScreen(
          onScreenSelected: (selectedScreen) => setState(() => currentScreen = selectedScreen),
          onLogout: _logout,
        );
      case Screen.SETTINGS:
        return SettingsScreen(
          onBack: () => setState(() => currentScreen = Screen.MAIN_MENU),
        );
      // Your additional case statements for other screens
      default:
        return DefaultScreen(
          onBack: () => setState(() => currentScreen = Screen.MAIN_MENU),
        );
    }
  }
}

class MainMenuScreen extends StatelessWidget {
  final Function(Screen) onScreenSelected;
  final VoidCallback onLogout;

  MainMenuScreen({required this.onScreenSelected, required this.onLogout});

  @override
  Widget build(BuildContext context) {
    List<Widget> menuItems = Screen.values
        .where((screen) => screen != Screen.LOGIN && screen != Screen.MAIN_MENU) // Exclude LOGIN and MAIN_MENU itself
        .map((screen) => ListTile(
              title: Text(screen.toString().split('.').last),
              onTap: () => onScreenSelected(screen),
            ))
        .toList();

    // Adding a Logout button as the last item
    menuItems.add(
      ListTile(
        title: Text('Logout'),
        onTap: onLogout,
      ),
    );

    return Scaffold(
      body: ListView(children: menuItems),
    );
  }
}


class DefaultScreen extends StatelessWidget {
  final VoidCallback onBack;

  DefaultScreen({required this.onBack});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Unimplemented Screen"),
        leading: IconButton(icon: Icon(Icons.arrow_back), onPressed: onBack),
      ),
      body: Center(child: Text("This screen has not been implemented yet.")),
    );
  }
}
