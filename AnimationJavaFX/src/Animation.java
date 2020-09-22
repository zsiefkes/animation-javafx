import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animation extends Application {
	// window width and height
	int width = 800, height = 600;
	
	// initial default velocities
	private static float dx = -3f, dy = -3f;

	// fields for storing velocities
	private static ArrayList<Float> dxArr = new ArrayList<Float>();
	private static ArrayList<Float> dyArr = new ArrayList<Float>();

	// want to add multiple circles and store them in an arraylist
	private static int numCircles = 5;
	private static ArrayList<Circle> circles = new ArrayList<Circle>();
	
	// create multiple circles and populate velocity arrays
	private static void createCircles(int numCircles) {
		for (int i = 0; i < numCircles; i++) {
			Circle circle = new Circle((i+1)*70, (i+1)*50, 30);
			circles.add(circle);
			dxArr.add(dx);
			dyArr.add(dy);
		}
		
		
	}

	// then, holy shit, try and detect when they bounce into each other? phwoar

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create a group (like a pane, but without a set layout)
		Group root = new Group();
		// add the circles to the group
		for (Circle c : circles) {
			root.getChildren().add(c);
		}
		
		Scene scene = new Scene(root, width, height);
	
		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				// do we have to loop through each circle and have each circle have its own um stuff? translatex etc. probably want to put them in a class of their own, a custom thing. when we get to the bugs
				for (int i = 0; i < numCircles; i++) {
					Circle circle = circles.get(i);
					float dx = dxArr.get(i);
					float dy = dyArr.get(i);
					// translate the circle
					circle.setTranslateX(circle.getTranslateX() + dx);
					circle.setTranslateY(circle.getTranslateY() + dy);
					
					// so, I ran into bugginess when I translated the circle AFTER checking for going over the border. translating first, then checking for going over the border and reversing velocity direction, seemed to fix it.
					
					// check for circle hitting edge of the screen and reverse direction in this event
					if (circle.getCenterX() + circle.getTranslateX() < circle.getRadius() || circle.getCenterX() + circle.getTranslateX() + circle.getRadius() > scene.getWidth()) {
						dxArr.set(i, -dx);
					}
					if (circle.getCenterY() + circle.getTranslateY() < circle.getRadius() || circle.getCenterY() + circle.getTranslateY() + circle.getRadius() > scene.getHeight()) {
						dyArr.set(i, -dy);
					}
//				System.out.println("translateX: " + circle.getTranslateX() + " centerX: " + circle.getCenterX());
//				System.out.println("translateY: " + circle.getTranslateY() + " centerY: " + circle.getCenterY());
					// okay cool. circle.centerX() and circle.centerY() are the initial x, y coordinates you instantiate the circle with (Circle circle = new Circle(100, 100, 30);)
					// and then circle.getTranslateX() and circle.getTranslateY() are the translations from these initial coordinates of the circle currently. sweet. so their sum in each direction gives you the current coordinates of the center of the circle.
				}
				
				
			}
		});
		
		// create timeline with keyframe. using https://docs.oracle.com/javafx/2/animations/basics.htm example 1-5
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
		
		primaryStage.setTitle("Hello Animation!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		createCircles(numCircles);
		launch(args);
	}
	
}
