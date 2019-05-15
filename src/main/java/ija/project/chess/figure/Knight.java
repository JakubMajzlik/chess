package ija.project.chess.figure;

import ija.project.chess.field.Field;

public class Knight extends AbstractFigure {
	public Knight() {
        super("J", true);
    }

    public Knight(boolean white) {
        super("J", white);
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

    /**
    *   Skontroluje, ci sa figurka moze pohnut
    *   @param moveTo Policko kde sa ma pohnut
    */
    public boolean canMove(Field moveTo) {
        int fieldCol = field.getCol();
        int fieldRow = field.getRow();

        int moveToCol = moveTo.getCol();
        int moveToRow = moveTo.getRow();

        boolean makeMove = false;


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
