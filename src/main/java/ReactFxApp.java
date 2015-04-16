import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;
import org.reactfx.util.Tuples;


public class ReactFxApp extends Application {
	
	public static void main(String... args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Pane root = new Pane();
		
		final EventStream<MouseEvent> clicks = EventStreams.eventsOf(root, MouseEvent.MOUSE_CLICKED);
		
		clicks.filter(click -> click.getButton() == MouseButton.PRIMARY)
                .mapToBi(click -> Tuples.t(click.getX(), click.getY()))
                .map((x,y) -> new Circle(x, y, 10, Color.RED))
                .subscribe(circle -> root.getChildren().add(circle));

		stage.setScene(new Scene(root, 600, 600));
		stage.show();
	}
}
