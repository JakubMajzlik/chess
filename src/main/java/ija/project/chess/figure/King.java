package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class King extends AbstractFigure {
	public King() {
        super("K", true);
    }

    public King(boolean white) {
        super("K", white);
    }

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

    public boolean canMove(Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if( fieldCol == moveToCol && fieldRow == moveToRow) return false;

        int checkCol = Math.abs(fieldCol - moveToCol);
        int checkRow = Math.abs(fieldRow - moveToRow);

        if(checkCol > 1 || checkRow > 1) return false;

        return true;
    }

}