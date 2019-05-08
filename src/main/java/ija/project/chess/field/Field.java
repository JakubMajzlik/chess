package ija.project.chess.field;

import ija.project.chess.board.Board;
import ija.project.chess.figure.Figure;
import ija.project.chess.game.Game;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Field {

    private int col;
    private int row;

    private Figure figure;

    private Board board;

    private Field[] surrouding = new Field[8];

    private ImageView background;

    public enum Direction{
        D, L, LD, LU, R, RD, RU, U
    }

    public Field(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void addNextField(Direction dirs, Field field) {
        switch(dirs) {
            case D:
                surrouding[5] = field;
                break;
            case L:
                surrouding[7] = field;
                break;
            case LD:
                surrouding[6] = field;
                break;
            case LU:
                surrouding[0] = field;
                break;
            case R:
                surrouding[3] = field;
                break;
            case RD:
                surrouding[4] = field;
                break;
            case RU:
                surrouding[2] = field;
                break;
            case U:
                surrouding[1] = field;
                break;
            default:
                break;
        }
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public ImageView getBackground() {
        return background;
    }

    public void setBackground(ImageView background) {
        this.background = background;
    }

    public boolean isEmpty() {
        return figure == null;
    }

    public boolean put(Figure figure) {
        if(isEmpty()) {
            if(figure.getField() != null) {
                Field figureField = figure.getField();
                board.getField(figureField.getCol(), figureField.getRow()).remove(figure);
            }
            this.figure = figure;
            figure.setField(this);
            if(board.getGame() != null) {
                GridPane gridPane = (GridPane)board.getGame().getContent().getCenter();
                gridPane.add(figure.getImage(), this.col,this.row);
            }

            return true;
        }
        return false;
    }

    public boolean remove(Figure figure) {
        if(this.figure.equals(figure)) {
            if(!isEmpty()) {
                GridPane gridPane = (GridPane)board.getGame().getContent().getCenter();
                gridPane.getChildren().remove(figure.getImage());
                figure.setField(null);
                this.figure = null;
                return true;
            }
        }
        return false;
    }

    public Figure get() {
        return figure;
    }

    public Field nextField(Field.Direction dirs) {
        switch(dirs) {
            case D:
                return surrouding[5];
            case L:
                return surrouding[7];
            case LD:
                return surrouding[6];
            case LU:
                return surrouding[0];
            case R:
                return surrouding[3];
            case RD:
                return surrouding[4];
            case RU:
                return surrouding[2];
            case U:
                return surrouding[1];
            default:
                break;
        }
        return null;
    }

    public void handleEvents() {
        background.setOnMouseClicked(e -> {
            Game game = this.board.getGame();
            if(this.figure == null && game.getActiveFigure() != null) {
                if(game.getActiveFigure().canMove(this)) {
                    game.getActiveFigure().move(this);
                    game.setActiveFigure(null);
                    game.setWhiteTurn(!game.isWhiteTurn());
                }
            } else if(this.figure != null && game.getActiveFigure() == null) {
                if(game.isWhiteTurn() == this.getFigure().isWhite()) {
                    game.setActiveFigure(this.getFigure());
                }
            }
        });
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Field[] getSurrouding() {
        return surrouding;
    }

    public void setSurrouding(Field[] surrouding) {
        this.surrouding = surrouding;
    }

    @Override
    public boolean equals(Object obj) {

        return ((Field)obj).getCol() == col && ((Field)obj).getRow() == row;
    }

}
