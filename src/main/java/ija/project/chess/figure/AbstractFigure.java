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
                    image = new ImageView("/ija/project/chess/images/wpawn.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/bpawn.png");
                }
                break;
            case "K":
                if(isWhite) {
                    image = new ImageView("/ija/project/chess/images/wking.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/bking.png");
                }
                break;
            case "D":
                if(isWhite) {
                    image = new ImageView("/ija/project/chess/images/wqueen.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/bqueen.png");
                }
                break;
            case "S":
                if(isWhite) {
                    image = new ImageView("/ija/project/chess/images/wbishop.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/bbishop.png");
                }
                break;
            case "V":
                if(isWhite) {
                    image = new ImageView("/ija/project/chess/images/wrook.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/brook.png");
                }
                break;
            case "J":
                if(isWhite) {
                    image = new ImageView("/ija/project/chess/images/wknight.png");
                } else {
                    image = new ImageView("/ija/project/chess/images/bknight.png");
                }
                break;
            default:
                break;
        }

        handleEvents();

    }

    /**
    *   Zachitava udalosti kliknutia
    */
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

    /**
    *   Vyhodi superovu figurku
    *   @param col stlpec
    *   @param row raidok
    */
    protected boolean capture(int col, int row) {
        Board board = field.getBoard();
        Figure figureToRemove = board.getField(col, row).get();
        if(figureToRemove != null && figureToRemove.isWhite() != isWhite) {
            board.getField(col, row).remove(figureToRemove);
            return true;
        }
        return false;
    }

    /**
    *   Skontroluje, ci sa na zadanej suradnici nachadza protihrac
    *   @param col stlpec
    *   @param row raidok
    */
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


    /**
    *   Pohne s figurkou
    */
    public boolean move(Field moveTo) {
        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if(canMove(moveTo) == false) return false;

        if(checkIfFieldContainsEnemyFigure(moveToCol, moveToRow)) {
            return capture(moveToCol, moveToRow) && moveTo.put(this);
        } else {
            return moveTo.put(this);
        }
    }
}
