package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Bishop extends AbstractFigure {
	public Bishop() {
        super("S", true);
    }

    public Bishop(boolean white) {
        super("S", white);
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
        if(checkCol != checkRow) return false;

        return true;
    }
}
