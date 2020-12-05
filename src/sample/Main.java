package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application implements Serializable{
    protected Scene playerDetails,titleScreen,splashScreen,mainMenu,enterName,prizes,shop;
    private Player player;
    private HashMap<String,Player> players;

    @Override
    public void start(Stage primaryStage) throws Exception{
        enterName = enterName(primaryStage);

        splashScreen = splashScreen();
        pauseTransition(primaryStage,enterName,6);

        titleScreen = titleImage();
        pauseTransition(primaryStage,splashScreen,2);

        primaryStage.setScene(titleScreen);
        primaryStage.setTitle("Color Switch");
        primaryStage.show();
    }

    private void pauseTransition(Stage primaryStage,Scene nextScene,int time){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(time));
        pauseTransition.setOnFinished( event -> primaryStage.setScene(nextScene) );
        pauseTransition.play();
    }

    private void serialize() throws IOException{
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("users.txt"));
            if(players!=null)
            out.writeObject(players);

        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void deserialize(String name) throws IOException,ClassNotFoundException{
        ObjectInputStream in = null;
        File file = new File("users.txt");
        if(file.length()==0) players = new HashMap<>();
        else{
            try{
                in = new ObjectInputStream(new FileInputStream("users.txt"));
                players = (HashMap<String,Player>) in.readObject();
            }
            finally {
                in.close();
            }
        }
        if(players.containsKey(name)) this.player = players.get(name);
        else{
            player = new Player(name);
            players.put(name,player);
            serialize();
        }
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

    private Scene playerDetails(Stage primaryStage) throws IOException{
        Text name = new Text(this.player.getName());
        Text highScore = new Text("High Score:");
        Text highScoreNo = new Text(Integer.toString(this.player.getHighScore())); //TODO update high score,stars and diamonds after game over
        Text stars = new Text("Stars");
        Text starsno = new Text(Integer.toString(this.player.getStars()));
        Text diamonds = new Text("Diamonds");
        Text diamondsno = new Text(Integer.toString(this.player.getDiamonds()));

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

    private Scene shop(Stage primaryStage)throws IOException{
        //TODO when gems are purchased, update in stats screen
        //TODO when ball purchased, change ball in game
        Text balls = new Text("Balls");
        balls.getStyleClass().add("title-text");

        Arrow backButton = new Arrow();
        Group backbtn = backButton.show();
        backbtn.setOnMouseClicked(mouseEvent -> primaryStage.setScene(mainMenu));

        DisplayImage displayImage = new DisplayImage();
        ImageView icon = displayImage.show("diamond.png",50);
        ImageView recruit = displayImage.show("gem1.png",50);
        ImageView corporal = displayImage.show("gem2.png",50);
        ImageView general = displayImage.show("gem3.png",50);

        Ball square = new Ball();
        Rectangle squareBall = square.showRectangle();
        Ball triangle = new Ball();
        Polygon triangleBall = triangle.showTriangle();

        Button generalBtn = new Button("Purchase for 50 Diamonds");
        Button corporalBtn = new Button("Purchase for 25 Diamonds");
        Button recruitBtn = new Button("Purchase for 10 Diamonds");
        Button squareBallBtn = new Button("Purchase for 30 Diamonds");
        Button triangleBallBtn = new Button("Purchase for 50 Diamonds");

        Line line = new Line(0,0,300,0);
        line.setStrokeWidth(140);
        line.setStroke(Color.valueOf("#ff3333"));
        HBox hbox = new HBox();
        for(int i=0;i<18;i++){
            hbox.getChildren().add(new Circle(300.0f,100.0f,10.f,Color.valueOf("#ff3333")));
        }

        Text headerText = new Text("SHOP");
        headerText.setFont(new Font(27));
        headerText.setStyle("-fx-fill: #f7f7f7");
        headerText.relocate(20,22);
        Pane pane = new Pane(line,hbox,icon,backbtn,headerText,recruit,corporal,general,generalBtn,corporalBtn,recruitBtn,squareBall,triangleBall,squareBallBtn,triangleBallBtn);
        pane.setStyle("-fx-background-color: #282828");
        hbox.relocate(0,60);

        icon.relocate(100,12);
        backbtn.relocate(5,5);
        recruit.relocate(25,100);
        corporal.relocate(25,160);
        general.relocate(25,220);
        generalBtn.relocate(100,120);
        corporalBtn.relocate(100,180);
        recruitBtn.relocate(100,240);
        squareBall.relocate(40,310);
        triangleBall.relocate(40,370);
        squareBallBtn.relocate(100,300);
        triangleBallBtn.relocate(100,360);

        recruitBtn.getStyleClass().add("purchase-btn");
        corporalBtn.getStyleClass().add("purchase-btn");
        generalBtn.getStyleClass().add("purchase-btn");
        squareBallBtn.getStyleClass().add("purchase-btn");
        triangleBallBtn.getStyleClass().add("purchase-btn");

        Scene scene = new Scene(pane,300,500);
        scene.getStylesheets().add("Theme.css");
        return scene;
    }

    private Scene enterName(Stage primaryStage) throws IOException,ClassNotFoundException{
        Text text = new Text("Enter Name");
        text.setId("text");
        TextField name = new TextField();
        name.setId("textField");
        name.setMinSize(180,40);
        name.setAlignment(Pos.CENTER);
        Button next = new Button("NEXT");
        next.setId("nextBtn");
        next.setOnAction(e->{
                    if(!name.getText().trim().equals("")){
                        try {
                            deserialize(name.getText().trim());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        try {
                            prizes = prize(primaryStage);
                            mainMenu = mainMenu(primaryStage, prizes,player);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        primaryStage.setScene(mainMenu);
                    }
                }
        );
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

    private Scene titleImage() throws IOException{
        Image image = new Image(new FileInputStream("src/images/TitleImage.jpg"));
        ImageView titleImage = new ImageView(image);
        titleImage.setFitWidth(300);
        titleImage.setPreserveRatio(true);
        StackPane layout = new StackPane();
        layout.getChildren().addAll(titleImage,playSound("mainMenuSound.mp3"));
        layout.setStyle("-fx-background-color: #292929");
        return new Scene(layout,300,500);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MediaView playSound(String filename){
        Media media = new Media(new File(filename).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        return mediaView;
    }

    protected Scene mainMenu(Stage primaryStage, Scene prizes,Player p) throws IOException{
        this.player = p;
        Group root = circleAnimation(primaryStage);
        Button start = new Button("START");
        Button resume = new Button("RESUME");
        Button exit = new Button("EXIT");

        root.setOnMouseClicked(mouseEvent -> {
            try {
                player.start(primaryStage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    serialize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Image iconImage = new Image(new FileInputStream("src/images/staricon2.png"));
        ImageView icon = new ImageView(iconImage);
        icon.setFitWidth(38);
        icon.setPreserveRatio(true);
        icon.setOnMouseClicked(mouseEvent -> {
            try {
                playerDetails = playerDetails(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(playerDetails);

        });

        Image giftImage = new Image(new FileInputStream("src/images/gifticon.png"));
        ImageView icon2 = new ImageView(giftImage);
        icon2.setFitWidth(46);
        icon2.setPreserveRatio(true);
        icon2.setOnMouseClicked(mouseEvent -> primaryStage.setScene(prizes));
        Circle circle = new Circle(150.0f, 150.0f, 23.f);
        circle.setFill(Color.valueOf("#fff"));
        circle.setOnMouseClicked(mouseEvent ->{
            try {
                playerDetails = playerDetails(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(playerDetails);}
        );
        circle.setId("circle-yellow");

        Image cartImage = new Image(new FileInputStream("src/images/diamond.png"));
        ImageView icon3 = new ImageView(cartImage);
        icon3.setFitWidth(46);
        icon3.setPreserveRatio(true);
        icon3.setOnMouseClicked(mouseEvent ->{
            try {
                shop = shop(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(shop);}
        );

        Circle circle2 = new Circle(150.0f, 150.0f, 23.f);
        circle2.setFill(Color.valueOf("#fff"));
        circle2.setId("circle-pink");
        circle2.setOnMouseClicked(mouseEvent -> primaryStage.setScene(prizes));

        Pane pane = new Pane(root,start,resume,exit,circle,icon,circle2,icon2,icon3);
        pane.setStyle("-fx-background-color: #282828");
        root.relocate(30,40);
        start.relocate(80,300);
        resume.relocate(80,350);
        exit.relocate(80,400);
        circle.relocate(240,350);
        icon.relocate(243,353);
        circle2.relocate(20,350);
        icon2.relocate(20,350);
        icon3.relocate(20,400);

        Scene scene = new Scene(pane,300,500);
        start.getStyleClass().add("btn");
        start.setOnAction(e-> {
            try {
                player.start(primaryStage);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
        resume.getStyleClass().add("btn");
        resume.setOnAction(e-> {
            try {
                player.savedGames(primaryStage,mainMenu);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        exit.getStyleClass().add("btn");
        exit.setOnAction(e->System.exit(1));
        scene.getStylesheets().add("Theme.css");
        return scene;
    }

    protected Scene prize (Stage primaryStage)throws IOException{
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

        int prizes[] = new int[]{10,100,50,15,75};
        int randomIndex =(int) (Math.random()*5);
        Text text2 = new Text("Congrats!\nYou won "+prizes[randomIndex]+" Stars.");
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
            player.incrementStars(prizes[randomIndex]);

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
}

class Player implements Serializable{
    private String name;
    private int stars;
    private int diamonds;
    private int highScore;
    private ArrayList<Game> savedGames;

    Player(String name){
        this.name = name;
        this.stars=0;
        this.diamonds=0;
        this.highScore=0;
        this.savedGames = new ArrayList<>();
    }
    public void start(Stage primaryStage) throws IOException,ClassNotFoundException{
        Game G = new Game(this);
        G.start(primaryStage);
    }

    public void incrementStars(int val){
        this.stars+=val;
    }

    public void incrementDiamonds(int val){
        this.diamonds+=val;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getStars() {
        return stars;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public ArrayList<Game> getSavedGames() {
        return savedGames;
    }

    public void savedGames(Stage primaryStage, Scene mainMenu)throws IOException{
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

        Pane pane = new Pane(line,hbox,headerText);
        pane.setStyle("-fx-background-color: #282828");
        hbox.relocate(0,60);
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setStyle("-fx-background: #282828");

        Scene resumeScene =  new Scene(scrollPane,300,500);
        primaryStage.setScene(resumeScene);
        resumeScene.getStylesheets().add("Theme.css");

        Arrow backButton = new Arrow();
        Group backbtn = backButton.show();
        backbtn.relocate(5,5);
        pane.getChildren().add(backbtn);
        backbtn.setOnMouseClicked(mouseEvent -> primaryStage.setScene(mainMenu));

        for(int i = 0;i<savedGames.size();i++){
            Text stars = new Text(Integer.toString(savedGames.get(i).getStars()));
            Text diamonds = new Text(Integer.toString(savedGames.get(i).getDiamonds()));
            Text score = new Text("Score "+Integer.toString(savedGames.get(i).getScore()));

            Image resumebtn = new Image(new FileInputStream("src/images/play.png"));
            ImageView resumeBtn = new ImageView(resumebtn);
            resumeBtn.setFitWidth(35);
            resumeBtn.setPreserveRatio(true);

            Star star = new Star();
            Group starImage = star.show();
            star.blink(starImage);

            Diamond diamond = new Diamond();
            Group dia = diamond.show();
            diamond.blink(dia);

            int y = 80*(i+2);

            stars.relocate(25,y);
            starImage.relocate(45,y-15);
            diamonds.relocate(90,y);
            dia.relocate(110,y-10);
            score.relocate(160,y);
            resumeBtn.relocate(240,y-10);
            pane.getChildren().addAll(stars,diamonds,score,resumeBtn,starImage,dia);
            stars.getStyleClass().add("white-text");
            diamonds.getStyleClass().add("white-text");
            score.getStyleClass().add("white-text");
        }
    }

}

class Game extends Main implements Serializable {
    private int stars;
    private int diamonds;
    private int score;
    private Player player;

    Game(Player p){
        this.stars=0;
        this.diamonds=0;
        this.score = 0;
        this.player=p;
    }

    public int getStars() {
        return stars;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public int getScore() {
        return score;
    }

    public void start(Stage primaryStage) throws IOException{
        double ballx = 150;
        AtomicReference<Double> bally = new AtomicReference<>((double) 450);
        AtomicInteger ballSpeed = new AtomicInteger(6);
        AtomicInteger ballDistance = new AtomicInteger(35);
        Ball ball = new Ball();
        Circle b = ball.show();
        b.setCenterX(ballx);
        b.setCenterY(bally.get());
        Pane pane = new Pane();

        class GravityTimer extends AnimationTimer{
            @Override
            public void handle(long now){
                b.setCenterY(b.getCenterY()+1.5);
                bally.set(b.getCenterY());
            }
        }

        ColorChanger colorChanger = new ColorChanger();
        Group changer = colorChanger.show(150,0);

        ColorChanger colorChanger2 = new ColorChanger();
        Group changer2 = colorChanger2.show(150,0);

        ArrayList<Group> changers = new ArrayList<Group>();
        changers.add(changer);
        changers.add(changer2);

        changer.setTranslateY(180);
        changer2.setTranslateY(-100);
        pane.getChildren().addAll(changer,changer2);

        Reward reward1 = new Star();
        Group starReward = reward1.show();
        reward1.blink(starReward);

        Reward reward2 = new Diamond();
        Group diamondReward = reward2.show();
        reward2.blink(diamondReward);

        ArrayList<Group> rewards = new ArrayList<>();
        ArrayList<Boolean> rewardsType = new ArrayList<>();
        rewards.add(starReward);
        rewardsType.add(true);
        rewards.add(diamondReward);
        rewardsType.add(false);

        starReward.relocate(135,0);
        diamondReward.relocate(135,0);
        starReward.setTranslateY(283);
        diamondReward.setTranslateY(40);
        pane.getChildren().addAll(starReward,diamondReward);

        AtomicInteger ballMemory = new AtomicInteger(0);
        AtomicInteger changerMemory = new AtomicInteger(0);
        AtomicInteger rewardMemory = new AtomicInteger(0);

        class MoveChangers extends AnimationTimer{
            @Override
            public void handle(long now){
                changerMemory.set(changerMemory.get()+4);
                for(int i=0;i<changers.size();++i){
                    changers.get(i).setTranslateY(changers.get(i).getTranslateY()+4);
                }
                if(changerMemory.get()>=40){
                    changerMemory.set(0);
                    this.stop();
                }
            }
        }

        class MoveRewards extends AnimationTimer{
            @Override
            public void handle(long now){
                rewardMemory.set(rewardMemory.get()+4);
                for(int i=0;i<rewards.size();i++){
                    rewards.get(i).setTranslateY(rewards.get(i).getTranslateY()+4);
                }
                if(rewardMemory.get()>=40){
                    rewardMemory.set(0);
                    this.stop();
                }
            }
        }

        Text scoreText = new Text("Score "+Integer.toString(this.score));
        scoreText.getStyleClass().add("white-text");
        scoreText.relocate(10,10);
        pane.getChildren().add(scoreText);

        Circular circle = new Circular();
        Group root = circle.show(150,300,70.0f,56.0f);
        circle.move(root,360);
        root.setLayoutX(80);
        root.setLayoutY(235);
        root.relocate(root.getLayoutX(),root.getLayoutY());
        pane.getChildren().add(root);

        Plus plus = new Plus();
        Group plusRoot = plus.show(200.0f,300.0f,75.0f);
        plus.move(plusRoot,360);

        HorizontalLine horizontalLine = new HorizontalLine();
        HorizontalLine horizontalLine2 = new HorizontalLine();
        Group horizontal = horizontalLine.show(130.0f,85.0f);
        horizontalLine.moveLeft(horizontal);
        Group horizontal2 = horizontalLine2.show(250.0f,85.0f);
        horizontalLine2.moveRight(horizontal2);
        Group horizontalObstacle = new Group(horizontal,horizontal2);
        horizontalObstacle.relocate(-800,-10);
        pane.getChildren().add(horizontalObstacle);

        Square rectangle = new Square();
        Group rectangleRoot = rectangle.show(100.0f,150.0f,120.0f,135.0f);
        rectangle.move(rectangleRoot,360);

        Square square = new Square();
        Group squareRoot = square.show(100.0f,150.0f,120.0f,120.0f);
        square.move(squareRoot,360);

        AtomicReference<Group> obstacle1 = new AtomicReference<>(root);
        AtomicReference<Group> obstacle2 = new AtomicReference<>(horizontalObstacle);
        AtomicReference<Group> obstacle3 = new AtomicReference<>((Group) null);
        AtomicBoolean flag= new AtomicBoolean(true);
        AtomicReference<Group> memory = new AtomicReference<>(squareRoot);

        AtomicInteger obstacleMemory = new AtomicInteger(0);

        class MoveObstacles extends AnimationTimer{
            @Override
            public void handle(long now){
                obstacleMemory.set(obstacleMemory.get()+4);
                obstacle1.get().setTranslateY(obstacle1.get().getTranslateY()+4);
                obstacle2.get().setTranslateY(obstacle2.get().getTranslateY()+4);
                if(obstacle3.get()!=null)
                    obstacle3.get().setTranslateY(obstacle3.get().getTranslateY()+4);
                if(obstacleMemory.get()>=40){
                    obstacleMemory.set(0);
                    this.stop();
                }
            }
        }

        AnimationTimer moveObstacles = new MoveObstacles();

        Group[] obstacles = new Group[5];
        obstacles[0] = root;
        obstacles[1] = horizontalObstacle;
        obstacles[2] = squareRoot;
        obstacles[3] = plusRoot;
        obstacles[4] = rectangleRoot;

        int[] obstaclex = new int[5];
        int[] obstacley = new int[5];

        obstaclex[0] = 85;
        obstacley[0] = -300;

        obstaclex[1] = -800;
        obstacley[1] = -240;

        obstaclex[2] = 90;
        obstacley[2] = -240;

        obstaclex[3] = 100;
        obstacley[3] = -260;

        obstaclex[4] = 90;
        obstacley[4] = -310;

        AtomicInteger obstacleCounter = new AtomicInteger(3);
        AtomicInteger nextObstacleX = new AtomicInteger(obstaclex[2]);
        AtomicInteger nextObstacleY = new AtomicInteger(obstacley[2]);

        AtomicBoolean firstMouse = new AtomicBoolean(true);
        AnimationTimer gravity = new GravityTimer();

        class MoveBall extends AnimationTimer{
            @Override
            public void handle(long now){
                b.setCenterY(b.getCenterY()-ballSpeed.get());
                if(b.getCenterY()<=ballMemory.get()-ballDistance.get()){
                    bally.set(b.getCenterY());
                    this.stop();
                }
                for(int i = 0;i<obstacles.length;i++){
                    ObservableList obs =obstacles[i].getChildren();
                    obs.forEach((o ->
                    {
                        try{
                            Shape shape = (Shape)o;
                            if(b.intersects(shape.getBoundsInParent())){
                                if(!b.getFill().equals(shape.getFill())) {
//                                    System.out.println("different color, game over");
                                }
                            }
                        }
                        catch (Exception e){
                            Group grp = (Group)o;
                            ObservableList obs2 = grp.getChildren();
                            obs2.forEach((o1 ->{
                                Shape shape = (Shape)o1;
                                if(b.intersects(shape.getBoundsInParent())){
                                    if(!b.getFill().equals(shape.getFill())) {
//                                        System.out.println("different color, game over");
                                    }
                                }
                            }));
                        }

                    }));
                }
            }
        }

        AnimationTimer moveBall = new MoveBall();
        AnimationTimer moveChangers = new MoveChangers();
        AnimationTimer moveRewards = new MoveRewards();
        AtomicBoolean gamePaused = new AtomicBoolean(false);

        pane.getChildren().add(b);

        AtomicInteger count = new AtomicInteger(0);

        //handle click
        pane.addEventHandler(MouseEvent.MOUSE_RELEASED,e->{
            if(!gamePaused.get()){
                if(firstMouse.get()){
                    firstMouse.set(false);
                    gravity.start();
                }
                Bounds boundsInScreen = obstacle1.get().localToScreen(obstacle1.get().getBoundsInLocal());
                gravity.stop();
                if(bally.get()>350){
                    //move ball
                    ballMemory.set((int)b.getCenterY());
                    ballDistance.set(35);
                    ballSpeed.set(6);
                    moveBall.start();
//                TODO: remove gravity on page close or pause.
                }
                else{
                    //move ball
                    ballMemory.set((int)b.getCenterY());
                    ballDistance.set(15);
                    ballSpeed.set(4);
                    moveBall.start();

                    moveChangers.start();
                    moveObstacles.start();
                    moveRewards.start();
                    double check = boundsInScreen.getMaxY();
                    if(boundsInScreen.getHeight()<20){
                        check+=50;
                    }
                    if(check>=550 && flag.get()){
                        flag.set(false);
                        obstacle3.set(memory.get());
                        obstacle3.get().setTranslateY(0);
                        obstacle3.get().relocate(nextObstacleX.get(), nextObstacleY.get());
                        pane.getChildren().add(obstacle3.get());
                        Reward reward=null;
                        if(count.get()%2==0) {
                            reward = new Star();
                            rewardsType.add(true);
                        }
                        else{
                            reward = new Diamond();
                            rewardsType.add(false);
                        }
                        count.set(count.get()+1);
                        Group rewardgrp = null;
                        try {
                            rewardgrp = reward.show();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        rewardgrp.relocate(135,0);
                        rewardgrp.setTranslateY(nextObstacleY.get()+50);
                        rewardgrp.setTranslateX(0);
                        reward.blink(rewardgrp);
                        rewards.add(rewardgrp);
                        pane.getChildren().add(rewardgrp);
                        ColorChanger colorChangerPer = new ColorChanger();
                        Group Changer = colorChangerPer.show(150,0);
                        Changer.setTranslateY(nextObstacleY.get()-75);
                        changers.add(Changer);
                        pane.getChildren().add(Changer);
                    }
                    if(boundsInScreen.getMinY()>=650 && !flag.get()){
                        flag.set(true);
                        pane.getChildren().remove(obstacle1.get());
                        nextObstacleX.set(obstaclex[obstacleCounter.get()]);
                        nextObstacleY.set(obstacley[obstacleCounter.get()]);
                        memory.set(obstacles[obstacleCounter.get()]);
                        obstacleCounter.set((obstacleCounter.get() + 1) % 5);
                        obstacle1.set(obstacle2.get());
                        obstacle2.set(obstacle3.get());
                        obstacle3.set(null);
                    }
                }
                for(int i=0;i<changers.size();++i){
                    if(b.intersects(changers.get(i).getBoundsInParent())){
                        pane.getChildren().remove(changers.get(i));
                        changers.remove(i);
                        String color;
                        do{
                            color = ColorChanger.generateRandomColor();
                        }while(Color.valueOf(color).equals(b.getFill()));
                        b.setFill(Color.valueOf(color));
                        break;
                    }
                }
                for(int i=0;i<rewards.size();i++){
                    if(b.intersects(rewards.get(i).getBoundsInParent())){
                        if(rewardsType.get(i)) {
                            player.incrementStars(1);
                            this.stars++;
                            this.score+=5;
                        }
                        else{
                            player.incrementDiamonds(1);
                            this.diamonds++;
                            this.score+=10;
                        }
                        scoreText.setText("Score "+Integer.toString(this.score));
                        pane.getChildren().remove(rewards.get(i));
                        rewards.remove(i);
                        rewardsType.remove(i);
                        player.setHighScore(Math.max(player.getHighScore(),this.score));
                    }
                }
                gravity.start();
            }
        });

        Image resumebtn = new Image(new FileInputStream("src/images/play.png"));
        ImageView resumeBtn = new ImageView(resumebtn);
        resumeBtn.setFitWidth(35);
        resumeBtn.setPreserveRatio(true);

        Image pausebtn = new Image(new FileInputStream("src/images/pause.png"));
        ImageView pauseBtn = new ImageView(pausebtn);
        pauseBtn.setFitWidth(35);
        pauseBtn.setPreserveRatio(true);
        pane.getChildren().add(pauseBtn);
        pauseBtn.relocate(10,450);
        pauseBtn.setOnMouseClicked(mouseEvent -> {
            pane.getChildren().add(resumeBtn);
            pane.getChildren().remove(pauseBtn);
            resumeBtn.relocate(10,450);
            gravity.stop();
            gamePaused.set(true);
        });

        resumeBtn.setOnMouseClicked(mouseEvent -> {
            pane.getChildren().add(pauseBtn);
            pane.getChildren().remove(resumeBtn);
            pauseBtn.relocate(10,450);
            gravity.start();
            gamePaused.set(false);
        });

        Image exitbtn = new Image(new FileInputStream("src/images/stop.png"));
        ImageView exitBtn = new ImageView(exitbtn);
        exitBtn.setFitWidth(35);
        exitBtn.setPreserveRatio(true);
        pane.getChildren().add(exitBtn);
        exitBtn.relocate(10,400);
        exitBtn.setOnMouseClicked(mouseEvent ->{
//            try {
//                prizes = prize(primaryStage);
//                mainMenu = mainMenu(primaryStage, prizes,player);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            primaryStage.setScene(mainMenu);
            player.getSavedGames().add(this);
        });

        pane.setStyle("-fx-background-color: #282828");
        Scene startScene = new Scene(pane,300,500);
        startScene.getStylesheets().add("Theme.css");
        primaryStage.setScene(startScene);
    }

    public void bonusLevel(Stage primaryStage) throws IOException{
        //TODO save games in player and show bonus scene after every 5th game is over
        //TODO add moving ball and 10 stars
        Text text = new Text("BONUS");
        text.getStyleClass().add("title-text");
        text.relocate(100,30);

        DisplayImage displayImage = new DisplayImage();
        ImageView bonus1 = displayImage.show("bonus.png",40);
        ImageView bonus2 = displayImage.show("bonus.png",40);
        bonus1.relocate(55,10);
        bonus2.relocate(195,10);

        Pane pane = new Pane(text,bonus1,bonus2);
        pane.getStyleClass().add("background");
        Scene bonusScene = new Scene(pane,300,500);
        bonusScene.getStylesheets().add("Theme.css");
        primaryStage.setScene(bonusScene);
    }
}

class Ball{
    Ball(){

    }
    public Circle show(){
        return new Circle(10.f,Color.valueOf("#f7f7f7"));
    }

    public Rectangle showRectangle(){
        return new Rectangle(17.0f,17.0f,Color.valueOf("#f7f7f7"));
    }

    public Polygon showTriangle(){
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
                0.0, 0.0,
                12.5, -15.0,
                25.0, 0.0 });
        triangle.setFill(Color.valueOf("#f7f7f7"));
        return triangle;
    }
}

abstract class Reward{
    public void blink(Group root){
        ScaleTransition st = new ScaleTransition(Duration.millis(1000),root);
         st.setByX(0.15f);
         st.setByY(0.15f);
         st.setCycleCount(100);
         st.setAutoReverse(true);
         st.play();
    }

    public abstract Group show() throws IOException;
}

class Star extends Reward{
    @Override
    public Group show() throws IOException{
        Image image = new Image(new FileInputStream("src/images/staricon.png"));
        ImageView star = new ImageView(image);
        star.setFitWidth(35);
        star.setPreserveRatio(true);
        return new Group(star);
    }
}

class Diamond extends Reward{
    @Override
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

class Arrow{
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

class ColorChanger{
    private final float radius;
    private static String[] possibleColors;
    ColorChanger(){
        this.radius = 13;
        ColorChanger.possibleColors = new String[]{"#e53e7b", "#8a49ef", "eed948", "5edcea"};
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
    public static String generateRandomColor(){
        Random rand = new Random();
        int index = rand.nextInt(4);
        return ColorChanger.possibleColors[index];
    }
}

class Obstacles{}

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

        Line lines[] = new Line[38];
        Paint paint[] = {Color.valueOf("#e53e7b"),Color.valueOf("#8a49ef"),Color.valueOf("#eed948"),Color.valueOf("#5edcea")};

        for(int i=-12;i<16;i++){
            lines[i+12] = new Line(i*len,y,(i+1)*len,y);
            lines[i+12].setStrokeWidth(strokeWidth);
            lines[i+12].setStroke(paint[Math.abs(i%4)]);
        }

        Group root = new Group();
        for(int i=-12;i<16;i++){
            root.getChildren().add(lines[i+12]);
        }
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

class DisplayImage{
    //TODO use this class for all images
    public ImageView show(String image,int width) throws IOException{
        Image cartImage = new Image(new FileInputStream("src/images/"+image));
        ImageView icon3 = new ImageView(cartImage);
        icon3.setFitWidth(width);
        icon3.setPreserveRatio(true);
        return icon3;
    }
}