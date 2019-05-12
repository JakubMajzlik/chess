package ija.project.chess.game;

import ija.project.chess.board.Board;
import ija.project.chess.field.Field;
import ija.project.chess.figure.Figure;
import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.parser.ChessNotationMapper;
import ija.project.chess.turn.Turn;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class Game {
    private Board board;

    private List<Turn> history = new ArrayList<Turn>();

    private int historyIndex = 0;

    private Turn turn;

    private List<Figure> capturedFigures = new ArrayList<>();

    private Field sourceMarkField = null;
    private ImageView originalSourceMarkFieldBackground = null;
    private Field destinationMarkField = null;
    private ImageView originalDestinationMarkFieldBackground = null;

    private BorderPane content = new BorderPane();
    private VBox notationsBox;
    private VBox figuresForChange;
    private Text checkOrCheckMateText = new Text("Check");

    private Figure activeFigure = null;
    private Figure pawnFigureToChange;

    private boolean whiteTurn = true;

    private boolean autoPlay;
    private Timer timer;

    ImageView rookImage;
    ImageView bishopImage;
    ImageView queenImage;
    ImageView knightImage;

    ImageView markedSourceBg;
    ImageView markedDestinationBg;

    public Game(Board board) {
        this.board = board;
        markedSourceBg = new ImageView("images/markedbg.png");
        markedDestinationBg = new ImageView("images/markedbg.png");
        initializeGameBoardUI();
    }


    private void initializeGameBoardUI() {
        // TOP PANEL
        HBox topPanel = new HBox();

        Button restart = new Button("Restart");

        Button backward = new Button("Backward");
        Button stop = new Button("Stop");

        Button play = new Button("Play");
        Button forward = new Button("Forward");
        Button forwardAutoplay = new Button("Forward AutoPlay");
        Button backwardAutoplay = new Button("Backward AutoPlay");
        Slider autoplaySpeed = new Slider();

        // 10 = 10s
        autoplaySpeed.setMax(10);
        autoplaySpeed.setMin(0.1);


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

        play.setOnMouseClicked(e -> {
            stop.setVisible(true);
            play.setVisible(false);
            autoPlay = true;
            timer =  new Timer();

               timer.schedule(
                    new TimerTask() {

                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                if(autoPlay) {
                                    if (historyIndex < history.size()) {
                                        forward();
                                    } else {
                                        autoPlay = false;
                                        play.setVisible(true);
                                        stop.setVisible(false);
                                        timer.cancel();
                                        timer.purge();
                                    }
                                }
                            });
                        }
                     }, 0, 500);
        });

        stop.setOnMouseClicked(e -> {
            play.setVisible(true);
            stop.setVisible(false);
            timer.cancel();
            timer.purge();
            autoPlay = false;
        });

        restart.setOnMouseClicked( e -> {
            while(historyIndex > 0 || !isWhiteTurn()){
                backward();
            }
        });

        stop.setVisible(false);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setPadding(new Insets(0, 50, 0, 50));
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        separator2.setPadding(new Insets(0, 50, 0, 50));

        topPanel.getChildren().add(restart);

        topPanel.getChildren().add(separator1);

        topPanel.getChildren().add(backward);
        topPanel.getChildren().add(forward);

        topPanel.getChildren().add(separator2);

        topPanel.getChildren().add(play);
        topPanel.getChildren().add(stop);
        topPanel.getChildren().add(forwardAutoplay);
        topPanel.getChildren().add(backwardAutoplay);
        topPanel.getChildren().add(autoplaySpeed);

        topPanel.setAlignment(Pos.CENTER);

        content.setTop(topPanel);

        // NOTATIONS
        notationsBox = new VBox();

        content.setLeft(notationsBox);

        // Right box
        VBox rightBox = new VBox();

        checkOrCheckMateText.setFont(Font.font("Arial", 25));
        checkOrCheckMateText.setVisible(false);

        figuresForChange = new VBox();

        rookImage = new ImageView();
        bishopImage = new ImageView();
        knightImage = new ImageView();
        queenImage = new ImageView();
        figuresForChange.getChildren().add(rookImage);
        figuresForChange.getChildren().add(bishopImage);
        figuresForChange.getChildren().add(knightImage);
        figuresForChange.getChildren().add(queenImage);

        hideFiguresForChange();

        rightBox.getChildren().add(checkOrCheckMateText);
        rightBox.getChildren().add(figuresForChange);
        content.setRight(rightBox);

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

    public void showFiguresForChange(boolean isWhile) {
        String prefix = isWhile ? "w" : "b";

        rookImage.setImage(new Image("images/" + prefix + "rook.png"));
        bishopImage.setImage(new Image("images/" + prefix + "bishop.png"));
        knightImage.setImage(new Image("images/" + prefix + "knight.png"));
        queenImage.setImage(new Image("images/" + prefix + "queen.png"));

        figuresForChange.setVisible(true);
    }

    public void hideFiguresForChange() {figuresForChange.setVisible(false);}

    public void handleiguresForChangeEvents() {
        rookImage.setOnMouseClicked(e -> {
            if(pawnFigureToChange != null) {
                if(!areAllFiguresOfTypeAlive("V", pawnFigureToChange.isWhite())) {
                    Figure figure = getCapturedFigure("V", pawnFigureToChange.isWhite());
                    if(figure != null) {
                        pawnFigureToChange.getField().put(figure);
                        capturedFigures.add(pawnFigureToChange);
                        pawnFigureToChange = null;
                    }
                }
            }
        });

        bishopImage.setOnMouseClicked(e -> {

        });

        knightImage.setOnMouseClicked(e -> {

        });

        queenImage.setOnMouseClicked(e -> {

        });
    }

    private Figure getCapturedFigure(String figureType, boolean isWhite) {
        for (Figure figure : capturedFigures) {
            if(figure.isWhite() == isWhite) {
                if(figure.getFigureChar() == figureType) {
                    capturedFigures.remove(figure);
                    return figure;
                }
            }
        }
        return null;
    }

    private void markFields() {
        removeMarkedFields();

        if(turn != null) {
            if(isWhiteTurn()) {
                if(historyIndex < history.size()) {
                    if(turn.getWhiteFigure() != null) {
                        ((GridPane)content.getCenter()).add(markedSourceBg, turn.getWhiteSourceField().getCol() + 1, turn.getWhiteSourceField().getRow());

                        ((GridPane)content.getCenter()).add(markedDestinationBg, turn.getWhiteDestinationField().getCol() + 1, turn.getWhiteDestinationField().getRow());

                    }
                }

            } else {
                if(turn.getBlackFigure() != null) {
                    ((GridPane)content.getCenter()).add(markedSourceBg, turn.getBlackSourceField().getCol() + 1, turn.getBlackSourceField().getRow());

                    ((GridPane)content.getCenter()).add(markedDestinationBg, turn.getBlackDestinationField().getCol() + 1, turn.getBlackDestinationField().getRow());
                }
            }
        }
    }

    private void removeMarkedFields() {

        ((GridPane)content.getCenter()).getChildren().remove(markedSourceBg);


        ((GridPane)content.getCenter()).getChildren().remove(markedDestinationBg);

    }

    public void backward() {
        if(isWhiteTurn()) {
            historyIndex--;
            turn = history.get(historyIndex);
            setWhiteTurn(false);

            Figure figure = turn.getBlackFigure();
            Field sourceField = turn.getBlackSourceField();
            Field destinationField = turn.getBlackDestinationField();

            figure.setField(board.getField(sourceField.getCol(), sourceField.getRow()));
            board.getField(sourceField.getCol(), sourceField.getRow()).put(figure);
            if(destinationField.get() != null) {
                board.getField(destinationField.getCol(), destinationField.getRow()).setFigure(destinationField.get());
                ((GridPane)content.getCenter()).add(destinationField.get()
                        .getImage(), destinationField.getCol() + 1, destinationField.getRow());
            } else {
                board.getField(destinationField.getCol(), destinationField.getRow()).setFigure(null);
            }

            // Vypis natacie vlavo
            ChessNotationMapper mapper = new ChessNotationMapper();
            ChessTurnNotation notation = mapper.getNotation(turn);
            Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
            notationText.setText(notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation());

        } else {

            turn = history.get(historyIndex);
            setWhiteTurn(true);

            Figure figure = turn.getWhiteFigure();
            Field sourceField = turn.getWhiteSourceField();
            Field destinationField = turn.getWhiteDestinationField();

            figure.setField(board.getField(sourceField.getCol(), sourceField.getRow()));
            board.getField(sourceField.getCol(), sourceField.getRow()).put(figure);
            if(destinationField.get() != null){
                board.getField(destinationField.getCol(), destinationField.getRow()).setFigure(destinationField.get());
                ((GridPane)content.getCenter()).add(destinationField.get()
                        .getImage(), destinationField.getCol() + 1, destinationField.getRow());
            } else {
                board.getField(destinationField.getCol(), destinationField.getRow()).setFigure(null);
            }


            notationsBox.getChildren().remove(notationsBox.getChildren().size() - 1);

        }

        markFields();
    }

    public void forward() {
        if(historyIndex < history.size()) {
            if(isWhiteTurn()) {
                if(turn == null) {
                    turn = history.get(historyIndex);
                }

                setWhiteTurn(false);

                Figure figure = turn.getWhiteFigure();
                //Figure figure = board.getField(turn.getWhiteFigure().getField().getCol(), turn.getWhiteFigure().getField().getRow()).get();
                Field sourceField = turn.getWhiteSourceField();
                Field destinationField = turn.getWhiteDestinationField();

//                if(board.getField(destinationField.getCol(),destinationField.getRow()).get() != null) {
//                    turn.getWhiteDestinationField().setFigure(board.getField(destinationField.getCol(),destinationField.getRow()).get());
//                }

                //figure.move(board.getField(destinationField.getCol(),destinationField.getRow()));
                if( board.getField(destinationField.getCol(),destinationField.getRow()).get() != null) {

                    ((GridPane)content.getCenter()).getChildren().
                            remove(board.getField(destinationField.getCol(),destinationField.getRow()).get().getImage());
                    board.getField(destinationField.getCol(),destinationField.getRow()).setFigure(null);
                }
                board.getField(destinationField.getCol(),destinationField.getRow()).put(figure);
                board.getField(sourceField.getCol(),sourceField.getRow()).setFigure(null);

                // Vypis natacie vlavo
                ChessNotationMapper mapper = new ChessNotationMapper();
                ChessTurnNotation notation = mapper.getNotation(turn);
                Text notationText = new Text( notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation() + " ");
                notationText.setFont(Font.font("Arial", 20));
                notationsBox.getChildren().add(notationText);

            } else if(turn.getBlackFigure() != null) {
                setWhiteTurn(true);

               // Figure figure = board.getField(turn.getBlackFigure().getField().getCol(), turn.getBlackFigure().getField().getRow()).get();
               Figure figure = turn.getBlackFigure();
                Field sourceField = turn.getBlackSourceField();
                Field destinationField = turn.getBlackDestinationField();

//                if(board.getField(destinationField.getCol(),destinationField.getRow()).get() != null) {
//                    turn.getBlackDestinationField().setFigure(board.getField(destinationField.getCol(),destinationField.getRow()).get());
//                }

                //figure.move(board.getField(destinationField.getCol(),destinationField.getRow()));
                if( board.getField(destinationField.getCol(),destinationField.getRow()).get() != null) {

                    ((GridPane)content.getCenter()).getChildren().
                            remove(board.getField(destinationField.getCol(),destinationField.getRow()).get().getImage());
                    board.getField(destinationField.getCol(),destinationField.getRow()).setFigure(null);
                }
                board.getField(destinationField.getCol(),destinationField.getRow()).put(figure);
                board.getField(sourceField.getCol(),sourceField.getRow()).setFigure(null);

                // Vypis natacie vlavo
                ChessNotationMapper mapper = new ChessNotationMapper();
                ChessTurnNotation notation = mapper.getNotation(turn);
                Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
                notationText.setText(notationText.getText() + notation.getBlackTurnNotation());

                historyIndex++;
                if(historyIndex < history.size()) {
                    turn = history.get(historyIndex);
                }
            }
        }
        markFields();
    }

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
                removeMarkedFields();
                board.getField(sourceField.getCol(),sourceField.getRow()).setFigure(null);

                Figure king = getBoard().findFigure("K", null, null, null, isWhiteTurn());
                Figure enemyKing = getBoard().findFigure("K", null, null, null, !isWhiteTurn());

                boolean isCheck = isCheck(enemyKing);
                boolean isCheckMate = isCheckMate(enemyKing);

                if(!isCheck && !isCheck(king)) getCheckOrCheckMateText().setVisible(false);

                if(figure.isWhite()) {
                    turn = new Turn();
                    turn.setTurnOrder(historyIndex + 1);
                    turn.setWhiteFigure(figure);
                    turn.setWhiteSourceField(sourceField);
                    turn.setWhiteDestinationField(destinationField);
                    turn.setWhiteDestinationField(destinationField);
                    if(turn.getWhiteDestinationField().get() != null) {
                        turn.getWhiteDestinationField().get().setField(turn.getWhiteDestinationField());
                    }

                    if(isCheck && !isCheckMate) turn.setBlackCheck(true);
                    if(isCheckMate) turn.setBlackCheckMate(true);
                    //TODO: dalsie casti notacie

                    if(historyIndex < history.size()) {
                        int historySize = history.size();
                        history.subList(historyIndex, historySize - 1).clear();
                    }

                    history.add(historyIndex, turn);

                    // Vypis natacie vlavo
                    ChessNotationMapper mapper = new ChessNotationMapper();
                    ChessTurnNotation notation = mapper.getNotation(turn);
                    Text notationText = new Text( notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation() + " ");
                    notationText.setFont(Font.font("Arial", 20));
                    notationsBox.getChildren().add(notationText);

                    return true;
                } else {
                    turn.setBlackFigure(figure);
                    turn.setBlackSourceField(sourceField);
                    turn.setBlackDestinationField(destinationField);
                    turn.setBlackDestinationField(destinationField);
                    if(turn.getBlackDestinationField().get() != null) {
                        turn.getBlackDestinationField().get().setField(turn.getWhiteDestinationField());
                    }


                    if(isCheck && !isCheckMate) turn.setWhiteCheck(true);
                    if(isCheckMate) turn.setWhiteCheckMate(true);
                    //TODO: dalsie casti notacie

                    if(historyIndex  < history.size() - 1) {
                        int historySize = history.size();
                        history.subList(historyIndex+1, historySize - 1).clear();
                    }

                    historyIndex++;

                    // Vypis natacie vlavo
                    ChessNotationMapper mapper = new ChessNotationMapper();
                    ChessTurnNotation notation = mapper.getNotation(turn);
                    Text notationText = (Text) notationsBox.getChildren().get(notationsBox.getChildren().size() - 1);
                    notationText.setText(notationText.getText() + notation.getBlackTurnNotation());

                    return true;
                }

            }
        }
        return false;
    }

    public boolean isCheck(Figure king) {
        return isCheck(king.getField(), king, new ArrayList<>());
    }

    // TODO: otestovat
    //TODO: pridat tuto kontrolo pu tahu
    public boolean isCheck(Field checkField, Figure king) {
        return isCheck(checkField, king, new ArrayList<Figure>());
    }

    public boolean isCheck(Field checkField, Figure king, List<Figure> excludeList) {
        boolean isKingWhite = king.isWhite();
        List<Figure> enemyFigureList = getAllFigures(!isKingWhite);
        enemyFigureList.removeAll(excludeList);

        for(Figure figure : enemyFigureList) {
            if(figure.canMove(checkField)) {

                Text text = getCheckOrCheckMateText();
                if(king.isWhite()) {
                    text.setText("White Check");
                } else {
                    text.setText("Black Check");
                }
                text.setVisible(true);

                return true;
            }
        }
        return false;
    }

    //TODO: otestovat
    //TODO: pridat tuto kontrolo pu tahu
    public boolean isCheckMate(Figure king) {
        Field checkField = king.getField();

        if(!isCheck(checkField, king)) return false;

        boolean isKingWhite = king.isWhite();
        List<Figure> enemyFigureList = getAllFigures(!isKingWhite);
        List<Figure> allyFigureList = getAllFigures(isKingWhite);
        List<Field> kingsPossibleMovement = new ArrayList<>();

        kingsPossibleMovement.add(checkField.nextField(Field.Direction.L));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.LU));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.U));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.RU));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.R));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.RD));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.D));
        kingsPossibleMovement.add(checkField.nextField(Field.Direction.LD));

        for(Field field : kingsPossibleMovement) {
            if(field == null) continue;

            if(!isCheck(checkField, king)) return false;
        }

        for(Figure enemyFigure : enemyFigureList) {
            if(enemyFigure.canMove(checkField)) {
                for(Figure allyFigure : allyFigureList) {
                    if(allyFigure.canMove(enemyFigure.getField())) {
                        List<Figure> excludedFigure  = new ArrayList<>();
                        excludedFigure.add(enemyFigure);
                        if(!isCheck(checkField, king, excludedFigure)) return false;
                    }
                }

                for(Figure allyFigure : allyFigureList) {
                    for(Field enemySurrounding : enemyFigure.getField().getSurrouding()) {
                        if(enemySurrounding != null) {
                            if(allyFigure.canMove(enemySurrounding)) {
                                List<Figure> excludedFigure  = new ArrayList<>();
                                excludedFigure.add(enemyFigure);
                                if(!isCheck(checkField, king, excludedFigure)) return false;
                            }
                        }

                    }
                }
            }
        }

        Text text = getCheckOrCheckMateText();
        if(king.isWhite()) {
            text.setText("White Check Mate");
        } else {
            text.setText("Black Check Mate");
        }
        text.setVisible(true);

        return true;
    }

    private boolean areAllFiguresOfTypeAlive(String figureType, boolean isWhite) {
        int count;
        switch (figureType) {
            case "Q": count = 1; break;
            case "S": case "J": case "V": count = 2; break;
            default: count = 0;
        }
        for(Figure figure : getAllFigures(isWhite)) {
            if(figure.getFigureChar().equals(figureType)) {
                count --;
            }
        }
        return count == 0;
    }

    private List<Figure> getAllFigures(boolean isWhite) {
        List<Figure> figureList = new ArrayList<>();

        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                Figure figure = board.getField(i, j).get();
                if(figure != null) {
                    if(figure.isWhite() == isWhite) {
                        figureList.add(figure);
                    }
                }
            }
        }
        return figureList;
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

    public List<Figure> getCapturedFigures() {
        return capturedFigures;
    }

    public void setCapturedFigures(List<Figure> capturedFigures) {
        this.capturedFigures = capturedFigures;
    }

    public Text getCheckOrCheckMateText() {
        return checkOrCheckMateText;
    }

    public void setCheckOrCheckMateText(Text checkOrCheckMateText) {
        this.checkOrCheckMateText = checkOrCheckMateText;
    }

    public Figure getPawnFigureToChange() {
        return pawnFigureToChange;
    }

    public void setPawnFigureToChange(Figure pawnFigureToChange) {
        this.pawnFigureToChange = pawnFigureToChange;
    }
}
