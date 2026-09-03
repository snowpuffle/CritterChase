## HOW TO RUN ##

>> set FX=C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib
>> javac -d bin --module-path "%FX%" --add-modules javafx.controls,javafx.fxml src\Main.java src\controllers\*.java
>> java -cp "bin;src" --module-path "%FX%" --add-modules javafx.controls,javafx.fxml Main