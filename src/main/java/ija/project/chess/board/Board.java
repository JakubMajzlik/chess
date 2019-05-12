package ija.project.chess.board;

import ija.project.chess.field.Field;
import ija.project.chess.figure.Figure;
import ija.project.chess.game.Game;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Board {

    int size;

    Field[][] field;

    Field startField;

    private List<Figure> capturedFigures = new ArrayList<>();

    private Game game;

    public Board(int size) {
        field = new Field[size][size];
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Field boardField = new Field(col, row);
                boardField.setBoard(this);

                if(row % 2 == 0) {
                    if(col % 2 == 0) {
                        boardField.setBackground(new ImageView("images/white.png"));
                    }else {
                        boardField.setBackground(new ImageView("images/black.png"));
                    }
                } else {
                    if(col % 2 == 1) {
                        boardField.setBackground(new ImageView("images/white.png"));
                    } else {
                        boardField.setBackground(new ImageView("images/black.png"));
                    }
                }

                field[col][row] = boardField;
                boardField.handleEvents();
            }
        }

        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Field boardField = field[col][row];

                if(col > 0)	boardField.addNextField(Field.Direction.L, field[col-1][row]);
                if(col+1 < size) boardField.addNextField(Field.Direction.R, field[col+1][row]);
                if(row > 0) boardField.addNextField(Field.Direction.D, field[col][row-1]);
                if(row+1 < size) boardField.addNextField(Field.Direction.U, field[col][row+1]);
                if(col > 0 && row > 0) boardField.addNextField(Field.Direction.LD, field[col-1][row-1]);
                if(col+1 < size && row+1 < size) boardField.addNextField(Field.Direction.RU, field[col+1][row+1]);
                if(col+1 < size && row > 0) boardField.addNextField(Field.Direction.RD, field[col+1][row-1]);
                if(col > 0 && row+1 < size) boardField.addNextField(Field.Direction.LU, field[col-1][row+1]);
            }

        }


        this.size = size;
    }

    public Field getField(int col, int row) {
        if(col > size - 1  || col < 0
                || row > size - 1  || row < 0) {
            return null;
        }
        return field[col][row];
    }

    public int getSize() {
        return size;
    }

    public Figure findFigure(String figureType, Integer column, Integer row, Field canMoveTo, boolean isWhite) {
        Figure figure = null;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                Figure foundedFigure = field[i][j].get();

                if(foundedFigure != null && foundedFigure.isWhite() == isWhite
                    && foundedFigure.getFigureChar().equals(figureType)) {

                    //TODO: Mozno to teraz rozbijem
                    foundedFigure.setField(field[i][j]);

                    if(column != null && column.intValue() == foundedFigure.getField().getCol()) {
                        figure = foundedFigure;
                    } else if(row != null && row.intValue() == foundedFigure.getField().getRow()) {
                        figure = foundedFigure;
                    } else if(column == null && row == null) {
                        if(canMoveTo != null) {
                            if(foundedFigure.canMove(canMoveTo)) {
                                figure = foundedFigure;
                            }
                        } else {
                            figure = foundedFigure;
                        }
                    }

                }
            }
        }

        return figure;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Figure> getCapturedFigures() {
        return capturedFigures;
    }

    public void setCapturedFigures(List<Figure> capturedFigures) {
        this.capturedFigures = capturedFigures;
    }
}
