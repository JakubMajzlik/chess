package ija.project.chess.figure;

import ija.project.chess.field.Field;

public interface Figure {
    boolean move(Field moveTo);
    boolean isWhite();

    void setWhite(boolean isWhite);
    void setField(Field field);

    Field getField();
    String getState();
}
