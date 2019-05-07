package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Knight extends AbstractFigure {
	public Knight() {
        super.figureChar = "K";
    }

    public Knight(boolean white) {
        super.figureChar = "K";
        super.isWhite = white;
    }

    public boolean move(Field moveTo) {
        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        if(canMove(moveTo)) {
        	if(checkIfFieldContainsEnemyFigure(moveToCol, moveToRow)) {
            	//vzhodi a spravi presuun
                return capture(moveToCol, moveToRow) && moveTo.put(this);
            } else {
            	return moveTo.put(this);
            }
        } else return false;
    }

    public boolean canMove(Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        boolean makeMove = false;

        //moznsti na prvom riadku
        int possibleCol = fieldCol + 2;
        int possibleRow = fieldRow - 1;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        possibleRow = fieldRow + 1;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        //moznsti na druhom riadku
        possibleCol = fieldCol + 1;
        possibleRow = fieldRow - 2;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        possibleRow = fieldRow + 2;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        //moznsti na tretom riadku
        possibleCol = fieldCol - 1;
        possibleRow = fieldRow - 2;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        possibleRow = fieldRow + 2;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        //moznsti na stvrtom riadku
        possibleCol = fieldCol - 2;
        possibleRow = fieldRow - 1;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;
        possibleRow = fieldRow + 1;
        if(possibleCol == moveToCol && possibleRow == moveToRow) makeMove = true;

        return makeMove;
    }
    
}
