package ija.project.tests;

import ija.project.chess.exceptions.WrongChessNotationException;
import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.writer.ChessNotationWriter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ChessNotationWriterTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void ChessNotationWriterTestNotation() throws IOException {
        File testFile = new File("TestFile.txt");
        ChessNotationWriter writer = new ChessNotationWriter(testFile);

        ChessTurnNotation notation = new ChessTurnNotation();
        notation.setTurnOrder(1);
        notation.setWhiteTurnNotation("e2e4");
        notation.setBlackTurnNotation("e7e5");

        ChessTurnNotation notation2 = new ChessTurnNotation();
        notation2.setTurnOrder(2);
        notation2.setWhiteTurnNotation("Sf1c4#");
        notation2.setBlackTurnNotation(null);

        try {
            writer.appendNotation(notation);
            writer.appendNotation(notation2);
        } catch (WrongChessNotationException e) {

        }

        writer.close();

        BufferedReader reader = new BufferedReader(new FileReader(testFile));

        Assert.assertEquals("1. e2e4 e7e5", reader.readLine());
        Assert.assertEquals("2. Sf1c4#", reader.readLine());

        reader.close();
    }

    @Test
    public void ChessNotationWriterWrongNotation() throws IOException, WrongChessNotationException {
        File testFile = new File("TestFile.txt");
        ChessNotationWriter writer = new ChessNotationWriter(testFile);

        //Turn order missing
        ChessTurnNotation notation = new ChessTurnNotation();
        notation.setWhiteTurnNotation("e2e4");
        notation.setBlackTurnNotation("e7e5");

        ChessTurnNotation notation2 = new ChessTurnNotation();
        notation2.setTurnOrder(2);
        notation2.setWhiteTurnNotation("Sf1c4#");
        notation2.setBlackTurnNotation(null);

        expectedException.expect(WrongChessNotationException.class);

        writer.appendNotation(notation);
        writer.appendNotation(notation2);

        writer.close();

    }
    @Test
    public void ChessNotationWriterWrongNotation2() throws IOException, WrongChessNotationException {
        File testFile = new File("TestFile.txt");
        ChessNotationWriter writer = new ChessNotationWriter(testFile);

        //White turn missing
        ChessTurnNotation notation = new ChessTurnNotation();
        notation.setTurnOrder(1);
        notation.setBlackTurnNotation("e7e5");

        ChessTurnNotation notation2 = new ChessTurnNotation();
        notation2.setTurnOrder(2);
        notation2.setWhiteTurnNotation("Sf1c4#");
        notation2.setBlackTurnNotation(null);

        expectedException.expect(WrongChessNotationException.class);

        writer.appendNotation(notation);
        writer.appendNotation(notation2);

        writer.close();

    }
}
