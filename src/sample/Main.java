package sample;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

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

        Circle circle = new Circle();
        Group root = circle.show();
        circle.move(root);

        Square square = new Square();
        Group root2 = square.show(100.0f,150.0f,100.0f,50.0f);
        square.move(root2);

        start.setOnAction(e->primaryStage.setScene(game));

        VBox layout1 = new VBox(50);
        layout1.getChildren().addAll(label1,start,root,root2);
        menu = new Scene(layout1,300,500);

        Button exit = new Button("Exit");
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

class Game extends Main{}

class Obstacles extends Game{}

class Rotating extends Obstacles{

    public void move(Group root){
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(1000);
        rotate.setDuration(Duration.millis(2000));
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setNode(root);
        rotate.play();
    }
}

class Square extends Rotating{
    public Group show(float x,float y,float length,float breadth){

        Line line1 = new Line(x,y,x+length,y);
        line1.setStrokeWidth(10);
        line1.setStroke(Color.RED);

        Line line2 = new Line(x,y+breadth,x+length,y+breadth);
        line2.setStrokeWidth(10);
        line2.setStroke(Color.GREEN);

        Line line3 = new Line(x,y,x,y+breadth);
        line3.setStrokeWidth(10);
        line3.setStroke(Color.PINK);

        Line line4 = new Line(x+length,y,x+length,y+breadth);
        line4.setStrokeWidth(10);
        line4.setStroke(Color.ORANGE);

        Group root = new Group(line1,line2,line3,line4);
        return root;
    }
}

class Circle extends Rotating{
    public Group show(){
        Arc arc1o = new Arc(300.0f,100.0f,50.0f,50.0f,0.0f,90.0f);
        Arc arc1i = new Arc(300.0f,100.0f,25.0f,25.0f,0.0f,90.0f);
        arc1o.setType(ArcType.ROUND);
        arc1i.setType(ArcType.ROUND);

        Shape ring = Shape.subtract(arc1o,arc1i);
        ring.setFill(Color.ORANGE);
        arc1i.setFill(null);

        Arc arc2o = new Arc(300.0f,100.0f,50.0f,50.0f,90.0f,90.0f);
        Arc arc2i = new Arc(300.0f,100.0f,25.0f,25.0f,90.0f,90.0f);
        arc2o.setType(ArcType.ROUND);
        arc2i.setType(ArcType.ROUND);

        Shape ring2 = Shape.subtract(arc2o,arc2i);
        ring2.setFill(Color.YELLOW);
        arc2i.setFill(null);

        Arc arc3o = new Arc(300.0f,100.0f,50.0f,50.0f,180.0f,90.0f);
        Arc arc3i = new Arc(300.0f,100.0f,25.0f,25.0f,180.0f,90.0f);
        arc3o.setType(ArcType.ROUND);
        arc3i.setType(ArcType.ROUND);

        Shape ring3 = Shape.subtract(arc3o,arc3i);
        ring3.setFill(Color.GREEN);
        arc3i.setFill(null);

        Arc arc4o = new Arc(300.0f,100.0f,50.0f,50.0f,270.0f,90.0f);
        Arc arc4i = new Arc(300.0f,100.0f,25.0f,25.0f,270.0f,90.0f);
        arc4o.setType(ArcType.ROUND);
        arc4i.setType(ArcType.ROUND);

        Shape ring4 = Shape.subtract(arc4o,arc4i);
        ring4.setFill(Color.PINK);
        arc4i.setFill(null);

        Group root = new Group(ring,ring2,ring3,ring4);
        return root;
    }
}