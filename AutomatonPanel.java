/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: AutomatonPanel.
 * Content: Creating an instance of top/bottom block of main window.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AutomatonPanel extends JPanel {
    AutomataBlock automataBlock;

    final String statePanelName = "Stavy:";
    final String symbolPanelName = "Symboly:";
    final String rulePanelName = "Pravidla:";

    JButton determinizeButton;

    Controller controller;

    AutomatonPanel(String name, boolean canAddValues, Controller controller, JButton determinizeButton) {
        this.determinizeButton = determinizeButton;
        this.controller = controller;

        setBorder(new EmptyBorder(0, 0, 10, 0));
        setLayout(new BorderLayout());

        JLabel textLabel = new JLabel(name);
        textLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel textPanel = new JPanel();
        textPanel.add(textLabel);

        automataBlock = new AutomataBlock(canAddValues, controller);

        add(textPanel, BorderLayout.NORTH);
        add(automataBlock, BorderLayout.CENTER);
    }

    class AutomataBlock extends JPanel implements ActionListener {
        StateBlock stateBlock;
        SymbolBlock symbolBlock;
        RuleBlock ruleBlock;

        JButton addState, deleteState;
        JButton addSymbol, deleteSymbol;

        AutomataBlock(boolean canAddValues, Controller controller) {
            setLayout(new GridBagLayout());
            setBackground(new Color(219,219,219));

            addState = new JButton();
            addState.addActionListener(this);
            deleteState = new JButton();
            deleteState.addActionListener(this);

            addSymbol = new JButton();
            addSymbol.addActionListener(this);
            deleteSymbol = new JButton();
            deleteSymbol.addActionListener(this);

            stateBlock = new StateBlock(new Color(211,211,211), statePanelName, canAddValues, canAddValues ? controller.getNondeterministicAutomaton() : controller.getDeterministicAutomaton(), canAddValues ? determinizeButton : null, addState, deleteState);
            symbolBlock = new SymbolBlock(new Color(169,169,169), symbolPanelName, canAddValues, canAddValues ? controller.getNondeterministicAutomaton() : controller.getDeterministicAutomaton(), addSymbol, deleteSymbol);
            ruleBlock = new RuleBlock(new Color(211,211,211), rulePanelName, canAddValues, canAddValues ? controller.getNondeterministicAutomaton() : controller.getDeterministicAutomaton());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 1;
            gbc.weightx = 0.2;
            gbc.fill = GridBagConstraints.BOTH;
            add(stateBlock, gbc);

            gbc.gridx++;
            gbc.weightx = 0.3;
            add(symbolBlock, gbc);

            gbc.gridx++;
            gbc.weightx = 0.5;
            add(ruleBlock, gbc);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteState) {
                ArrayList<Integer> removeIndex = new ArrayList<>();
                for (Rule rule: controller.getNondeterministicAutomaton().getRules()) {
                    boolean top = false, left = false, out = false;
                    for (StateSet stateSet : controller.getNondeterministicAutomaton().getStates()) {
                        if (rule.getTop().getValue().equals(stateSet.getValue()))
                            top = true;
                        if (rule.getLeft().getValue().equals(stateSet.getValue()))
                            left = true;
                        if (rule.getOut().getValue().equals(stateSet.getValue()))
                            out = true;
                    }
                    if (!top || !left || !out) removeIndex.add(controller.getNondeterministicAutomaton().getRules().indexOf(rule));
                }
                while (!removeIndex.isEmpty()) {
                    controller.getNondeterministicAutomaton().getRules().remove(removeIndex.remove(removeIndex.size() - 1).intValue());
                }
            }
            else if (e.getSource() == deleteSymbol) {
                ArrayList<Integer> removeIndex = new ArrayList<>();
                for (Rule rule: controller.getNondeterministicAutomaton().getRules()) {
                    boolean sym = false;
                    for (Symbol symbol: controller.getNondeterministicAutomaton().getSymbols()) {
                        if (rule.getSymbol().getValue() == symbol.getValue())
                            sym = true;
                    }
                    if (!sym) removeIndex.add(controller.getNondeterministicAutomaton().getRules().indexOf(rule));
                }
                while (!removeIndex.isEmpty()) {
                    controller.getNondeterministicAutomaton().getRules().remove(removeIndex.remove(removeIndex.size() - 1).intValue());
                }
            }
            getRuleBlock().refreshTableCells(controller.getNondeterministicAutomaton().getRules());
            getRuleBlock().refreshInputValues();

            if ((controller.getNondeterministicAutomaton().getSymbols().size() == 0) || (controller.getNondeterministicAutomaton().getStates().size() == 0)) {
                getRuleBlock().addButton.setEnabled(false);
            }
        }

        public StateBlock getStateBlock() {
            return stateBlock;
        }

        public SymbolBlock getSymbolBlock() {
            return symbolBlock;
        }

        public RuleBlock getRuleBlock() {
            return ruleBlock;
        }
    }

    void refreshAutomaton(Automaton automaton) {
        getAutomataBlock().getStateBlock().setAutomaton(automaton);
        getAutomataBlock().getSymbolBlock().setAutomaton(automaton);
        getAutomataBlock().getRuleBlock().setAutomaton(automaton);
    }

    public AutomataBlock getAutomataBlock() {
        return automataBlock;
    }
}
