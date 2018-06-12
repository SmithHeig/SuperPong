package Menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Displayer;
import network.ServerManager;
import protocole.data.stats.Stats;

public class StatsMenuController {

    @FXML
    private Button BackButton;

    @FXML
    private PieChart graphStats;

    @FXML
    private VBox vboxStats;

    @FXML
    void goBackToNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkMenu();
    }

    @FXML
    private void initialize(){
        Stats stats = ServerManager.getInstance().getStats();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nombre de victoires", stats.getNbWins()),
                new PieChart.Data("Nombre de défaites", stats.getNbPlays() -stats.getNbWins())
                );
        graphStats.setData(pieChartData);

        int nbLoose = stats.getNbPlays() - stats.getNbWins();
        double ratio = (double) stats.getNbWins() / (double) nbLoose;
        vboxStats.getChildren().add(new Label("Nb de victoires\t: " + stats.getNbWins()));
        vboxStats.getChildren().add(new Label("Nb de defaites\t: " + nbLoose));
        vboxStats.getChildren().add(new Label("Nb parties jouées\t: " + stats.getNbPlays()));
        vboxStats.getChildren().add(new Label("Ratio\t\t\t: " + ratio));
    }


}
