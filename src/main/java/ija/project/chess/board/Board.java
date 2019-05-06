package ija.project.chess.board;

import ija.project.chess.field.Field;
import ija.project.chess.figure.Figure;
import javafx.scene.image.ImageView;

public class Board {

    int size;

    Field[][] field;

    Field startField;

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

    public Figure findFigure(Figure figureType, Integer column, Integer row) {
        Figure figure = null;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                Figure foundedFigure = field[i][j].get();
                // TODO: foundedFigure.getClass().isInstance(figureType) vobec netusim ci to ide
                if(foundedFigure != null && foundedFigure.getClass().isInstance(figureType)) {
                    // TODO
                    /*
                    if(column != null && column.intValue() == figure.getColumn()) {
                        figure = foundedFigure;
                    } else if(row != null && row.intValue() == figure.getRow()) {
                        figure = foundedFigure;
                    }
                     */
                }
            }
        }

        return figure;
    }
}
