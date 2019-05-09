package ija.project.chess.turn;

import ija.project.chess.field.Field;
import ija.project.chess.figure.Figure;

public class Turn {
    private Figure whiteFigure;
    private Figure whitePawnUpgradesTo;
    private boolean specifyColumnForWhite;
    private boolean specifyRowForWhite;
    private boolean whiteDefends;
    private boolean whiteCheckMate;
    private boolean whiteCheck;

    private Field whiteDestinationField;
    private Field whiteSourceField;

    private Figure blackFigure;
    private Figure blackPawnUpgradesTo;
    private boolean specifyColumnForBlack;
    private boolean specifyRowForBlack;
    private boolean blackDefends;
    private boolean blackCheckMate;
    private boolean blackCheck;

    private Field blackDestinationField;
    private Field blackSourceField;

    private int turnOrder;

    public int getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Figure getWhitePawnUpgradesTo() {
        return whitePawnUpgradesTo;
    }

    public void setWhitePawnUpgradesTo(Figure whitePawnUpgradesTo) {
        this.whitePawnUpgradesTo = whitePawnUpgradesTo;
    }

    public Figure getBlackPawnUpgradesTo() {
        return blackPawnUpgradesTo;
    }

    public void setBlackPawnUpgradesTo(Figure blackPawnUpgradesTo) {
        this.blackPawnUpgradesTo = blackPawnUpgradesTo;
    }

    public Figure getWhiteFigure() {
        return whiteFigure;
    }

    public void setWhiteFigure(Figure whiteFigure) {
        this.whiteFigure = whiteFigure;
    }

    public boolean isSpecifyColumnForWhite() {
        return specifyColumnForWhite;
    }

    public void setSpecifyColumnForWhite(boolean specifyColumnForWhite) {
        this.specifyColumnForWhite = specifyColumnForWhite;
    }

    public boolean isSpecifyRowForWhite() {
        return specifyRowForWhite;
    }

    public void setSpecifyRowForWhite(boolean specifyRowForWhite) {
        this.specifyRowForWhite = specifyRowForWhite;
    }

    public boolean isWhiteDefends() {
        return whiteDefends;
    }

    public void setWhiteDefends(boolean whiteDefends) {
        this.whiteDefends = whiteDefends;
    }

    public boolean isWhiteCheckMate() {
        return whiteCheckMate;
    }

    public void setWhiteCheckMate(boolean whiteCheckMate) {
        this.whiteCheckMate = whiteCheckMate;
    }

    public boolean isWhiteCheck() {
        return whiteCheck;
    }

    public void setWhiteCheck(boolean whiteCheck) {
        this.whiteCheck = whiteCheck;
    }

    public Field getWhiteDestinationField() {
        return whiteDestinationField;
    }

    public void setWhiteDestinationField(Field whiteDestinationField) {
        this.whiteDestinationField = whiteDestinationField;
    }

    public Field getWhiteSourceField() {
        return whiteSourceField;
    }

    public void setWhiteSourceField(Field whiteSourceField) {
        this.whiteSourceField = whiteSourceField;
    }

    public Figure getBlackFigure() {
        return blackFigure;
    }

    public void setBlackFigure(Figure blackFigure) {
        this.blackFigure = blackFigure;
    }

    public boolean isSpecifyColumnForBlack() {
        return specifyColumnForBlack;
    }

    public void setSpecifyColumnForBlack(boolean specifyColumnForBlack) {
        this.specifyColumnForBlack = specifyColumnForBlack;
    }

    public boolean isSpecifyRowForBlack() {
        return specifyRowForBlack;
    }

    public void setSpecifyRowForBlack(boolean specifyRowForBlack) {
        this.specifyRowForBlack = specifyRowForBlack;
    }

    public boolean isBlackDefends() {
        return blackDefends;
    }

    public void setBlackDefends(boolean blackDefends) {
        this.blackDefends = blackDefends;
    }

    public boolean isBlackCheckMate() {
        return blackCheckMate;
    }

    public void setBlackCheckMate(boolean blackCheckMate) {
        this.blackCheckMate = blackCheckMate;
    }

    public boolean isBlackCheck() {
        return blackCheck;
    }

    public void setBlackCheck(boolean blackCheck) {
        this.blackCheck = blackCheck;
    }

    public Field getBlackDestinationField() {
        return blackDestinationField;
    }

    public void setBlackDestinationField(Field blackDestinationField) {
        this.blackDestinationField = blackDestinationField;
    }

    public Field getBlackSourceField() {
        return blackSourceField;
    }

    public void setBlackSourceField(Field blackSourceField) {
        this.blackSourceField = blackSourceField;
    }
}
