package ija.project.tests;

import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.reader.ChessNotationReader;
import ija.project.chess.exceptions.WrongChessNotationFileException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ChessChessNotationReaderTests {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private File emptyFile, testFile, wrongFile, wrongFile2;

    @Before
    public void setup() {
        try {
            testFile = folder.newFile("testFile.txt");
            emptyFile = folder.newFile("emptyFile.txt");
            wrongFile = folder.newFile("wrongFile.txt");
            wrongFile2 = folder.newFile("wrongFile2.txt");

            FileWriter testFileWriter = new FileWriter(testFile);
            testFileWriter.append("10. e2e4 e7e5\n");
            testFileWriter.append("2. Sf1c4 Dd8f6\n");
            testFileWriter.append("3. c4c5#\n");
            testFileWriter.close();

            FileWriter wrongFileWriter = new FileWriter(wrongFile);
            wrongFileWriter.append("1 e2e4 e7e5");
            wrongFileWriter.close();

            FileWriter wrongFile2Writer = new FileWriter(wrongFile2);
            wrongFile2Writer.append("1. e2e4 e7e5\n");
            wrongFile2Writer.append("Sf1c4 Dd8f6");
            wrongFile2Writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ChestNotationReaderSimpleFileTest() throws WrongChessNotationFileException {
        ChessNotationReader reader = null;
        try {
            reader = new ChessNotationReader(testFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ChessTurnNotation returnedNotation = reader.getTurnNotation();

        Assert.assertEquals(10, returnedNotation.getTurnOrder());
        Assert.assertEquals("e2e4", returnedNotation.getWhiteTurnNotation());
        Assert.assertEquals("e7e5", returnedNotation.getBlackTurnNotation());

        returnedNotation = reader.getTurnNotation();

        Assert.assertEquals(2, returnedNotation.getTurnOrder());
        Assert.assertEquals("Sf1c4", returnedNotation.getWhiteTurnNotation());
        Assert.assertEquals("Dd8f6", returnedNotation.getBlackTurnNotation());

        returnedNotation = reader.getTurnNotation();

        Assert.assertEquals(3, returnedNotation.getTurnOrder());
        Assert.assertEquals("c4c5#", returnedNotation.getWhiteTurnNotation());
        Assert.assertNull(returnedNotation.getBlackTurnNotation());

        reader.close();
    }

    @Test
    public void ChessNotationReaderEmptyFileTest() throws WrongChessNotationFileException {
        ChessNotationReader reader = null;
        try {
            reader = new ChessNotationReader(emptyFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ChessTurnNotation returnedNotation = reader.getTurnNotation();

        Assert.assertNull(returnedNotation);

        reader.close();
    }

    @Test
    public void ChessNotationReaderWrongFile() throws FileNotFoundException, WrongChessNotationFileException {
        expectedException.expect(WrongChessNotationFileException.class);

        ChessNotationReader reader = new ChessNotationReader(wrongFile);

        ChessTurnNotation returnedNotation = reader.getTurnNotation();

        reader.close();
    }

    @Test
    public void ChessNotationReaderWrongFile2() throws FileNotFoundException, WrongChessNotationFileException {
        expectedException.expect(WrongChessNotationFileException.class);

        ChessNotationReader reader = new ChessNotationReader(wrongFile2);

        ChessTurnNotation returnedNotation = reader.getTurnNotation();
        returnedNotation = reader.getTurnNotation();

        reader.close();
    }

    @Test
    public void ChessNotationReaderFileNotFound() throws FileNotFoundException {
        expectedException.expect(FileNotFoundException.class);

        ChessNotationReader reader = new ChessNotationReader("nonExistingFile");

        reader.close();
    }
}
