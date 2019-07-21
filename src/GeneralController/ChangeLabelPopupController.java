package GeneralController;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import Model.Edge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeLabelPopupController extends AbstractController implements Initializable {
	
	@FXML
	private TextField txtValueLabel;
	@FXML
	private Button btnChangeLabel;
	
	private Stage stage = null;
	private HashMap<Edge, String> result = new HashMap<Edge, String>();
	private Edge edge;
	
	public ChangeLabelPopupController() {
		
	}
	
	
	
	public ChangeLabelPopupController(Edge edge) {
		super();
		this.edge = edge;
		
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnChangeLabel.setOnAction((event)->{
           // result.clear();
           // result.put("typeEdge", "directed");
           // result.put("weight", txtEdgeWeight.getText());
            edge.setTextWeight(txtValueLabel.getText());
            closeStage();
        });
		
		

	}
	
	/**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }
    
    public Edge getResult() {
        return this.edge;
    }


}