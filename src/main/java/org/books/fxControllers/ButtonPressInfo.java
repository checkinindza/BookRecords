package org.books.fxControllers;

public final class ButtonPressInfo {
    private boolean addWasPressed;
    private String text;
    private final static ButtonPressInfo instance = new ButtonPressInfo();

    private ButtonPressInfo() {}

    public static ButtonPressInfo getInstace() {
        return instance;
    }
    
    public void setAddWasPressed(boolean addWasPressed) {
        this.addWasPressed = addWasPressed;
    }
    
    public boolean getAddWasPressed() {
        return addWasPressed;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
