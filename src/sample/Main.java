package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application implements EventHandler<ActionEvent> {

    Button buttonNewgame;

    @Override
    public void start(Stage primaryStage) throws Exception

    {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Color Switch");
        buttonNewgame = new Button("Start");
        buttonNewgame.setOnAction(this);


        StackPane layout = new StackPane();
        layout.getChildren().add(buttonNewgame);

        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==buttonNewgame){
            System.out.println("New game button pressed");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
