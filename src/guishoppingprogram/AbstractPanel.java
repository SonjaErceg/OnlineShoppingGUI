/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractPanel extends JPanel {
    protected MainFrame mainFrame;

    public AbstractPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE);
        buildUI(); // force UI construction
    }

    protected abstract void buildUI(); // every panel must implement this
}
