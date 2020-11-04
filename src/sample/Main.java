package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application {
    
    Button start;
    Button exit;
    Scene menu,game;


    @Override
    public void start(Stage primaryStage) throws Exception

    {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Label label1 = new Label("COLOR GAME");
        Button start = new Button("Start");
        start.setOnAction(e->primaryStage.setScene(game));

        VBox layout1 = new VBox(50);
        layout1.getChildren().addAll(label1,start);
        menu = new Scene(layout1,300,500);

        Button exit = new Button("Back to main menu");
        exit.setOnAction(e->primaryStage.setScene(menu));

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(exit);
        game = new Scene(layout2,300,500);

        primaryStage.setScene(menu);
        primaryStage.setTitle("Color Switch");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
