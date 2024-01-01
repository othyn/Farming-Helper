package com.farminghelper.speaax.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartStopJButton extends JButton
{
    private String originalText;

    private Boolean currentState;

    public StartStopJButton(String text)
    {
        super(text, null);

        this.originalText = text;

        this.setStartStopState(false);
    }

    public void setStartStopState(Boolean started)
    {
        this.currentState = started;

        String startOrStop = started ? "Stop " : "Start ";

        this.setText(startOrStop.concat(this.originalText));

        this.setBackground(started ? Color.RED : Color.BLACK);
    }

    public void changeText(String text)
    {
        this.originalText = text;

        this.setStartStopState(this.currentState);
    }

    public void removeAllActionListeners()
    {
        for(ActionListener actionListener : this.getActionListeners()) {
            this.removeActionListener(actionListener);
        }
    }
}
