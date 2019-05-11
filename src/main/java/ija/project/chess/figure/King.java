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

        if(canMove(moveTo)) {
            if (field.getBoard().getField(moveToCol, moveToRow).get() != null ){
                //ak je to enmy figura
                if(checkIfFieldContainsEnemyFigure(moveToCol, moveToRow)) {
                    //vzhodi a spravi presuun
                    return capture(moveToCol, moveToRow) && moveTo.put(this);
                    //chceme vyhodit vlastnu figuru
                } else {
                    return false;
                }
            } else {
                return moveTo.put(this);
            }
        }

        return false;
    }

    @Override
    public boolean canMove(Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if( fieldCol == moveToCol && fieldRow == moveToRow) return false;

        int checkCol = Math.abs(fieldCol - moveToCol);
        int checkRow = Math.abs(fieldRow - moveToRow);
        if(checkCol > 1 || checkRow > 1) return false;
        //vrati figuru ktora stoji kralovi v ceste
        if (field.getBoard().getField(moveToCol, moveToRow).get() != null ){
            //ak je to enmy figura
            if(checkIfFieldContainsEnemyFigure(moveToCol, moveToRow)) {
                //vzhodi a spravi presuun
                return true;
                //chceme vyhodit vlastnu figuru
            } else {
                return false;
            }
        }
        return true;
    }

}