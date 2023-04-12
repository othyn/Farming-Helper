//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import net.runelite.client.ui.overlay.components.ImageComponent;

public class OutlinedImageComponent extends ImageComponent
{
    private final BufferedImage image;
    private final int xOffset;
    private final int yOffset;
    private Color borderColor;

    public OutlinedImageComponent(BufferedImage image, int xOffset, int yOffset)
    {
        super(image);
        this.image = image;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (borderColor != null)
        {
            graphics.setColor(borderColor);
            graphics.drawRect(xOffset, yOffset, image.getWidth(), image.getHeight());
        }
        graphics.drawImage(image, xOffset + 1, yOffset + 1, null);
        return new Dimension(image.getWidth() + 2, image.getHeight() + 2);
    }

    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
}