package ija.project.chess.figure;

import ija.project.chess.field.Field;
import javafx.scene.image.ImageView;

public interface Figure {
    boolean move(Field moveTo);
    boolean canMove(Field moveTo);
    boolean isWhite();

    void setWhite(boolean isWhite);
    void setField(Field field);

    Field getField();
    String getState();
    String getFigureChar();

    ImageView getImage();
}
