package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import Enum.StateOnLeftPane;
import Model.Edge;
import Model.Vertex;
import application.MainController;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MainControllerTest {

	@Test
	public void createVertex() {
		// List<Integer> li = Arrays.asList(0, 1, 2, 3, 4, 5);
		// assertTrue(li.equals(Iterators.fillList(new ArrayList<Integer>(),
		// li.iterator())));

		MainController main = new MainController();
		MouseEvent event = new MouseEvent(null, 0, 0, 0, 0, null, 10, false, false, false, false, false, false, false,
				false, false, false, null);
		main.setEventOnLeftPane(StateOnLeftPane.VERTEX);
		main.setIndexVertex(-1);

		main.pressMouse(event);
		assertTrue(main.getVertices().size() > 0);

	}

}
