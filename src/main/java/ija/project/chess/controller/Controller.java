package ija.project.chess.controller;

import ija.project.chess.board.Board;
import ija.project.chess.exceptions.ChessNotationMapperException;
import ija.project.chess.exceptions.WrongChessNotationFileException;
import ija.project.chess.factory.GameFactory;
import ija.project.chess.game.Game;
import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.parser.ChessNotationMapper;
import ija.project.chess.reader.ChessNotationReader;
import ija.project.chess.turn.Turn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    public void newGame(ActionEvent event) {
        Board board = new Board(8);
        Game game = GameFactory.createChessGame(board);
        board.setGame(game);

        Tab tab = new Tab();
        tab.setText("Game #" + (tabPane.getTabs().size() + 1));
        tab.setContent(game.getContent());

        tabPane.getTabs().add(tab);
    }

    @FXML
    public void openGame(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Chess Notation File", "*.chess");

        fileChooser.setSelectedExtensionFilter(filter);
        fileChooser.getExtensionFilters().add(filter);

        File notationFile = fileChooser.showOpenDialog(new Stage());

        if(notationFile == null) return;

        Board board = new Board(8);
        Game game = GameFactory.createChessGame(board);
        board.setGame(game);

        ChessNotationReader reader = null;
        ChessNotationMapper mapper = new ChessNotationMapper(game);
        ChessTurnNotation turnNotation;
        List<Turn> history = new ArrayList<>();
        try {
            reader = new ChessNotationReader(notationFile);
            while((turnNotation = reader.getTurnNotation()) != null) {
                Turn turn = mapper.getTurn(turnNotation);
                history.add(turn);
            }
            game.setHistory(history);


            Tab tab = new Tab();
            tab.setText("Game #" + (tabPane.getTabs().size() + 1));
            tab.setContent(game.getContent());

            tabPane.getTabs().add(tab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(ChessNotationMapperException | WrongChessNotationFileException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong file content");
            alert.show();
        }
    }

    @FXML
    public void saveGame(ActionEvent event) {



    }

    @FXML
    public void quitGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void showAbout() {
        final Alert aboutAppAlert = new Alert(Alert.AlertType.NONE);
        aboutAppAlert.setContentText("Test");
        aboutAppAlert.show();
        // TODO: Ukoncenie
        aboutAppAlert.setOnCloseRequest(e -> aboutAppAlert.close());
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
