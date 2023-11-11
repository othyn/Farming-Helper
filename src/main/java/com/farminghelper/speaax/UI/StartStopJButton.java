package com.farminghelper.speaax.UI;

import javax.swing.*;
import java.awt.*;

public class StartStopJButton extends JButton
{
    private String originalText;

    public StartStopJButton(String text)
    {
        super(text, null);

        this.originalText = text;

        this.setStartStopState(false);
    }

    public void setStartStopState(Boolean started)
    {
        String startOrStop = started ? "Stop " : "Start ";

        this.setText(startOrStop.concat(this.originalText));

        this.setBackground(started ? Color.RED : Color.BLACK);
    }
}
