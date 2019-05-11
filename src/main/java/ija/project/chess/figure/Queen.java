package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Queen extends AbstractFigure {
	public Queen() {
        super("D", true);
    }

    public Queen(boolean white) {
        super("D", white);
    }
    
    //kralovna sa pohybuje ako veza a strelec  
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
        return canDoTowerMove(moveTo) || canDoBishopMove(moveTo);
    }

    private boolean canDoTowerMove (Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if( fieldCol == moveToCol && fieldRow == moveToRow) return false;

        if( fieldCol == moveToCol) {
            //inkrementujeme row az pokial sa row == movetorow
            for(int row = fieldRow + 1; row <= moveToRow; row++) {
                //vrati figuru ktora stoji vezi v ceste
                if (field.getBoard().getField(moveToCol, row).get() != null ){
                    //ak naslo figuru az na konci cesty
                    if(row == moveToRow) {
                        //ak je to enmy figura
                        if(checkIfFieldContainsEnemyFigure(moveToCol, row)) {
                            //vzhodi a spravi presuun
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            //nic nestoji v ceste srpavi len presun
            return moveTo.put(this);
        }else if(fieldRow == moveToRow) {
            for(int col = fieldCol+1; col <= moveToCol; col++) {
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

    private boolean canDoBishopMove(Field moveTo) {
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
