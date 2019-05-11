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

        if( fieldCol == moveToCol) {
            for(int row = fieldRow + 1; row <= moveToRow; row++) {
                if (field.getBoard().getField(moveToCol, row).get() != null ){
                    if(row == moveToRow) {
                        if(checkIfFieldContainsEnemyFigure(moveToCol, row)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }else if(fieldRow == moveToRow) {
            for(int col = fieldCol + 1; col <= moveToCol; col++) {
                if (field.getBoard().getField(col, moveToRow).get() != null) {
                    if(col == moveToCol) {
                        if(checkIfFieldContainsEnemyFigure(col, moveToRow)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


}
