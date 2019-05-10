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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private Board board;

    private List<Turn> history = new ArrayList<Turn>();

    private int historyIndex = 0;

    private Turn turn;

    private BorderPane content = new BorderPane();
    private VBox notationsBox;

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

        backward.setOnMouseClicked(e -> {
            if(historyIndex > 0 || !isWhiteTurn()){
                backward();
            }
        });

        forward.setOnMouseClicked(e -> {
            if(historyIndex < history.size()) {
                forward();
            }
        });

        stop.setVisible(false);

        topPanel.getChildren().add(backward);
        topPanel.getChildren().add(play);
        topPanel.getChildren().add(stop);
        topPanel.getChildren().add(forward);

        topPanel.setAlignment(Pos.CENTER);

        content.setTop(topPanel);

        // NOTATIONS
        notationsBox = new VBox();

        content.setLeft(notationsBox);

        // GAME BOARD
        GridPane gameBoard = new GridPane();

        gameBoard.setAlignment(Pos.CENTER);

        for(int row = 0; row < board.getSize() + 1; row++) {
            for (int col = 0; col < board.getSize() + 1; col++) {
                if(col == 0 && row < board.getSize()) {
                    Text text = new Text(""+ (row + 1));
                    text.setFont(Font.font("Arial", 20));
                    gameBoard.add(text, col, row);
                } else if(col > 0 && row == board.getSize()) {
                    //TODO: Vycentrovat
                    Text text = new Text(""+ (char)(col - 1 + 'a'));
                    text.setFont(Font.font("Arial", 20));
                    text.setTextAlignment(TextAlignment.CENTER);
                    gameBoard.add(text, col, row);
                } else if(col > 0 && row < board.getSize()) {
                    gameBoard.add(board.getField(col - 1 , row).getBackground(), col, row);
                    // TODO: vymazat podmienku
                    if(board.getField(col - 1, row).getFigure() != null)
                        gameBoard.add(board.getField(col - 1, row).getFigure().getImage(), col, row);
                }


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

    public void backward() {
        if(isWhiteTurn()) {
            historyIndex--;
            turn = history.get(historyIndex);
            setWhiteTurn(false);

            Figure figure = turn.getBlackFigure();
            Field sourceField = turn.getBlackSourceField();
            Field destinationField = turn.getBlackDestinationField();
//
//            ChessNotationMapper mapper = new ChessNotationMapper(this);
//            ChessTurnNotation notation = mapper.getNotation(turn);
//            System.out.println(notation.getTurnOrder() + " " + notation.getWhiteTurnNotation() + " " + notation.getBlackTurnNotation());

            board.getField(sourceField.getCol(), sourceField.getRow()).put(figure);
            if(destinationField.get() != null) {
                board.getField(destinationField.getCol(), destinationField.getRow()).put(destinationField.get());
            }

            // Vypis natacie vlavo
            ChessNotationMapper mapper = new ChessNotationMapper(this);
            ChessTurnNotation notation = mapper.getNotation(turn);
            Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
            notationText.setText(notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation());

        } else {

            turn = history.get(historyIndex);
            setWhiteTurn(true);

            Figure figure = turn.getWhiteFigure();
            Field sourceField = turn.getWhiteSourceField();
            Field destinationField = turn.getWhiteDestinationField();

            board.getField(sourceField.getCol(), sourceField.getRow()).put(figure);
            if(destinationField.get() != null){
                board.getField(destinationField.getCol(), destinationField.getRow()).put(destinationField.get());
            }

            notationsBox.getChildren().remove(notationsBox.getChildren().size() - 1);

        }
    }

    public void forward() {
        if(historyIndex < history.size()) {
            if(isWhiteTurn()) {
                if(turn == null) {
                    turn = history.get(historyIndex);
                }

                setWhiteTurn(false);

//                Figure figure = turn.getWhiteFigure();
                Figure figure = board.getField(turn.getWhiteFigure().getField().getCol(), turn.getWhiteFigure().getField().getRow()).get();
                Field sourceField = turn.getWhiteSourceField();
                Field destinationField = turn.getWhiteDestinationField();

                figure.move(board.getField(destinationField.getCol(),destinationField.getRow()));

                // Vypis natacie vlavo
                ChessNotationMapper mapper = new ChessNotationMapper(this);
                ChessTurnNotation notation = mapper.getNotation(turn);
                Text notationText = new Text( notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation() + " ");
                notationText.setFont(Font.font("Arial", 20));
                notationsBox.getChildren().add(notationText);

            } else if(turn.getBlackFigure() != null) {
                setWhiteTurn(true);

                Figure figure = board.getField(turn.getBlackFigure().getField().getCol(), turn.getBlackFigure().getField().getRow()).get();
//                Figure figure = turn.getBlackFigure();
                Field sourceField = turn.getBlackSourceField();
                Field destinationField = turn.getBlackDestinationField();

                figure.move(board.getField(destinationField.getCol(),destinationField.getRow()));

                // Vypis natacie vlavo
                ChessNotationMapper mapper = new ChessNotationMapper(this);
                ChessTurnNotation notation = mapper.getNotation(turn);
                Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
                notationText.setText(notationText.getText() + notation.getBlackTurnNotation());

                historyIndex++;
                if(historyIndex < history.size()) {
                    turn = history.get(historyIndex);
                }
            }
        }
    }

    //TODO: zmena tahu po pretoceni hapruje
    public boolean move(Figure figure, Field field) {

        if(figure.canMove(field)) {
            Field sourceField = new Field(figure.getField().getCol(), figure.getField().getRow());
            sourceField.setFigure(figure);
            sourceField.setBackground(figure.getField().getBackground());
            sourceField.setBoard(field.getBoard());

            Field destinationField = new Field(field.getCol(), field.getRow());
            destinationField.setFigure(field.get());
            destinationField.setBackground(field.getBackground());
            destinationField.setBoard(field.getBoard());
            if(figure.move(field)) {
                if(figure.isWhite()) {
                    turn = new Turn();
                    turn.setTurnOrder(historyIndex + 1);
                    turn.setWhiteFigure(figure);
                    turn.setWhiteSourceField(sourceField);
                    turn.setWhiteDestinationField(destinationField);
                    //TODO: dalsie casti notacie

                    if(historyIndex < history.size()) {
                        int historySize = history.size();
                        history.subList(historyIndex, historySize - 1).clear();
                    }

                    history.add(historyIndex, turn);

                    // Vypis natacie vlavo
                    ChessNotationMapper mapper = new ChessNotationMapper(this);
                    ChessTurnNotation notation = mapper.getNotation(turn);
                    Text notationText = new Text( notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation() + " ");
                    notationText.setFont(Font.font("Arial", 20));
                    notationsBox.getChildren().add(notationText);

                    return true;
                } else {
                    turn.setBlackFigure(figure);
                    turn.setBlackSourceField(sourceField);
                    turn.setBlackDestinationField(destinationField);
                    //TODO: dalsie casti notacie

                    if(historyIndex  < history.size() - 1) {
                        int historySize = history.size();
                        history.subList(historyIndex+1, historySize - 1).clear();
                    }

                    historyIndex++;

                    // Vypis natacie vlavo
                    ChessNotationMapper mapper = new ChessNotationMapper(this);
                    ChessTurnNotation notation = mapper.getNotation(turn);
                    Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
                    notationText.setText(notationText.getText() + notation.getBlackTurnNotation());

                    return true;
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

    public List<Turn> getHistory() {
        return history;
    }

    public void setHistory(List<Turn> history) {
        this.history = history;
    }
}
