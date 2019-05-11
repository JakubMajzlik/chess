package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Pawn extends AbstractFigure {

    public Pawn(boolean white) {
        super("p", white);
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

        if( moveTo.equals(field) || fieldCol != moveToCol) return false;

        if(isWhite && fieldRow >= moveToRow) return false;
        else if(!isWhite && fieldRow <= moveToRow) return false;
        return true;
    }
}
