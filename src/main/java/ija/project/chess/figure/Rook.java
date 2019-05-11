package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Rook extends AbstractFigure {

    public Rook() {
        super("V", true);
    }

    public Rook(boolean white) {
        super("V", white);
    }

    public boolean canMove(Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if( fieldCol == moveToCol && fieldRow == moveToRow) return false;

        int checkCol = Math.abs(fieldCol - moveToCol);
        int checkRow = Math.abs(fieldRow - moveToRow);

        //nemam to overene ale nenasiel som priklad kde by to neplatilo
        if(checkCol == 0 && checkRow > 0 || checkCol > 0 && checkRow == 0);
        else return false;

        return true;
    }


}
