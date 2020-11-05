package sample;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

//        Circle circle = new Circle();
//        Group root = circle.show(300.0f,100.0f,50.0f,25.0f);
//        circle.move(root);
//
//        Square square = new Square();
//        Group root2 = square.show(100.0f,150.0f,100.0f,50.0f);
//        square.move(root2);
//
//        Plus plus = new Plus();
//        Group root3 = plus.show(200.0f,300.0f,50.0f);
//        plus.move(root3);
//
//        HorizontalLine horizontalLine = new HorizontalLine();
//        Group root4 = horizontalLine.show(10.0f,75.0f);
//        horizontalLine.move(root4);

        VerticalLine verticalLine = new VerticalLine();
        Group root5[] = verticalLine.show();
        verticalLine.moveRight(root5[0]);
        verticalLine.moveLeft(root5[1]);
        StackPane stack = new StackPane(root5[0],root5[1]);
//        HBox hbox = new HBox(root5[0],root5[1]);

        start.setOnAction(e->primaryStage.setScene(game));

        VBox layout1 = new VBox(50);
        layout1.getChildren().addAll(label1,start,stack);
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

class Linear extends Obstacles{
    public void move(Group root){
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-600);
        translate.setDuration(Duration.millis(3000));
        translate.setCycleCount(500);
        translate.setAutoReverse(true);
        translate.setNode(root);
        translate.play();
    }

    public void moveLeft(Group root){
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-300);
        translate.setDuration(Duration.millis(3000));
        translate.setCycleCount(500);
        translate.setAutoReverse(true);
        translate.setNode(root);
        translate.play();
    }

    public  void moveRight(Group root){
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(300);
        translate.setDuration(Duration.millis(3000));
        translate.setCycleCount(500);
        translate.setAutoReverse(true);
        translate.setNode(root);
        translate.play();
    }
}

class VerticalLine extends Linear{
    private final int strokeWidth;

    VerticalLine(){
        this.strokeWidth=10;
    }

    public Group[] show(){
        Rectangle rectangle1 = new Rectangle(60.0f,75.0f,12.0f,100.0f);
        rectangle1.setArcHeight(15.02);
        rectangle1.setArcWidth(30.02);
        rectangle1.setFill(Color.PINK);

        Rectangle rectangle2 = new Rectangle(180.0f,75.0f,12.0f,100.0f);
        rectangle2.setArcHeight(15.02);
        rectangle2.setArcWidth(30.02);
        rectangle2.setFill(Color.YELLOW);

        Rectangle rectangle3 = new Rectangle(120,90.0f,8.0f,70.0f);
        rectangle3.setArcHeight(15.02);
        rectangle3.setArcWidth(30.02);
        rectangle3.setFill(Color.PURPLE);

        Rectangle rectangle4 = new Rectangle(240,90.0f,8f,70.0f);
        rectangle4.setArcHeight(15.02);
        rectangle4.setArcWidth(30.02);
        rectangle4.setFill(Color.BLUE);

        Rectangle rectangle5 = new Rectangle(40.0f,90.0f,8f,70.0f);
        rectangle5.setArcHeight(15.02);
        rectangle5.setArcWidth(30.02);
        rectangle5.setFill(Color.PINK);

        Rectangle rectangle6 = new Rectangle(160.0f,90.0f,8f,70.0f);
        rectangle6.setArcHeight(15.02);
        rectangle6.setArcWidth(30.02);
        rectangle6.setFill(Color.PURPLE);

        Rectangle rectangle7 = new Rectangle(100.0f,100.0f,6f,50.0f);
        rectangle7.setArcHeight(15.02);
        rectangle7.setArcWidth(30.02);
        rectangle7.setFill(Color.YELLOW);

        Rectangle rectangle8 = new Rectangle(220.0f,100.0f,6f,50.0f);
        rectangle8.setArcHeight(15.02);
        rectangle8.setArcWidth(30.02);
        rectangle8.setFill(Color.BLUE);

        Group root1 = new Group(rectangle1,rectangle2,rectangle3,rectangle4);
        Group root2 = new Group(rectangle5,rectangle6,rectangle7,rectangle8);
        Group arr[] ={root1,root2};
        return arr;
    }
}

class HorizontalLine extends Linear{
    private final int strokeWidth;

    HorizontalLine(){
        this.strokeWidth=10;
    }

    public Group show(float y,float len){

        Line lines[] = new Line[12];
        Paint paint[] = {Color.LIGHTBLUE,Color.PINK,Color.YELLOW,Color.PURPLE};

        for(int i=-4;i<8;i++){
            lines[i+4] = new Line(i*len,y,(i+1)*len,y);
            lines[i+4].setStrokeWidth(strokeWidth);
            lines[i+4].setStroke(paint[Math.abs(i%4)]);
        }

        Group root = new Group(lines[0],lines[1],lines[2],lines[3],lines[4],lines[5],lines[6],lines[7],lines[8],lines[9],lines[10],lines[11]);
        return root;
    }
}

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

class Plus extends Rotating{
    private final int strokeWidth;

    Plus(){
        this.strokeWidth=10;
    }

    public Group show(float centerx,float centery,float length){
        Line line1 = new Line(centerx,centery-length,centerx,centery);
        line1.setStrokeWidth(strokeWidth);
        line1.setStroke(Color.RED);

        Line line2 = new Line(centerx,centery,centerx,centery+length);
        line2.setStrokeWidth(strokeWidth);
        line2.setStroke(Color.GREEN);

        Line line3 = new Line(centerx,centery,centerx+length,centery);
        line3.setStrokeWidth(strokeWidth);
        line3.setStroke(Color.PINK);

        Line line4 = new Line(centerx-length,centery,centerx,centery);
        line4.setStrokeWidth(strokeWidth);
        line4.setStroke(Color.ORANGE);

        Group root = new Group(line1,line2,line3,line4);
        return root;
    }
}

class Square extends Rotating{
    private final int strokeWidth;

    Square(){
        this.strokeWidth=10;
    }
    public Group show(float x,float y,float length,float breadth){

        Line line1 = new Line(x,y,x+length,y);
        line1.setStrokeWidth(strokeWidth);
        line1.setStroke(Color.RED);

        Line line2 = new Line(x,y+breadth,x+length,y+breadth);
        line2.setStrokeWidth(strokeWidth);
        line2.setStroke(Color.GREEN);

        Line line3 = new Line(x,y,x,y+breadth);
        line3.setStrokeWidth(strokeWidth);
        line3.setStroke(Color.PINK);

        Line line4 = new Line(x+length,y,x+length,y+breadth);
        line4.setStrokeWidth(strokeWidth);
        line4.setStroke(Color.ORANGE);

        Group root = new Group(line1,line2,line3,line4);
        return root;
    }
}

class Circle extends Rotating{
    public Group show(float x,float y,float radiusOuter,float radiusInner){
        float angle = 0.0f;
        Shape arcs[] = new Shape[4];

        Paint paint[] = {Color.PINK,Color.RED,Color.GREEN,Color.BLACK};
        for(int i=0;i<4;i++){
            Arc arc1o = new Arc(x,y,radiusOuter,radiusOuter,angle,90.0f);
            Arc arc1i = new Arc(x,y,radiusInner,radiusInner,angle,90.0f);
            arc1o.setType(ArcType.ROUND);
            arc1i.setType(ArcType.ROUND);
            arcs[i] = Shape.subtract(arc1o,arc1i);
            arcs[i].setFill(paint[i]);
            arc1i.setFill(null);
            angle+=90.0f;
        }
        Group root = new Group(arcs[0],arcs[1],arcs[2],arcs[3]);
        return root;
    }
}