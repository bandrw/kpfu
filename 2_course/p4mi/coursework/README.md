compile:
`javac -d out --module-path src/lib/javafx --add-modules javafx.controls,javafx.fxml src/*.java`
`jar -c -f Conference.jar --main-class Main -C out Main.class`

run: `java -cp out/production/coursework:out/production/coursework/lib/mysql/mysql-connector-java-8.0.22.jar --module-path ~/Desktop/javafx/lib --add-modules javafx.controls,javafx.fxml Main`
