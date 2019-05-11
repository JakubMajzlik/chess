package ija.project.chess.factory;

import ija.project.chess.board.Board;
import ija.project.chess.figure.*;
import ija.project.chess.game.Game;

public abstract class GameFactory {

    public static Game createChessGame(Board board) {

        if(board.getSize() != 8) {
            return null;
        }

        // White

        // Towers
        board.getField(0,0).put(new Rook(true));
        board.getField(7,0).put(new Rook(true));

        // Bishop
        board.getField(2,0).put(new Bishop(true));
        board.getField(5,0).put(new Bishop(true));

        // Queen
        board.getField(4,0).put(new Queen(true));

        // King
        board.getField(3,0).put(new King(true));

        // Knight
        board.getField(1,0).put(new Knight(true));
        board.getField(6,0).put(new Knight(true));


        // Pawns
        for (int col = 0; col < 8; col++) {
            board.getField(col,1).put(new Pawn(true));
        }

        //Black

        // Towers
        board.getField(0,7).put(new Rook(false));
        board.getField(7,7).put(new Rook(false));

        // Bishop
        board.getField(2,7).put(new Bishop(false));
        board.getField(5,7).put(new Bishop(false));

        // Queen
        board.getField(4,7).put(new Queen(false));

        // King
        board.getField(3,7).put(new King(false));

        // Knight
        board.getField(1,7).put(new Knight(false));
        board.getField(6,7).put(new Knight(false));

        // Pawns
        for (int col = 0; col < 8; col++) {
            board.getField(col,6).put(new Pawn(false));
        }

        return new Game(board);
    }

}
