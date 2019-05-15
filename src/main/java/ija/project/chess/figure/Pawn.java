package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Pawn extends AbstractFigure {

    public Pawn(boolean white) {
        super("p", white);
    }

    /**
    *   Skontroluje, ci sa figurka moze pohnut
    *   @param moveTo Policko kde sa ma pohnut
    */
    @Override
    public boolean canMove(Field moveTo) {

            int fieldCol = field.getCol();
            int fieldRow = field.getRow();


            int moveToCol = moveTo.getCol();
            int moveToRow = moveTo.getRow();

            if( moveTo.equals(field)) return false;


            if(fieldCol == moveToCol) {
                if(isWhite ){
                    if(fieldRow >= moveToRow) return false;
                    else if(fieldRow == 1 && moveToRow > 3) return false;
                    else if(fieldRow > 1 && (moveToRow - fieldRow) > 1) return false;
                    else if(moveTo.get() != null) return false;

                } else {
                    if(fieldRow <= moveToRow) return false;
                    else if(fieldRow == 6 && moveToRow < 4) return false;
                    else if(fieldRow < 6 && (fieldRow - moveToRow) > 1) return false;
                    else if(moveTo.get() != null) return false;
                }
            } else {


                if(Math.abs(fieldCol - moveToCol) != 1) return false;
                else if(Math.abs(moveToRow - fieldRow) != 1) return false;
                else if(moveTo.get() == null) return false;
                else if(!checkIfFieldContainsEnemyFigure(moveToCol, moveToRow)) return false;
            }
            return true;
        }
}
