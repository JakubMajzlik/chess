package ija.project.chess.notation;

public class ChessTurnNotation {
    private int turnOrder;
    private String whiteTurnNotation;
    private String blackTurnNotation;

    public ChessTurnNotation() {}

    public ChessTurnNotation(int turnOrder, String whiteTurnNotation, String blackTurnNotation) {
        this.turnOrder = turnOrder;
        this.whiteTurnNotation = whiteTurnNotation;
        this.blackTurnNotation = blackTurnNotation;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public String getWhiteTurnNotation() {
        return whiteTurnNotation;
    }

    public void setWhiteTurnNotation(String whiteTurnNotation) {
        this.whiteTurnNotation = whiteTurnNotation;
    }

    public String getBlackTurnNotation() {
        return blackTurnNotation;
    }

    public void setBlackTurnNotation(String blackTurnNotation) {
        this.blackTurnNotation = blackTurnNotation;
    }
}
