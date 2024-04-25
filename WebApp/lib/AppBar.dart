import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'LoginScreen.dart'; 
import 'theme_data.dart'; 
import 'main.dart'; 

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final Color backgroundColor;
  final Color titleColor;
  final double titleFontSize;
  final double iconSize;
  final VoidCallback? onBack;
  final List<Widget>? additionalActions;  

  const CustomAppBar({
    Key? key,
    required this.title,
    this.backgroundColor = Colors.blue,
    this.titleColor = Colors.white,
    this.titleFontSize = 20,
    this.iconSize = 30,
    this.onBack,
    this.additionalActions,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    IconData darkModeIcon = ThemeManager.isDarkMode ? Icons.wb_sunny : Icons.nightlight_round;

    // Create a list of default actions
    List<Widget> actions = [
      // Insert additional actions before default ones
      if (additionalActions != null) ...additionalActions!,
      IconButton(
        icon: Icon(darkModeIcon, size: iconSize, color: titleColor),
        onPressed: () {
          ThemeManager.toggleTheme();
          MyApp.restartApp(context);
        },
      ),
      IconButton(
        icon: Icon(Icons.logout, size: iconSize, color: Color(0xFFFF8A65)),
        onPressed: () => _logout(context),
      ),
    ];

    return AppBar(
      title: Text(
        title,
        style: TextStyle(
          fontSize: titleFontSize,
          color: titleColor,
          fontWeight: FontWeight.normal,
        ),
      ),
      backgroundColor: backgroundColor,
      automaticallyImplyLeading: false,
      leading: onBack != null
          ? IconButton(
              icon: Icon(Icons.arrow_back, size: iconSize, color: titleColor),
              onPressed: onBack,
            )
          : null,
      actions: actions,
    );
  }

  void _logout(BuildContext context) {
    FirebaseAuth.instance.signOut().then((_) {
      Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (context) => LoginScreen()),
        (Route<dynamic> route) => false,
      );
    });
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
