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
	int width = 500, height = 300;
	float x = 100, y = 100, dx = -1.5f, dy = -1.5f;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Circle circle = new Circle(100, 100, 30);
		
		Group root = new Group(circle);
		Scene scene = new Scene(root, 400, 300);
	
		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				// account for circle hitting edge of the screen
				if (circle.getCenterX() + circle.getTranslateX() - circle.getRadius() < 0 || circle.getCenterX() + circle.getTranslateX() + circle.getRadius() > scene.getWidth()) {
					dx = -dx;
				}
				if (circle.getCenterY() + circle.getTranslateY() - circle.getRadius() < 0 || circle.getCenterY() + circle.getTranslateY() + circle.getRadius() > scene.getHeight()) {
					dy = -dy;
				}
				
				// translate the circle
				circle.setTranslateX(circle.getTranslateX() + dx);
				circle.setTranslateY(circle.getTranslateY() + dy);
			}
		});
		
		// using https://docs.oracle.com/javafx/2/animations/basics.htm example 1-5
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
		
		primaryStage.setTitle("Hello Animation!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
