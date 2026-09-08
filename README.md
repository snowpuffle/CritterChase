## HOW TO RUN ##

>> set FX=C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib
>> javac --module-path "C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib" --add-modules javafx.controls,javafx.fxml -d bin src\Main.java src\controllers\*.java src\models\*.java src\models\entities\*.java src\models\levels\*.java src\models\objects\*.java src\models\utils\*.java
>> java --module-path "C:\Users\katie\Downloads\Katie\Projects\JavaFX\javafx-sdk-21.0.12\lib" --add-modules javafx.controls,javafx.fxml -cp "bin" Main