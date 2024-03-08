If you want to compile in terminal, simply:

cd [root directory, aka this folder]

flutter run -d chrome 

"chrome" is whatever browser you want to use.
I don't know the exact names for other browsers flutter uses, but this one 
should work if you have chrome installed.

You may want to compile in terminal before in IDE in order to generate the
flutter files/folders. Not sure if it generates the same way in IDE compilation.

Technically the only files you should need to compile this webapp are:

lib folder (has all of the desktop app's main code inside)
Analysis_options.yaml
google-services.json
pubspec.lock
pubspec.yaml

Flutter should generate all remaining necessary files on compilation

Compiling in terminal also assumes you have Flutter SDK installed on your
machine and is in your environmental variable paths.