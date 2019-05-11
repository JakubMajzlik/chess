package ija.project.chess.figure;

import ija.project.chess.board.Board;
import ija.project.chess.field.Field;
import ija.project.chess.game.Game;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public abstract class AbstractFigure implements Figure{

    protected Field field;
    protected boolean isWhite;
    protected String figureChar;
    protected ImageView image;

    public AbstractFigure(String figureChar, boolean isWhite) {
        this.figureChar = figureChar;
        this.isWhite = isWhite;

        switch(figureChar) {
            case "p":
                if(isWhite) {
                    image = new ImageView("images/wpawn.png");
                } else {
                    image = new ImageView("images/bpawn.png");
                }
                break;
            case "K":
                if(isWhite) {
                    image = new ImageView("images/wking.png");
                } else {
                    image = new ImageView("images/bking.png");
                }
                break;
            case "D":
                if(isWhite) {
                    image = new ImageView("images/wqueen.png");
                } else {
                    image = new ImageView("images/bqueen.png");
                }
                break;
            case "S":
                if(isWhite) {
                    image = new ImageView("images/wbishop.png");
                } else {
                    image = new ImageView("images/bbishop.png");
                }
                break;
            case "V":
                if(isWhite) {
                    image = new ImageView("images/wrook.png");
                } else {
                    image = new ImageView("images/brook.png");
                }
                break;
            case "J":
                if(isWhite) {
                    image = new ImageView("images/wknight.png");
                } else {
                    image = new ImageView("images/bknight.png");
                }
                break;
            default:
                break;
        }

        handleEvents();

    }

    private void handleEvents() {
        image.setOnMouseClicked(e ->{
            Game game = this.field.getBoard().getGame();
            Figure activeFigure = game.getActiveFigure();
            if(game.isWhiteTurn() == this.isWhite()) {
                if(activeFigure == null) {
                    game.setActiveFigure(this);
                } else if(activeFigure.isWhite() == this.isWhite()) {
                    game.setActiveFigure(this);
                }
                System.out.println("Clicked on: " + this.getState());
            } else {
                if(game.getActiveFigure() != null) {
                    if(game.getActiveFigure().canMove(this.field)) {
                        game.move(game.getActiveFigure(), this.field);
                        game.setActiveFigure(null);
                        game.setWhiteTurn(!game.isWhiteTurn());
                    }
                }
            }
        });
    }

    @Override
    public String getFigureChar() {
        return figureChar;
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

    @Override
    public boolean equals(Object obj) {
        Figure figure = (Figure)obj;
        if(figure.getState().equals(getState()))
            return true;
        return false;
    }
}
