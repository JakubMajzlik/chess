package ija.project.chess.figure;

import ija.project.chess.board.Board;
import ija.project.chess.field.Field;
import javafx.scene.image.ImageView;

public abstract class AbstractFigure implements Figure{

    protected Field field;
    protected boolean isWhite;
    protected String figureChar;
    protected ImageView image;

    public AbstractFigure() {
        switch(figureChar) {
            case "P":
                if(isWhite) {
                    image = new ImageView("images/wpawn.png");
                } else {
                    image = new ImageView("images/bpawn.png");
                }
                break;
            case "Ki":
                if(isWhite) {
                    image = new ImageView("images/wking.png");
                } else {
                    image = new ImageView("images/bking.png");
                }
                break;
            case "Q":
                if(isWhite) {
                    image = new ImageView("images/wqueen.png");
                } else {
                    image = new ImageView("images/bqueen.png");
                }
                break;
            case "B":
                if(isWhite) {
                    image = new ImageView("images/wbishop.png");
                } else {
                    image = new ImageView("images/bbishop.png");
                }
                break;
            case "R":
                if(isWhite) {
                    image = new ImageView("images/wrook.png");
                } else {
                    image = new ImageView("images/brook.png");
                }
                break;
            case "K":
                if(isWhite) {
                    image = new ImageView("images/wknight.png");
                } else {
                    image = new ImageView("images/bknight.png");
                }
                break;
            default:
                break;
        }
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getState() {
        StringBuilder stateMessage = new StringBuilder();
        stateMessage.append(figureChar + "[");
        stateMessage.append(isWhite ? "W" : "B");
        stateMessage.append("]");
        stateMessage.append(field.getCol() + ":" + field.getRow());
        return stateMessage.toString();
    }

    protected boolean capture(int col, int row) {
        Board board = field.getBoard();
        Figure figureToRemove = board.getField(col, row).get();
        if(figureToRemove != null && figureToRemove.isWhite() != isWhite) {
            board.getField(col, row).remove(figureToRemove);
            return true;
        }
        return false;
    }

    protected boolean checkIfFieldContainsEnemyFigure(int col, int row) {
        Figure figureAtField = field.getBoard().getField(col, row).get();
        if(figureAtField != null) {
            return isWhite != figureAtField.isWhite();
        }
        return false;
    }

    @Override
    public ImageView getImage() {
        return image;
    }
}
