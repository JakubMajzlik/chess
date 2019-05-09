package ija.project.chess.game;

import ija.project.chess.board.Board;
import ija.project.chess.field.Field;
import ija.project.chess.figure.Figure;
import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.parser.ChessNotationMapper;
import ija.project.chess.turn.Turn;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private Board board;

    private List<Turn> history = new LinkedList<Turn>();

    private int historyIndex = 0;

    private Turn turn;

    private BorderPane content = new BorderPane();

    private Figure activeFigure = null;

    private boolean whiteTurn = true;

    public Game(Board board) {
        this.board = board;
        initializeGameBoardUI();
    }

    private void initializeGameBoardUI() {
        // TOP PANEL
        HBox topPanel = new HBox();

        Button backward = new Button("Backward");
        Button play = new Button("Play");
        Button stop = new Button("Stop");
        Button forward = new Button("Forward");

        stop.setVisible(false);

        topPanel.getChildren().add(backward);
        topPanel.getChildren().add(play);
        topPanel.getChildren().add(stop);
        topPanel.getChildren().add(forward);

        topPanel.setAlignment(Pos.CENTER);

        content.setTop(topPanel);

        // GAME BOARD
        GridPane gameBoard = new GridPane();

        for(int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                gameBoard.add(board.getField(col , row).getBackground(), col, row);
                // TODO: vymazat podmienku
                if(board.getField(col, row).getFigure() != null)
                gameBoard.add(board.getField(col, row).getFigure().getImage(), col, row);

            }
        }

        gameBoard.setAlignment(Pos.CENTER);

        content.setCenter(gameBoard);

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void undo() {
        //TODO
    }

    public boolean move(Figure figure, Field field) {

        if(figure.canMove(field)) {
            Field sourceField = figure.getField();
            if(figure.move(field)) {
                if(figure.isWhite()) {
                    turn = new Turn();
                    turn.setTurnOrder(historyIndex + 1);
                    turn.setWhiteFigure(figure);
                    turn.setWhiteSourceField(sourceField);
                    turn.setWhiteDestinationField(field);
                    //TODO: dalsie casti notacie

                    if(historyIndex < history.size() - 1) {
                        for (int i = historyIndex; i < history.size(); i++) {
                            history.remove(i);
                        }
                    }
                    history.add(historyIndex++, turn);
                } else {
                    turn.setBlackFigure(figure);
                    turn.setBlackSourceField(sourceField);
                    turn.setBlackDestinationField(field);
                    //TODO: dalsie casti notacie

                    if(historyIndex  < history.size()) {
                        for(int i = historyIndex; i < history.size(); i++) {
                            history.remove(i);
                        }
                    }

                    ChessNotationMapper mapper = new ChessNotationMapper(this);
                    ChessTurnNotation notation = mapper.getNotation(turn);
                    System.out.println(notation.getTurnOrder() + " " + notation.getWhiteTurnNotation() + " " + notation.getBlackTurnNotation());
                }

            }
        }
        return false;
    }

    public BorderPane getContent() {
        return content;
    }

    public Figure getActiveFigure() {
        return activeFigure;
    }

    public void setActiveFigure(Figure activeFigure) {
        this.activeFigure = activeFigure;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }
}
