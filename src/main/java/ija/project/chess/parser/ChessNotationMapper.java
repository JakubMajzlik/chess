package ija.project.chess.parser;

import ija.project.chess.board.Board;
import ija.project.chess.exceptions.ChessNotationMapperException;
import ija.project.chess.field.Field;
import ija.project.chess.figure.*;
import ija.project.chess.game.Game;
import ija.project.chess.notation.ChessTurnNotation;
import ija.project.chess.turn.Turn;

import java.util.HashMap;
import java.util.Map;

public class ChessNotationMapper {

    private Game game;
    private Board board;
    private Board originalBoard;
    private Map<Figure, Field> originalFigurePosition = new HashMap<>();

    public ChessNotationMapper() {}

    public ChessNotationMapper(Game game) {

        this.game = game;
        board = new Board(8);
        originalBoard = game.getBoard();
        replaceBoard();

    }

    public void replaceBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = originalBoard.getField(i,j).get();
                if(figure != null) {
                    //Field originalField = new Field(figure.getField().getCol(), figure.getField().getRow());
                    Field originalField = figure.getField();
                    originalField.setBoard(originalBoard);
                    originalField.setFigure(figure);

                    originalFigurePosition.put(figure, originalField);
                    figure.setField(board.getField(i,j));
                    board.getField(i,j).put(figure);
                }
            }
        }
    }

    public void recoverBoard() {
        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = board.getField(i,j).get();
                if(figure != null) {
                    Field originalField = originalFigurePosition.get(figure);
                    figure.setField(originalField);
                }
            }
        }*/
        originalFigurePosition.forEach(((figure, field) -> {
            figure.setField(field);
            System.out.println(figure.getState());
        }));
    }

    public Turn getTurn(ChessTurnNotation notation) throws ChessNotationMapperException {
        Turn turn = new Turn();



        turn.setTurnOrder(notation.getTurnOrder());

        setTurnAttributes(notation, turn, true);

        if(notation.getBlackTurnNotation() != null && notation.getBlackTurnNotation().length() > 0) {
            setTurnAttributes(notation, turn, false);
        }



        return turn;
    }

    public ChessTurnNotation getNotation(Turn turn) {
        ChessTurnNotation notation = new ChessTurnNotation();
        StringBuilder whiteNotation = new StringBuilder();
        StringBuilder blackNotation = new StringBuilder();

        // Figure
        if(turn.getWhiteFigure().getFigureChar() != "p") {
            whiteNotation.append(turn.getWhiteFigure().getFigureChar());
        }
        if(turn.getBlackFigure() != null && turn.getBlackFigure().getFigureChar() != "p") {
            blackNotation.append(turn.getBlackFigure().getFigureChar());
        }

        // Specification of figure
        if(turn.isSpecifyColumnForWhite()) {
            whiteNotation.append((char)(turn.getWhiteSourceField().getCol() + 'a'));
        } else if(turn.isSpecifyRowForWhite()) {
            whiteNotation.append(turn.getWhiteSourceField().getRow());
        }
        if(turn.getBlackFigure() != null && turn.isSpecifyColumnForBlack()) {
            blackNotation.append((char)(turn.getBlackSourceField().getCol() + 'a'));
        } else if(turn.getBlackFigure() != null && turn.isSpecifyRowForBlack()) {
            whiteNotation.append(turn.getBlackSourceField().getRow());
        }

        // Defends
        if(turn.isBlackDefends()) {
            whiteNotation.append("x");
        }
        if(turn.isWhiteDefends()) {
            blackNotation.append("x");
        }

        // Destination
        whiteNotation.append((char)(turn.getWhiteDestinationField().getCol() + 'a'));
        whiteNotation.append(turn.getWhiteDestinationField().getRow() + 1);
        if(turn.getBlackFigure() != null) {
            blackNotation.append((char)(turn.getBlackDestinationField().getCol() + 'a'));
            blackNotation.append(turn.getBlackDestinationField().getRow() + 1);
        }


        // Pawn upgrade
        if(turn.getWhiteFigure().getFigureChar() == "p"
            && turn.getWhitePawnUpgradesTo() != null) {
            whiteNotation.append(turn.getWhitePawnUpgradesTo().getFigureChar());
        }
        if(turn.getBlackFigure() != null && turn.getBlackFigure().getFigureChar() == "p"
            && turn.getBlackPawnUpgradesTo() != null) {
            blackNotation.append(turn.getBlackPawnUpgradesTo().getFigureChar());
        }

        // Check or Check Mate
        if(turn.isBlackCheck()) {
            whiteNotation.append("+");
        } else if(turn.isBlackCheckMate()) {
            whiteNotation.append("#");
        }
        if(turn.isWhiteCheck()) {
            blackNotation.append("+");
        } else if(turn.isWhiteCheckMate()) {
            blackNotation.append("#");
        }

        notation.setTurnOrder(turn.getTurnOrder());
        notation.setWhiteTurnNotation(whiteNotation.toString());
        notation.setBlackTurnNotation(blackNotation.toString());

        return notation;
    }

    private void setTurnAttributes(ChessTurnNotation notation, Turn turn, boolean isWhite) throws ChessNotationMapperException {

        // Figure type
        turn = getFigure(turn, notation, isWhite);

        // Figure move
        turn = getDestination(turn, notation, isWhite);

        //Check Check or Check mate
        turn = checkCheckOrCheckMate(turn, notation, isWhite);
    }

    private Turn getFigure(Turn turn, ChessTurnNotation notation, boolean isWhite) throws ChessNotationMapperException {
        char c;
        char nextC;
        char nextNextC = 0;
        char nextNextNextC = 0;
        Figure figure = null;

        if(isWhite) {
            c = notation.getWhiteTurnNotation().charAt(0);
            nextC = notation.getWhiteTurnNotation().charAt(1);
            if(notation.getWhiteTurnNotation().length() > 2) {
                nextNextC = notation.getWhiteTurnNotation().charAt(2);
            }
            if(notation.getWhiteTurnNotation().length() > 3) {
                nextNextNextC = notation.getWhiteTurnNotation().charAt(3);
            }


        } else {
            c = notation.getBlackTurnNotation().charAt(0);
            nextC = notation.getBlackTurnNotation().charAt(1);
            if(notation.getBlackTurnNotation().length() > 2){
                nextNextC = notation.getBlackTurnNotation().charAt(2);
            }
            if(notation.getBlackTurnNotation().length() > 3){
                nextNextNextC = notation.getBlackTurnNotation().charAt(3);
            }

        }

        switch (c) {
            case 'K': case 'D':

                //figure = game.getBoard().findFigure(Character.toString(c), null, null,null, isWhite);
                figure = board.findFigure(Character.toString(c), null, null,null, isWhite);
                break;

            case 'V': case 'S': case 'J':
                if (isCharacterInRangeFromaToh(nextC) && isCharacterInRangeFromaToh(nextNextC) && nextNextNextC > 0) {
                    int column = nextC - 'a';
                    Field moveToField;

                    //moveToField = game.getBoard().getField(nextNextC - 'a', nextNextNextC - '1');
                    moveToField = board.getField(nextNextC - 'a', nextNextNextC - '1');

                    //figure = game.getBoard().findFigure(Character.toString(c), column, null, moveToField, isWhite);
                    figure = board.findFigure(Character.toString(c), column, null, moveToField, isWhite);
                } else if (isCharacterInRangeFrom1To8(nextC) && isCharacterInRangeFromaToh(nextNextC)
                        && nextNextNextC > 0) {
                    int row = nextC - '1';
                    Field moveToField;

                    //moveToField = game.getBoard().getField(nextNextC - 'a', nextNextNextC - '1');
                    moveToField = board.getField(nextNextC - 'a', nextNextNextC - '1');

                    //figure = game.getBoard().findFigure(Character.toString(c), null, row,  moveToField,isWhite);
                    figure = board.findFigure(Character.toString(c), null, row,  moveToField,isWhite);
                } else {
                    Field moveToField;

                    //moveToField = game.getBoard().getField(nextC - 'a', nextNextC - '1');
                    moveToField = board.getField(nextC - 'a', nextNextC - '1');

                    //figure = game.getBoard().findFigure(Character.toString(c), null, null,  moveToField, isWhite);
                    figure = board.findFigure(Character.toString(c), null, null,  moveToField, isWhite);
                }

                if (figure == null || !figure.getFigureChar().equals(Character.toString(c))) {
                    throw new ChessNotationMapperException();
                }

                break;

            default:
                if(c >= 'a' && c <= 'g'){
                    int column = c - 'a';

                    if(isCharacterInRangeFrom1To8(nextC)) {
                        Field moveToField;
                        moveToField = board.getField(c - 'a', nextC - '1');
                        //figure = game.getBoard().findFigure("p", column, null, null ,isWhite);
                        figure = board.findFigure("p", null, null, moveToField ,isWhite);

                        if(figure == null) {
                            throw new ChessNotationMapperException();
                        }
                    } else {
                        throw new ChessNotationMapperException();
                    }
                    break;
                } else {
                    throw new ChessNotationMapperException();
                }
        }
        setFigure(turn, isWhite, figure);
        return turn;
    }

    private Turn getDestination(Turn turn, ChessTurnNotation notation, boolean isWhite) throws ChessNotationMapperException {
        //TODO: priradit rozumne mena premennych + kontrola na dlzku??
        char c;
        char nextC = 0;
        char nextNextC = 0 ;
        if(isWhite) {
            if(turn.getWhiteFigure().getFigureChar().equals("p")) {
                c = notation.getWhiteTurnNotation().charAt(0);
                nextC = notation.getWhiteTurnNotation().charAt(1);
                if(notation.getWhiteTurnNotation().length() >= 3) {
                     nextNextC = notation.getWhiteTurnNotation().charAt(2);
                }
                if(c == 'x') {
                    turn.setBlackDefends(true);
                    c = notation.getWhiteTurnNotation().charAt(1);
                    nextC = notation.getWhiteTurnNotation().charAt(2);

                    if(notation.getWhiteTurnNotation().length() >= 4) {
                         nextNextC = notation.getWhiteTurnNotation().charAt(3);
                    }
                }
            } else {
                c = notation.getWhiteTurnNotation().charAt(1);
                nextC = notation.getWhiteTurnNotation().charAt(2);

                if(notation.getWhiteTurnNotation().length() >= 4) {
                     nextNextC = notation.getWhiteTurnNotation().charAt(3);
                }
                if(c == 'x') {
                    turn.setBlackDefends(true);
                    c = notation.getWhiteTurnNotation().charAt(2);
                    nextC = notation.getWhiteTurnNotation().charAt(3);

                    if(notation.getWhiteTurnNotation().length() >= 5) {
                         nextNextC = notation.getWhiteTurnNotation().charAt(4);
                    }
                }
            }

        } else {
            if(turn.getBlackFigure().getFigureChar().equals("p")) {
                c = notation.getBlackTurnNotation().charAt(0);
                nextC = notation.getBlackTurnNotation().charAt(1);

                if(notation.getBlackTurnNotation().length() >= 3) {
                    nextNextC = notation.getBlackTurnNotation().charAt(2);
                }
                if(c == 'x') {
                    turn.setWhiteDefends(true);
                    c = notation.getBlackTurnNotation().charAt(1);
                    nextC = notation.getBlackTurnNotation().charAt(2);

                    if(notation.getBlackTurnNotation().length() >= 4) {
                        nextNextC = notation.getBlackTurnNotation().charAt(3);
                    }
                }
            } else {
                c = notation.getBlackTurnNotation().charAt(1);
                nextC = notation.getBlackTurnNotation().charAt(2);

                if(notation.getBlackTurnNotation().length() >= 4) {
                    nextNextC = notation.getBlackTurnNotation().charAt(3);
                }
                if(c == 'x') {
                    turn.setWhiteDefends(true);
                    c = notation.getBlackTurnNotation().charAt(2);
                    nextC = notation.getBlackTurnNotation().charAt(3);

                    if(notation.getBlackTurnNotation().length() >= 5) {
                        nextNextC = notation.getBlackTurnNotation().charAt(4);
                    }
                }
            }
        }

        // TODO: premena pesiaka na nieco ine
        if(isCharacterInRangeFromaToh(c)) {
            if(nextC > 0) {
                if(isCharacterInRangeFromaToh(nextC)) {
                    if(nextNextC > 0) {
                        if(isCharacterInRangeFrom1To8(nextNextC)) {
                            if(isWhite) {
                                if(!turn.getWhiteFigure().canMove(board.getField(nextC- 'a', nextNextC - '1')))
                                throw new ChessNotationMapperException();
                            } else {
                                if(!turn.getBlackFigure().canMove(board.getField(nextC- 'a', nextNextC - '1')))
                                throw new ChessNotationMapperException();
                            }
                            setSourceAndDestination(turn, isWhite, nextC, nextNextC);

                        } else {
                            throw new ChessNotationMapperException();
                        }
                    }
                } else if(isCharacterInRangeFrom1To8(nextC)) {
                    if(isWhite) {
                        if(!turn.getWhiteFigure().canMove(board.getField(c- 'a', nextC - '1')))
                        throw new ChessNotationMapperException();
                    } else {
                        if(!turn.getBlackFigure().canMove(board.getField(c- 'a', nextC - '1')))
                        throw new ChessNotationMapperException();
                    }

                    setSourceAndDestination(turn, isWhite, c, nextC);
                }
            }

        } else if(isCharacterInRangeFrom1To8(c)) {
            if(nextC > 0) {
                if(isCharacterInRangeFromaToh(nextC)) {
                    if(nextNextC > 0) {
                        if(isCharacterInRangeFrom1To8(nextNextC)) {
                            if(isWhite) {
                                if(!turn.getWhiteFigure().canMove(board.getField(nextC- 'a', nextNextC - '1')))
                                throw new ChessNotationMapperException();
                            } else {
                                if(!turn.getBlackFigure().canMove(board.getField(nextC- 'a', nextNextC - '1')))
                                throw new ChessNotationMapperException();
                            }
                            setSourceAndDestination(turn, isWhite, nextC, nextNextC);
                        } else {
                            throw new ChessNotationMapperException();
                        }
                    }


                } else {
                    throw new ChessNotationMapperException();
                }
            }
        }

        return turn;
    }



    private Turn checkCheckOrCheckMate(Turn turn, ChessTurnNotation notation, boolean isWhite) {
        if(isWhite) {
            if(notation.getWhiteTurnNotation().endsWith("#")) {
                turn.setBlackCheckMate(true);
            } else if(notation.getWhiteTurnNotation().endsWith("+")) {
                turn.setBlackCheck(true);
            }
        } else {
            if(notation.getBlackTurnNotation().endsWith("#")) {
                turn.setWhiteCheckMate(true);
            } else if(notation.getBlackTurnNotation().endsWith("+")) {
                turn.setWhiteCheck(true);
            }
        }

        return turn;
    }

    private boolean isCharacterInRangeFromaToh(char c) {
        return c >= 'a' && c <= 'g';
    }

    private boolean isCharacterInRangeFrom1To8(char c) {
        return c > '0' && c< '9';
    }

    private void setFigure(Turn turn, boolean isWhite, Figure figure) {
        if(isWhite) {
            turn.setWhiteFigure(figure);
        } else {
            turn.setBlackFigure(figure);
        }
    }

    private void setSourceAndDestination(Turn turn, boolean isWhite, char column, char row) {
        if (isWhite) {
            Field sourceField = new Field(turn.getWhiteFigure().getField().getCol(), turn.getWhiteFigure().getField().getRow());
            sourceField.setFigure(turn.getWhiteFigure());

            turn.setWhiteSourceField(sourceField);


            Field destinationField = new Field(column - 'a', row - '1');
            destinationField.setFigure(board.getField(column - 'a', row - '1').get());

            turn.setWhiteDestinationField(destinationField);


            board.getField(turn.getWhiteFigure().getField().getCol(), turn.getWhiteFigure().getField().getRow()).setFigure(null);
            board.getField(column - 'a', row - '1').setFigure(turn.getWhiteFigure());
            turn.getWhiteFigure().setField(destinationField);


        } else {
            Field sourceField = new Field(turn.getBlackFigure().getField().getCol(), turn.getBlackFigure().getField().getRow());
            sourceField.setFigure(turn.getBlackFigure());

            turn.setBlackSourceField(sourceField);

            Field destinationField = new Field(column - 'a', row - '1');
            destinationField.setFigure(board.getField(column - 'a', row - '1').get());

            turn.setBlackDestinationField(destinationField);

            board.getField(turn.getBlackFigure().getField().getCol(), turn.getBlackFigure().getField().getRow()).setFigure(null);
            board.getField(column - 'a', row - '1').setFigure(turn.getBlackFigure());
            turn.getBlackFigure().setField(destinationField);

        }
    }

//    private Figure getNonPawnFigure(String figureType, char nextC, Figure figure, boolean isWhite) throws ChessNotationMapperException {
//        if (isCharacterInRangeFromaToh(nextC)) {
//            int column = nextC - 'a';
//            figure = game.getBoard().findFigure(figureType, column, null, isWhite);
//        } else if (isCharacterInRangeFrom1To8(nextC)) {
//            int row = nextC - '1';
//            figure = game.getBoard().findFigure(figureType, null, row, isWhite);
//        } else {
//            throw new ChessNotationMapperException();
//        }
//
//        if (figure == null || !figure.getFigureChar().equals(figureType)) {
//            throw new ChessNotationMapperException();
//        }
//        return figure;
//    }
}
