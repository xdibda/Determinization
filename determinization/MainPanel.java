/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: MainPanel.
 * Content: Top/bottom panel.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    Controller controller;

    final private boolean canAddValues = true;

    JButton determinizeButton;

    AutomatonPanel topAutomatonPanel;
    AutomatonPanel bottomAutomatonPanel;

    MainPanel(Controller controller) {
        this.controller = controller;

        final String determinizeButtonName = "Determinizovat";
        final String topPanelName = "Nedeterminizovaný automat";
        final String bottomPanelName = "Determinizovaný automat";

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        determinizeButton = new JButton(determinizeButtonName);
        determinizeButton.setEnabled(false);
        determinizeButton.addActionListener(this);

        JPanel determinizePanel= new JPanel();
        determinizePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        determinizePanel.setLayout(new BorderLayout());
        determinizePanel.add(determinizeButton, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;

        topAutomatonPanel = new AutomatonPanel(topPanelName, canAddValues, controller, determinizeButton);
        bottomAutomatonPanel = new AutomatonPanel(bottomPanelName, !canAddValues, controller, determinizeButton);

        add(topAutomatonPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        add(determinizePanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0.4;
        add(bottomAutomatonPanel, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == determinizeButton) {
            controller.determinize();
            bottomAutomatonPanel.refreshAutomaton(controller.getDeterministicAutomaton());
            bottomAutomatonPanel.getAutomataBlock().getStateBlock().setView("ALL");
            bottomAutomatonPanel.getAutomataBlock().getRuleBlock().setView("ALL");

            bottomAutomatonPanel.getAutomataBlock().getSymbolBlock().refreshTableRows(new Object[][] {controller.getDeterministicAutomaton().getSymbols().getNameSymbols().toArray()});
        }
    }
}
