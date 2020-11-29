package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Player P = new Player();
        P.start(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}

class Player{
    public void start(Stage primaryStage) throws IOException{
        Game G = new Game();
        G.start(primaryStage);
    }
}

class Game extends Main{
    private Scene playerDetails,menu,game,titleScreen,splashScreen,mainMenu,enterName,play,resumeScreen,prizes;
    public void start(Stage primaryStage) throws IOException{
        //start new game
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Label label1 = new Label("COLOR GAME");
        Button start = new Button("Start");

        Arrow backButton = new Arrow();
        Group button = backButton.show();
        StackPane testPane = new StackPane(button);
        testPane.setStyle("-fx-background-color: #282828");

        VBox layout1 = new VBox(50);
        layout1.getChildren().addAll(label1,start);
        layout1.setStyle("-fx-background-color: #282828");
        menu = new Scene(layout1,300,500);

        Button exit = new Button("Exit");
        exit.setOnAction(e->primaryStage.setScene(menu));

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(exit);
        layout1.setStyle("-fx-background-color: #282828");
        game = new Scene(layout2,300,500);
        start.setOnAction(e->primaryStage.setScene(game));


        prizes = prize(primaryStage);


        playerDetails = playerDetails(primaryStage);

        mainMenu = mainMenu(primaryStage, prizes, playerDetails);

        enterName = enterName(mainMenu, primaryStage);

        splashScreen = splashScreen();

        pauseTransition(primaryStage,enterName,6);

        titleScreen = titleImage();
        pauseTransition(primaryStage,splashScreen,2);

        primaryStage.setScene(titleScreen);
        primaryStage.setTitle("Color Switch");
        primaryStage.show();
    }

    private Scene playerDetails(Stage primaryStage) throws IOException{
        Text name = new Text("Agrim Chopra");
        Text highScore = new Text("High Score:");
        Text highScoreNo = new Text("51");
        Text stars = new Text("Stars");
        Text starsno = new Text("100");
        Text diamonds = new Text("Diamonds");
        Text diamondsno = new Text("40");

        name.getStyleClass().add("title-text");
        highScore.getStyleClass().add("white-text");
        stars.getStyleClass().add("white-text");
        diamonds.getStyleClass().add("white-text");
        starsno.getStyleClass().add("white-text");
        diamondsno.getStyleClass().add("white-text");
        highScoreNo.getStyleClass().add("white-text");

        Star star = new Star();
        Group starImage = star.show();
        starImage.relocate(132,285);
        star.blink(starImage);

        Diamond diamond = new Diamond();
        Group dia = diamond.show();
        dia.relocate(135,70);
        diamond.blink(dia);

        Arrow backButton = new Arrow();
        Group backbtn = backButton.show();
        backbtn.setOnMouseClicked(mouseEvent -> primaryStage.setScene(mainMenu));

        Image image = new Image(new FileInputStream("src/images/trophy.png"));
        ImageView Image = new ImageView(image);
        Image.setFitWidth(40);
        Image.setPreserveRatio(true);

        Image iconImage = new Image(new FileInputStream("src/images/staricon.png"));
        ImageView icon = new ImageView(iconImage);
        icon.setFitWidth(38);
        icon.setPreserveRatio(true);

        Circle circle = new Circle(150.0f, 150.0f, 20.f);
        Circle circle2 = new Circle(150.0f,150.0f,18.0f);
        Shape ring =Shape.subtract(circle,circle2);
        ring.setFill(Color.valueOf("#fff"));
        circle2.setFill(null);
        Group ringg = new Group(ring);

        Line line = new Line(0,0,300,0);
        line.setStrokeWidth(140);
        line.setStroke(Color.valueOf("#ff3333"));
        HBox hbox = new HBox();
        for(int i=0;i<18;i++){
            hbox.getChildren().add(new Circle(300.0f,100.0f,10.f,Color.valueOf("#ff3333")));
        }

        Text headerText = new Text("STATS");
        headerText.setFont(new Font(27));
        headerText.setStyle("-fx-fill: #f7f7f7");
        headerText.relocate(20,22);
        Pane pane = new Pane(line,hbox,name,highScore,stars,diamonds,starImage,dia,starsno,diamondsno,Image,highScoreNo,ringg,icon,backbtn);
        pane.setStyle("-fx-background-color: #282828");
        pane.getChildren().add(headerText);
        hbox.relocate(0,60);

        ringg.relocate(115,20);
        icon.relocate(115,20);
        name.relocate(63,115);
        highScore.relocate(100,200);
        highScoreNo.relocate(200,200);
        Image.relocate(50,180);
        stars.relocate(40,334);
        diamonds.relocate(180,334);
        starImage.relocate(40,280);
        dia.relocate(210,280);
        starsno.relocate(42,360);
        diamondsno.relocate(210,360);
        backbtn.relocate(5,5);

        Scene scene = new Scene(pane,300,500);
        scene.getStylesheets().add("Theme.css");
        return scene;
    }

    private Scene prize (Stage primaryStage)throws IOException{
        Line line = new Line(0,0,300,0);
        line.setStrokeWidth(140);
        line.setStroke(Color.valueOf("#ff9900"));
        HBox hbox = new HBox();
        for(int i=0;i<18;i++){
            hbox.getChildren().add(new Circle(300.0f,100.0f,10.f,Color.valueOf("#ff9900")));
        }

        Text headerText = new Text("PRIZES");
        headerText.setFont(new Font(27));
        headerText.setStyle("-fx-fill: #f7f7f7");
        headerText.relocate(20,22);
        hbox.relocate(0,60);

        Image image = new Image(new FileInputStream("src/images/gift.png"));
        ImageView Image = new ImageView(image);
        Image.setFitWidth(250);
        Image.setPreserveRatio(true);

        Image image2 = new Image(new FileInputStream("src/images/yay.png"));
        ImageView Image2 = new ImageView(image2);
        Image2.setFitWidth(150);
        Image2.setPreserveRatio(true);
        Image2.setVisible(false);

        Arrow backButton = new Arrow();
        Group backbtn = backButton.show();
        backbtn.setOnMouseClicked(mouseEvent -> primaryStage.setScene(mainMenu));

        Text text = new Text("Click on the gift image to \nunlock today's prize.");
        text.setFill(Color.valueOf("#fff"));
        text.setFont(Font.font ("Verdana", 18));

        Text text2 = new Text("Congrats!\nYou won 10 Stars.");
        text2.setFill(Color.valueOf("#fff"));
        text2.setFont(Font.font("Verdana",17));
        text2.setVisible(false);

        Image iconImage = new Image(new FileInputStream("src/images/gifticon.png"));
        ImageView icon = new ImageView(iconImage);
        icon.setFitWidth(38);
        icon.setPreserveRatio(true);

        Circle circle = new Circle(150.0f, 150.0f, 20.f);
        Circle circle2 = new Circle(150.0f,150.0f,18.0f);
        Shape ring =Shape.subtract(circle,circle2);
        ring.setFill(Color.valueOf("#fff"));
        circle2.setFill(null);
        Group ringg = new Group(ring);

        Image.setOnMouseClicked(mouseEvent -> {
            System.out.println("image clicked");

            RotateTransition rotate = new RotateTransition();
            rotate.setAxis(Rotate.Z_AXIS);
            rotate.setByAngle(360);
            rotate.setCycleCount(1);
            rotate.setDuration(Duration.millis(2000));
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setNode(Image);
            rotate.play();

            rotate.setOnFinished(event -> {
                text2.setVisible(true);
                text.setVisible(false);
                Image2.setVisible(true);
            });

            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setDuration(Duration.millis(2000));
            scaleTransition.setByY(-1);
            scaleTransition.setByX(-1);
            scaleTransition.setNode(Image);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(false);
            scaleTransition.play();

        });

        Pane pane = new Pane(line,hbox,headerText,text,Image,text2,ringg,icon,Image2,backbtn);
        pane.setStyle("-fx-background-color: #282828");
        text.relocate(30,100);
        text2.relocate(20,260);
        Image.relocate(20,200);
        ringg.relocate(115,20);
        icon.relocate(115,20);
        Image2.relocate(140,170);
        backbtn.relocate(5,5);

        return new Scene(pane,300,500);
    }

    private void resume(Stage primaryStage){
        Line line = new Line(0,0,300,0);
        line.setStrokeWidth(140);
        line.setStroke(Color.valueOf("#e53e7b"));
        HBox hbox = new HBox();
        for(int i=0;i<18;i++){
            hbox.getChildren().add(new Circle(300.0f,100.0f,10.f,Color.valueOf("#e53e7b")));
        }

        Text headerText = new Text("SAVED GAMES");
        headerText.setFont(new Font(27));
        headerText.setStyle("-fx-fill: #f7f7f7");
        headerText.relocate(20,22);

        Arrow backButton = new Arrow();
        Group backbtn = backButton.show();
        backbtn.setOnMouseClicked(mouseEvent -> primaryStage.setScene(mainMenu));

        Pane pane = new Pane(line,hbox,headerText,backbtn);
        pane.setStyle("-fx-background-color: #282828");
        hbox.relocate(0,60);
        backbtn.relocate(5,5);
        Scene resumeScene =  new Scene(pane,300,500);
        primaryStage.setScene(resumeScene);
    }

    private void play(Stage primaryStage) throws IOException{


        double ballx = 150;
        AtomicReference<Double> bally = new AtomicReference<>((double) 450);
        Ball ball = new Ball();
        Circle b = ball.show();
        b.setCenterX(ballx);
        b.setCenterY(bally.get());
        Pane pane = new Pane();
        pane.getChildren().add(b);

        class GravityTimer extends AnimationTimer{
            @Override
            public void handle(long now){
                b.setCenterY(b.getCenterY()+1.5);
                bally.set(b.getCenterY());
            }
        }

        AtomicInteger ballMemory = new AtomicInteger(0);
        class MoveBall extends AnimationTimer{
            @Override
            public void handle(long now){
                b.setCenterY(b.getCenterY()-6);
                if(b.getCenterY()<=ballMemory.get()-35){
                    bally.set(b.getCenterY());
                    this.stop();
                }
            }
        }

        Text pause = new Text("II");
        pause.relocate(10,480);
        pane.getChildren().add(pause);
        pause.setStyle("-fx-fill:white; -fx-font-size: 35px");
        pause.setOnMouseClicked(mouseEvent -> System.out.println("paused game"));

        Circular circle = new Circular();
        Group root = circle.show(150,300,70.0f,56.0f);
        circle.move(root,360);
        root.setLayoutX(80);
        root.setLayoutY(235);
        root.relocate(root.getLayoutX(),root.getLayoutY());
        pane.getChildren().add(root);

        Plus plus = new Plus();
        Group plusRoot = plus.show(200.0f,300.0f,70.0f);
        plus.move(plusRoot,360);

        Star star = new Star();
        Group starImage = star.show();
        starImage.relocate(132,285);
        star.blink(starImage);
//        pane.getChildren().add(starImage);

        ColorChanger colorChanger = new ColorChanger();
        Group changer = colorChanger.show(10,10);
        changer.relocate(137,180);
//        pane.getChildren().add(changer);

        HorizontalLine horizontalLine = new HorizontalLine();
        Group horizontal = horizontalLine.show(130.0f,75.0f);
        horizontalLine.moveLeft(horizontal);
        pane.getChildren().add(horizontal);

        Diamond diamond = new Diamond();
        Group dia = diamond.show();
        dia.relocate(135,70);
        diamond.blink(dia);
//        pane.getChildren().add(dia);


        VerticalLine verticalLine = new VerticalLine();
        Group root5[] = verticalLine.show();
        verticalLine.moveRight(root5[0]);
        verticalLine.moveLeft(root5[1]);
        HBox hbox = new HBox(root5[0],root5[1]);
        Group verticalRoot = new Group(hbox);

        Square square = new Square();
        Group squareRoot = square.show(100.0f,150.0f,110.0f,110.0f);
        square.move(squareRoot,360);

        AtomicReference<Group> obstacle1 = new AtomicReference<>(root);
        AtomicReference<Group> obstacle2 = new AtomicReference<>(horizontal);
        AtomicReference<Group> obstacle3 = new AtomicReference<>((Group) null);
        AtomicBoolean flag= new AtomicBoolean(true);
        AtomicReference<Group> memory = new AtomicReference<>(squareRoot);

        Group[] obstacles = new Group[5];
        obstacles[0] = root;
        obstacles[1] = horizontal;
        obstacles[2] = squareRoot;
        obstacles[3] = plusRoot;
        obstacles[4] = verticalRoot;
        AtomicInteger obstacleCounter = new AtomicInteger(3);
        AtomicBoolean firstMouse = new AtomicBoolean(true);
        AnimationTimer gravity = new GravityTimer();
        AnimationTimer moveBall = new MoveBall();

        //handle click
        pane.addEventHandler(MouseEvent.MOUSE_RELEASED,e->{
            if(firstMouse.get()){
                firstMouse.set(false);
                gravity.start();
            }
            gravity.stop();
            if(bally.get()>300){
                //move ball
                  ballMemory.set((int)b.getCenterY());
                  moveBall.start();
//                TODO: remove gravity on page close or pause.
            }
            else{
                //move ball
                ballMemory.set((int)b.getCenterY());
                moveBall.start();

                //move lowermost obstacle
                TranslateTransition translate = new TranslateTransition();
                translate.setByY(40);
                translate.setDuration(Duration.millis(300));
                translate.setNode(obstacle1.get());
                translate.play();

                //move middle obstacle
                TranslateTransition translate1 = new TranslateTransition();
                translate1.setByY(40);
                translate1.setDuration(Duration.millis(300));
                translate1.setNode(obstacle2.get());
                translate1.play();

                //check for updates in obstacles
                Bounds boundsInScreen = obstacle1.get().localToScreen(obstacle1.get().getBoundsInLocal());
                if(boundsInScreen.getMaxY()>=550 && flag.get()){
                    flag.set(false);
                    obstacle3.set(memory.get());
                    obstacle3.get().setTranslateY(-50);
                    obstacle3.get().relocate(90, -80);
                    pane.getChildren().add(obstacle3.get());
                }
                if(boundsInScreen.getMinY()>=650 && !flag.get()){
                        flag.set(true);
                        pane.getChildren().remove(obstacle1.get());
                        memory.set(obstacles[obstacleCounter.get()]);
                        obstacleCounter.set((obstacleCounter.get() + 1) % 5);
                        obstacle1.set(obstacle2.get());
                        obstacle2.set(obstacle3.get());
                        obstacle3.set(null);
                }
                if(obstacle3.get()!=null){
                    //move topmost obstacle
                    TranslateTransition translate2 = new TranslateTransition();
                    translate2.setByY(40);
                    translate2.setDuration(Duration.millis(300));
                    translate2.setNode(obstacle3.get());
                    translate2.play();
                }
            }
            gravity.start();
        });

        pane.setStyle("-fx-background-color: #282828");
        Scene startScene = new Scene(pane,300,500);
        primaryStage.setScene(startScene);
    }

    private Scene enterName(Scene mainMenu, Stage primaryStage) throws IOException{
        Text text = new Text("Enter Name");
        text.setId("text");
        TextField name = new TextField();
        name.setId("textField");
        name.setMinSize(180,40);
        name.setAlignment(Pos.CENTER);
        Button next = new Button("NEXT");
        next.setId("nextBtn");
        next.setOnAction(e->primaryStage.setScene(mainMenu));
        Image image = new Image(new FileInputStream("src/images/ShortTitleImage.jpg"));
        ImageView titleImage = new ImageView(image);
        titleImage.setFitWidth(250);
        titleImage.setPreserveRatio(true);

        GridPane grid = new GridPane();
        grid.setMinSize(300, 500);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(20);
        grid.setHalignment(text, HPos.CENTER);
        grid.setHalignment(next, HPos.CENTER);
        grid.add(titleImage,1,3);
        grid.add(text, 1, 13);
        grid.add(name, 1, 17);
        grid.add(next,1,21);
        grid.setStyle("-fx-background-color: #282828");
        Scene scene =  new Scene(grid,300,500);
        scene.getStylesheets().add("Theme.css");
        return scene;
    }

    private Scene mainMenu(Stage primaryStage, Scene prizes, Scene playerDetails) throws IOException{
            Group root = circleAnimation(primaryStage);
            Button start = new Button("START");
            Button resume = new Button("RESUME");
            Button exit = new Button("EXIT");

            root.setOnMouseClicked(mouseEvent -> {
                try {
                    this.play(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Image iconImage = new Image(new FileInputStream("src/images/staricon2.png"));
            ImageView icon = new ImageView(iconImage);
            icon.setFitWidth(38);
            icon.setPreserveRatio(true);
            icon.setOnMouseClicked(mouseEvent -> primaryStage.setScene(playerDetails));

            Image giftImage = new Image(new FileInputStream("src/images/gifticon.png"));
            ImageView icon2 = new ImageView(giftImage);
            icon2.setFitWidth(46);
            icon2.setPreserveRatio(true);
            icon2.setOnMouseClicked(mouseEvent -> primaryStage.setScene(prizes));
            Circle circle = new Circle(150.0f, 150.0f, 23.f);
            circle.setFill(Color.valueOf("#fff"));
            circle.setOnMouseClicked(mouseEvent -> primaryStage.setScene(playerDetails));
            circle.setId("circle-yellow");

            Circle circle2 = new Circle(150.0f, 150.0f, 23.f);
            circle2.setFill(Color.valueOf("#fff"));
            circle2.setId("circle-pink");
            circle2.setOnMouseClicked(mouseEvent -> primaryStage.setScene(prizes));

            Pane pane = new Pane(root,start,resume,exit,circle,icon,circle2,icon2);
            pane.setStyle("-fx-background-color: #282828");
            root.relocate(30,40);
            start.relocate(80,300);
            resume.relocate(80,350);
            exit.relocate(80,400);
            circle.relocate(240,350);
            icon.relocate(243,353);
            circle2.relocate(20,350);
            icon2.relocate(20,350);


            Scene scene = new Scene(pane,300,500);
            start.getStyleClass().add("button");
    //        start.setOnAction(e->primaryStage.setScene(startScreen));
            start.setOnAction(e-> {
                try {
                    this.play(primaryStage);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            resume.getStyleClass().add("button");
            resume.setOnAction(e-> this.resume(primaryStage));
            exit.getStyleClass().add("button");
            exit.setOnAction(e->System.exit(1));
            scene.getStylesheets().add("Theme.css");
            return scene;
    }

    private Group circleAnimation(Stage primaryStage) throws FileNotFoundException,IOException {
        Circular outer = new Circular();
        Group root = outer.show(300.0f,100.0f,120.0f,100.0f);
        outer.move(root,360);
        Circular middle = new Circular();
        Group root2 = middle.show(300.0f,100.0f,95.0f,80.0f);
        middle.move(root2,-360);
        Circular inner = new Circular();
        Group root3 = inner.show(300.0f,100.0f,75.0f,60.0f);
        inner.move(root3,360);
        Image image = new Image(new FileInputStream("src/images/Triangle.jpg"));
        ImageView triangleImage = new ImageView(image);
        triangleImage.setFitWidth(70);
        triangleImage.setX(270);
        triangleImage.setY(64);
        triangleImage.setPreserveRatio(true);
        Circle circle = new Circle(300.0f,100.0f,55.f,Color.valueOf("#585858"));
        return new Group(root,root2,root3,circle,triangleImage);
    }

    private void pauseTransition(Stage primaryStage,Scene nextScene,int time){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(time));
        pauseTransition.setOnFinished( event -> primaryStage.setScene(nextScene) );
        pauseTransition.play();
    }

    private Scene splashScreen(){
        Circular circle = new Circular();
        Group root = circle.show(30.0f,50.0f,70.0f,56.0f);
        root.setLayoutX(121);
        root.setLayoutY(140);
        circle.move(root,360);
        Ball ball = new Ball();
        Circle b = ball.show();
        b.setCenterX(150);
        b.setCenterY(450);
        Pane layout = new Pane();
        layout.getChildren().add(root);
        layout.getChildren().add(b);
        layout.setStyle("-fx-background-color: #292929");
        Path path = new Path();
        path.getElements().add(new MoveTo(b.getCenterX(), b.getCenterY()));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), b.getCenterY(), b.getCenterX(), 120, b.getCenterX(), 450));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), b.getCenterY(), b.getCenterX(), 120, b.getCenterX(), 450));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), b.getCenterY(), b.getCenterX(), 120, b.getCenterX(), 450));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), b.getCenterY(), b.getCenterX(), 450, b.getCenterX(), 450));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), b.getCenterY(), b.getCenterX(), 300, b.getCenterX(), 450));
        path.getElements().add(new CubicCurveTo(b.getCenterX(), 450, b.getCenterX(), 187, b.getCenterX(), 187));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(b);
        pathTransition.play();
        return new Scene(layout,300,500);
    }

    private Scene titleImage() throws IOException{
        Image image = new Image(new FileInputStream("src/images/TitleImage.jpg"));
        ImageView titleImage = new ImageView(image);
        titleImage.setFitWidth(300);
        titleImage.setPreserveRatio(true);
        StackPane layout = new StackPane();
        layout.getChildren().add(titleImage);
        layout.setStyle("-fx-background-color: #292929");
        return new Scene(layout,300,500);
    }
}

class Ball extends Game{
    Ball(){

    }
    public Circle show(){
        Circle circle = new Circle(10.f,Color.valueOf("#f7f7f7"));
        return circle;
    }
}

class Reward extends Game{
    public void blink(Group root){
        ScaleTransition st = new ScaleTransition(Duration.millis(1000),root);
         st.setByX(0.15f);
         st.setByY(0.15f);
         st.setCycleCount(100);
         st.setAutoReverse(true);
         st.play();
    }
}

class Star extends Reward{
    public Group show() throws IOException{
        Image image = new Image(new FileInputStream("src/images/star.jpg"));
        ImageView star = new ImageView(image);
        star.setFitWidth(35);
        star.setPreserveRatio(true);
        return new Group(star);
    }
}

class Diamond extends Reward{
    public Group show(){
        Rectangle dia = new Rectangle(50,50,20,20);
        dia.setArcWidth(3);
        dia.setArcHeight(2);
        dia.setFill(Color.valueOf("#f7f7f7"));
        Rotate rotate = new Rotate(52);
        Shear shear = new Shear();
        shear.setX(0.3);
        dia.getTransforms().addAll(rotate,shear);
        Group group = new Group(dia);
        return group;
    }
}

class Arrow extends Game{
    private final int strokeWidth;
    Arrow(){
        this.strokeWidth = 2;
    }
    public Group show(){
        Line line = new Line(10, 8, 30, 8);
        Line line2 = new Line(10,8,15,4);
        Line line3 = new Line(10,8,15,12);
        line.setStrokeWidth(strokeWidth);
        line.setStroke(Color.valueOf("#fff"));
        line2.setStrokeWidth(strokeWidth);
        line2.setStroke(Color.valueOf("#fff"));
        line3.setStrokeWidth(strokeWidth);
        line3.setStroke(Color.valueOf("#fff"));
        Group group = new Group(line,line2,line3);
        return group;
    }
}

class ColorChanger extends Game{
    private final float radius;
    ColorChanger(){
        this.radius = 13;
    }
    public Group show(float x,float y){
        float angle = 0.0f;
        Arc arcs[] = new Arc[4];

        Paint paint[] = {Color.valueOf("#e53e7b"),Color.valueOf("#8a49ef"),Color.valueOf("#eed948"),Color.valueOf("#5edcea")};
        for(int i=0;i<4;i++){
            arcs[i] = new Arc(x,y,radius,radius,angle,90.0f);

            arcs[i].setType(ArcType.ROUND);
            arcs[i].setFill(paint[i]);
            angle+=90.0f;
        }
        Group root = new Group(arcs[0],arcs[1],arcs[2],arcs[3]);
        return root;
    }
}

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
        translate.setDuration(Duration.millis(2000));
        translate.setCycleCount(500);
        translate.setAutoReverse(true);
        translate.setNode(root);
        translate.play();
    }

    public  void moveRight(Group root){
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(300);
        translate.setDuration(Duration.millis(2000));
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
        rectangle1.setFill(Color.valueOf("#e53e7b"));

        Rectangle rectangle2 = new Rectangle(180.0f,75.0f,12.0f,100.0f);
        rectangle2.setArcHeight(15.02);
        rectangle2.setArcWidth(30.02);
        rectangle2.setFill(Color.valueOf("#eed948"));

        Rectangle rectangle3 = new Rectangle(120,90.0f,8.0f,70.0f);
        rectangle3.setArcHeight(15.02);
        rectangle3.setArcWidth(30.02);
        rectangle3.setFill(Color.valueOf("#8a49ef"));

        Rectangle rectangle4 = new Rectangle(240,90.0f,8f,70.0f);
        rectangle4.setArcHeight(15.02);
        rectangle4.setArcWidth(30.02);
        rectangle4.setFill(Color.valueOf("#5edcea"));

        Rectangle rectangle5 = new Rectangle(40.0f,90.0f,8f,70.0f);
        rectangle5.setArcHeight(15.02);
        rectangle5.setArcWidth(30.02);
        rectangle5.setFill(Color.valueOf("#e53e7b"));

        Rectangle rectangle6 = new Rectangle(160.0f,90.0f,8f,70.0f);
        rectangle6.setArcHeight(15.02);
        rectangle6.setArcWidth(30.02);
        rectangle6.setFill(Color.valueOf("#8a49ef"));

        Rectangle rectangle7 = new Rectangle(100.0f,100.0f,6f,50.0f);
        rectangle7.setArcHeight(15.02);
        rectangle7.setArcWidth(30.02);
        rectangle7.setFill(Color.valueOf("#eed948"));

        Rectangle rectangle8 = new Rectangle(220.0f,100.0f,6f,50.0f);
        rectangle8.setArcHeight(15.02);
        rectangle8.setArcWidth(30.02);
        rectangle8.setFill(Color.valueOf("#5edcea"));

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
        Paint paint[] = {Color.valueOf("#e53e7b"),Color.valueOf("#8a49ef"),Color.valueOf("#eed948"),Color.valueOf("#5edcea")};

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

    public void move(Group root,int angle){
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(angle);
        rotate.setCycleCount(1000);
        rotate.setDuration(Duration.millis(3500));
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setNode(root);
        rotate.play();
    }
}

class Plus extends Rotating{
    private final int strokeWidth;

    Plus(){
        this.strokeWidth=12;
    }

    public Group show(float centerx,float centery,float length){
        Line line1 = new Line(centerx,centery-length,centerx,centery);
        line1.setStrokeWidth(strokeWidth);
        line1.setStroke(Color.valueOf("#e53e7b"));

        Line line2 = new Line(centerx,centery,centerx,centery+length);
        line2.setStrokeWidth(strokeWidth);
        line2.setStroke(Color.valueOf("#8a49ef"));

        Line line3 = new Line(centerx,centery,centerx+length,centery);
        line3.setStrokeWidth(strokeWidth);
        line3.setStroke(Color.valueOf("#eed948"));

        Line line4 = new Line(centerx-length,centery,centerx,centery);
        line4.setStrokeWidth(strokeWidth);
        line4.setStroke(Color.valueOf("#5edcea"));

        Group root = new Group(line1,line2,line3,line4);
        return root;
    }
}

class Square extends Rotating{
    private final int strokeWidth;

    Square(){
        this.strokeWidth=15;
    }
    public Group show(float x,float y,float length,float breadth){

        Line line1 = new Line(x,y,x+length,y);
        line1.setStrokeWidth(strokeWidth);
        line1.setStroke(Color.valueOf("#e53e7b"));

        Line line2 = new Line(x,y+breadth,x+length,y+breadth);
        line2.setStrokeWidth(strokeWidth);
        line2.setStroke(Color.valueOf("#8a49ef"));

        Line line3 = new Line(x,y,x,y+breadth);
        line3.setStrokeWidth(strokeWidth);
        line3.setStroke(Color.valueOf("#eed948"));

        Line line4 = new Line(x+length,y,x+length,y+breadth);
        line4.setStrokeWidth(strokeWidth);
        line4.setStroke(Color.valueOf("#5edcea"));

        Group root = new Group(line1,line2,line3,line4);
        return root;
    }
}

class Circular extends Rotating{
    public Group show(float x,float y,float radiusOuter,float radiusInner){
        float angle = 0.0f;
        Shape arcs[] = new Shape[4];

        Paint paint[] = {Color.valueOf("#e53e7b"),Color.valueOf("#8a49ef"),Color.valueOf("#eed948"),Color.valueOf("#5edcea")};
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