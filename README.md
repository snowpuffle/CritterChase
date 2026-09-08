## HOW TO RUN ##

>> set FX=C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib
>> javac --module-path "C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib" --add-modules javafx.controls,javafx.fxml -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
>> java --module-path "C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib" --add-modules javafx.controls,javafx.fxml -cp "bin" Main