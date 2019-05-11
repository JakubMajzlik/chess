package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Queen extends AbstractFigure {
	public Queen() {
        super("D", true);
    }

    public Queen(boolean white) {
        super("D", white);
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
        if(checkCol == 0 && checkRow > 0 || checkCol > 0 && checkRow == 0 || checkCol == checkRow);
        else return false;

        return true;
    }

}
