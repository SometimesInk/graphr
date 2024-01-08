package ink.ink;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Pane settings = new Pane();
        Pane graph = new Pane();
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("GraphR");
        primaryStage.setScene(scene);
        primaryStage.show();

        root.requestFocus();

        AtomicInteger num = new AtomicInteger();

        Label seed = new Label("Seed: " + num);
        TextField numSelect = new TextField();
        numSelect.setLayoutY(25);
        numSelect.setPrefWidth(100);
        numSelect.setOnAction(e -> {
            num.set(Integer.parseInt(numSelect.getText()));
            seed.setText("Seed: " + num);
        });
        Button button = new Button("Calculate");
        button.setLayoutY(scene.getHeight() - 25);
        button.setPrefWidth(100);
        AtomicBoolean graphActive = new AtomicBoolean(false);
        button.setOnAction(e -> {
            graphActive.set(true);
            drawGraph(num.get(), graph);
        });
        Label cursor = new Label("Cursor: ");
        cursor.setLayoutY(scene.getHeight() - 100);

        settings.setLayoutX(0);
        settings.setLayoutY(0);
        settings.setPrefWidth(100);
        settings.setPrefHeight(scene.getHeight());
        settings.setStyle("-fx-background-color: #d4d4d4;");
        settings.getChildren().add(seed);
        settings.getChildren().add(numSelect);
        settings.getChildren().add(button);

        graph.setLayoutX(100);
        graph.setLayoutY(0);
        graph.setPrefWidth(scene.getWidth() - 100);
        graph.setPrefHeight(scene.getHeight());
        graph.setStyle("-fx-background-color: #f1f1f1;");

        root.getChildren().add(graph);
        root.getChildren().add(settings);


        Line mouse = new Line();
        //get mouse position
        root.setOnMouseMoved(e -> {
            if(!settings.contains(e.getX(), e.getY())) {
                mouse.setStartX(e.getX());
                mouse.setStartY(0);
                mouse.setEndX(e.getX());
                mouse.setEndY(graph.getHeight());
            }

        });
    }

    public void drawGraph(int seed, Pane graph) {
        //remove old lines
        graph.getChildren().removeAll(graph.getChildren());

        //calculate function domain and codomain
        int x = seed;
        int max = 0;
        int steps = 0;
        while(x != 1) {
            if (x % 2 == 0) {
                x /= 2;
            } else {
                x = (x * 3) + 1;
            }
            if (x > max) {
                max = x;
            }
            steps++;
            System.out.println(x + " " + steps);
        }
        //draw graph between max and 1 (graph's extremes) with x steps
        System.out.println("Drawing on extremes 1 and " + max + " with " + steps + " steps");
        x = seed;
        int prog = 0;
        while(x != 1) {

            //draw the line
            Line line = new Line();
            line.setStartX(calc(prog, steps, (int) graph.getWidth()));
            line.setStartY(graph.getHeight() - calc(x, max, (int) graph.getHeight()));


            //calculate next x
            if (x % 2 == 0) {
                x /= 2;
            } else {
                x = (x * 3) + 1;
            }

            prog++;

            line.setEndX(calc(prog, steps, (int) graph.getWidth()));
            line.setEndY(graph.getHeight() - calc(x, max, (int) graph.getHeight()));

            graph.getChildren().add(line);
        }
    }

    int calc(int x, int max, int size) {
        return (size * x) / max;
    }

    public static void main(String[] args) {
        launch();
    }
}