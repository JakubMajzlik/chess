package ija.project.chess.factory;

import ija.project.chess.board.Board;
import ija.project.chess.game.Game;

public abstract class GameFactory {

    public static Game createChessGame(Board board) {

        if(board.getSize() != 8) {
            return null;
        }
        // TODO
        // White

        // Towers
        //board.getField(1,1).put(new Tower(true));
        //board.getField(8,1).put(new Tower(true));

        //
        //board.getField(3,1).put(new Archer(true));
        //board.getField(6,1).put(new Archer(true));

        // Queen
        //board.getField(5,1).put(new Queen(true));

        // King
        //board.getField(4,1).put(new King(true));

        // Horse
        //board.getField(2,1).put(new Horse(true));
        //board.getField(7,1).put(new Horse(true));


        // Pawns
        for (int col = 1; col <= 8; col++) {
            //board.getField(col,2).put(new Pawn(true));
        }

        //Black

        // Towers
        //board.getField(1,8).put(new Tower(false));
        //board.getField(8,8).put(new Tower(false));

        //
        //board.getField(3,8).put(new Archer(false));
        //board.getField(6,8).put(new Archer(false));

        // Queen
        //board.getField(5,8).put(new Queen(false));

        // King
        //board.getField(4,8).put(new King(false));

        // Horse
        //board.getField(2,8).put(new Horse(false));
        //board.getField(7,8).put(new Horse(false));
        // Pawns
        for (int col = 1; col <= 8; col++) {
            //board.getField(col,7).put(new Pawn(false));
        }

        return new Game(board);
    }

}
