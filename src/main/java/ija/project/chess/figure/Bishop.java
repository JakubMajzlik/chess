package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Bishop extends AbstractFigure {
	public Bishop() {
        super("S", true);
    }

    public Bishop(boolean white) {
        super("S", white);
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

        if( fieldCol == moveToCol && fieldRow == moveToRow) return false;

        int checkCol = Math.abs(fieldCol - moveToCol);
        int checkRow = Math.abs(fieldRow - moveToRow);

        //nemam to overene ale nenasiel som priklad kde by to neplatilo
        if(checkCol != checkRow) return false;

        int x = fieldRow;//x ako os X
        int y = fieldCol;//y ako os Y
        while(true) {
            if(fieldRow < moveToRow) x++;
            else x--;

            if(fieldCol < moveToCol) y++;
            else y--;

            //vrati figuru ktora stoji vezi v ceste
            if (field.getBoard().getField(y, x).get() != null ){
                //ak naslo figuru az na konci cesty
                if( y == moveToCol && x == moveToRow) {
                    //ak je to enmy figura
                    if(checkIfFieldContainsEnemyFigure(y, x)) {
                        //vzhodi a spravi presuun
                        return true;
                        //chceme vyhodit vlastnu figuru
                    } else {
                        return false;
                    }
                    //nejaka figura zavadzia v ceste
                } else {
                    return false;
                }
            }
            //nic nezavadzia
            if( y == moveToCol && x == moveToRow)
                return true;
        }
    }
}
