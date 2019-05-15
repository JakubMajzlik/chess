package ija.project.chess.writer;

import ija.project.chess.exceptions.WrongChessNotationException;
import ija.project.chess.notation.ChessTurnNotation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ChessNotationWriter {

    FileWriter writer;

    public ChessNotationWriter(String targetFileLocation) throws IOException {
        writer = new FileWriter(targetFileLocation);
    }

    public ChessNotationWriter(File targetFile) throws IOException {
        writer = new FileWriter(targetFile);
    }

    /**
    *   Zapise nnotaciu do suboru
    */
    public void appendNotation(ChessTurnNotation notation) throws WrongChessNotationException {
        if(notation == null) {
            return;
        }

        if(notation.getTurnOrder() < 1 || notation.getWhiteTurnNotation() == null) throw new WrongChessNotationException();

        StringBuilder stringNotation = new StringBuilder();
        stringNotation.append(notation.getTurnOrder() + ". " + notation.getWhiteTurnNotation());
        if(notation.getBlackTurnNotation() != null){
            stringNotation.append(" " + notation.getBlackTurnNotation() + "\n");
        } else {
            stringNotation.append("\n");
        }

        try {
            writer.write(stringNotation.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
