package ija.project.chess.reader;

import ija.project.chess.exceptions.WrongChessNotationFileException;
import ija.project.chess.notation.ChessTurnNotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChessNotationReader {

    private FileReader fileReader;

    public ChessNotationReader(String fileLocation) throws FileNotFoundException {
        fileReader = new FileReader(fileLocation);
    }

    public ChessNotationReader(File file) throws FileNotFoundException {
        fileReader = new FileReader(file);
    }

    public ChessTurnNotation getTurnNotation() throws WrongChessNotationFileException {
        StringBuilder turnString = new StringBuilder();
        ChessTurnNotation chessTurnNotation = new ChessTurnNotation();
        int character = 0;

        // Turn order
        try {
            int turnOrder = fileReader.read();
            StringBuilder orderString = new StringBuilder();

            if(turnOrder == -1) return null;

            while (turnOrder != '.'){
                // Doesnt start with number
                if((turnOrder < 48 || turnOrder > 57) && (turnOrder != '.')) throw new WrongChessNotationFileException();

                orderString.append((char)turnOrder);

                turnOrder = fileReader.read();
            }

            chessTurnNotation.setTurnOrder(Integer.parseInt(orderString.toString()));

            // Skipping space
            fileReader.skip(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // White turn
        if(setTurnString(turnString)) {
            chessTurnNotation.setBlackTurnNotation(null);
            chessTurnNotation.setWhiteTurnNotation(turnString.toString());
            return chessTurnNotation;
        }

        chessTurnNotation.setWhiteTurnNotation(turnString.toString());

        turnString = new StringBuilder();

        //Black turn
        if(setTurnString(turnString)) {
            chessTurnNotation.setBlackTurnNotation(turnString.toString());
        }
        return chessTurnNotation;
    }

    private boolean setTurnString(StringBuilder turnString) {
        int character = 0;
        do {
            try {
                character = fileReader.read();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            turnString.append((char) character);

        } while(character != '\n' && character != ' ' && character != -1);

        turnString.deleteCharAt(turnString.length() - 1);

        //Is checkmate?
        if(character == -1 || character == '\n') {
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
