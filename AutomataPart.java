/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: AutomataPart + Blocks.
 * Content: Creating visual blocks from the automata's components.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Lukas on 08.04.16.
 */
public abstract class AutomataPart extends JPanel {
    GridBagConstraints gbc;

    DefaultTableModel defaultTableModel;
    JTable table;

    Automaton automaton;

    final String ALL_STATES = "Všechny";
    final String INIT_STATE = "Inicializační";
    final String FINITE_STATES = "Koncové";

    final String INIT = "Inic. stav";
    final String FINITE = "Koncový stav";

    final String ADD = "Přidat";
    final String DELETE = "Smazat";

    AutomataPart(Color color, String panelName, Automaton automaton) {
        this.automaton = automaton;

        setBackground(color);
        setLayout(new GridBagLayout());

        JLabel textLabel = new JLabel(panelName);

        JPanel textPanel = new JPanel();
        textPanel.setBorder(new LineBorder(Color.black));
        textPanel.add(textLabel);

        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(textPanel, gbc);

        gbc.weightx = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
    }

    void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    void setTable(Object[][] data, GridBagConstraints gbc, String[] dataNames, JButton deleteButton, boolean canAddValues) {
        defaultTableModel = new DefaultTableModel();
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(noFocusBorder);
                return this;
            }
        };

        for (int i = 0; i < data.length; i++) {
            defaultTableModel.addColumn(dataNames[i], data[i]);
        }

        if (canAddValues) {
            table = new JTable(defaultTableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }
            };

            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    deleteButton.setEnabled(true);
                }
            });

            table.setIntercellSpacing(new Dimension(0, 0));
        }
        else {
            table = new JTable(defaultTableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public boolean isRowSelected(int row) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }
            };
        }

        table.setDefaultRenderer(String.class, defaultTableCellRenderer);

        table.setRowHeight(table.getRowHeight() + 8);
        table.setFont(new Font("Tahoma", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, gbc);
    }

    void refreshTableRows(Object[][] data) {
        while (defaultTableModel.getRowCount() > 0)
            defaultTableModel.removeRow(0);

        for (Object[] subdata: data) {
            for (Object simpledata: subdata)
                defaultTableModel.addRow(new Object[] {simpledata});
        }
    }

    void refreshTableCells(ArrayList<Rule> rules) {
        while (defaultTableModel.getRowCount() > 0)
            defaultTableModel.removeRow(0);

        for (Rule rule: rules) {
            ArrayList<Object> tmp = new ArrayList<>();
            tmp.add(" " + rule.getTop().getValue());
            tmp.add("x");
            tmp.add(rule.getLeft().getValue());
            tmp.add("x");
            tmp.add(rule.getSymbol().getValue());
            tmp.add("--->");
            tmp.add(rule.getOut().getValue());
            defaultTableModel.addRow(tmp.toArray());
        }
    }
}

class StateBlock extends AutomataPart implements ActionListener, KeyListener {
    JTextField textField;

    JCheckBox init;
    JCheckBox finite;

    JButton addState;
    JButton deleteState;

    JButton allStates;
    JButton initState;
    JButton finiteStates;

    JButton determinizeButton;

    StateBlock(Color color, String panelName, boolean canAddValues, Automaton automaton, JButton determinizeButton, JButton addState, JButton deleteState) {
        super(color, panelName, automaton);

        this.addState = addState;
        this.deleteState = deleteState;
        this.determinizeButton = determinizeButton;

        allStates = new JButton(ALL_STATES);
        allStates.setEnabled(false);

        initState = new JButton(INIT_STATE);
        finiteStates = new JButton(FINITE_STATES);

        gbc.weightx = 0.33;

        if (canAddValues) {
            textField = new JTextField();

            addState.setText(ADD);
            addState.setEnabled(false);
            addState.addActionListener(this);

            deleteState.setText(DELETE);
            deleteState.setEnabled(false);
            deleteState.addActionListener(this);

            textField.addKeyListener(this);

            init = new JCheckBox(INIT);
            finite = new JCheckBox(FINITE);

            gbc.gridwidth = 2;
            gbc.weightx = 0.2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(textField, gbc);

            gbc.gridwidth = 1;
            gbc.gridx += 2;
            gbc.weightx = 0.8;
            add(addState, gbc);

            gbc.weightx = 0.33;
            gbc.gridx = 0;
            gbc.gridy++;
            add(init, gbc);

            gbc.gridx++;
            add(finite, gbc);

            gbc.gridx++;
            add(deleteState, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
        }

        gbc.weightx = 0.33;
        allStates.addActionListener(this);
        add(allStates, gbc);

        gbc.gridx++;
        initState.addActionListener(this);
        add(initState, gbc);

        gbc.gridx++;
        finiteStates.addActionListener(this);
        add(finiteStates, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        gbc.insets = new Insets(0, 5, 5, 5);

        setTable(new Object[][] {getEditedNames().toArray()}, gbc, new String[]{"Stavy"}, deleteState, canAddValues);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (textField.getText().equals("")) {
            addState.setEnabled(false);
            return;
        }

        boolean found = false;
        for (String stateName: automaton.getStates().getNameStates()) {
            if (textField.getText().equals(stateName))
                found = true;
        }
        addState.setEnabled(!found);

        init.setEnabled(!automaton.getStates().hasInitState());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == initState) {
            setView("INIT");
        }
        else if (e.getSource() == allStates) {
            setView("ALL");
        }
        else if (e.getSource() == finiteStates) {
            setView("FINITE");
        }
        else if (e.getSource() == deleteState) {
            int indexOfValue = 0;
            for (StateSet stateSet: automaton.getStates()) {
                String value = " " + stateSet.getValue();
                if (value.equals(table.getModel().getValueAt(table.getSelectedRow(), 0).toString())) {
                    indexOfValue = automaton.getStates().indexOf(stateSet);
                    break;
                }
            }

            automaton.getStates().remove(indexOfValue);
            defaultTableModel.removeRow(table.getSelectedRow());

            if (!automaton.getStates().hasInitState()) {
                init.setEnabled(true);
                determinizeButton.setEnabled(false);
            }

            setView("ALL");

            deleteState.setEnabled(false);
        }
        else if (e.getSource() == addState) {
            automaton.addState(new StateSet(new State(textField.getText(), init.isSelected() ? Role.INIT : null, finite.isSelected() ? Role.FINITE : null)));
            automaton.getStates().sort();

            finite.setSelected(false);

            textField.setText("");

            if (automaton.getStates().hasInitState()) {
                init.setEnabled(false);
                init.setSelected(false);
                determinizeButton.setEnabled(true);
            }

            refreshTableRows(new Object[][]{getEditedNames().toArray()});

            addState.setEnabled(false);
            deleteState.setEnabled(false);
        }
    }

    public void setView(String view) {
        while (defaultTableModel.getRowCount() > 0)
            defaultTableModel.removeRow(0);

        switch (view) {
            case "ALL":
                allStates.setEnabled(false);
                initState.setEnabled(true);
                finiteStates.setEnabled(true);
                for (StateSet stateSet: automaton.getStates()) {
                    defaultTableModel.addRow(new Object[]{" " + stateSet.getValue()});
                }
                break;
            case "INIT":
                allStates.setEnabled(true);
                initState.setEnabled(false);
                finiteStates.setEnabled(true);
                for (StateSet stateSet: automaton.getStates()) {
                    if(stateSet.isInit())
                        defaultTableModel.addRow(new Object[]{" " + stateSet.getValue()});
                }
                break;
            case "FINITE":
                allStates.setEnabled(true);
                initState.setEnabled(true);
                finiteStates.setEnabled(false);
                for (StateSet stateSet: automaton.getStates()) {
                    if(stateSet.isFinite())
                        defaultTableModel.addRow(new Object[]{" " + stateSet.getValue()});
                }
        }
        deleteState.setEnabled(false);
    }

    ArrayList<String> getEditedNames() {
        ArrayList<String> tmp = new ArrayList<>();
        for (String string: automaton.getStates().getNameStates()) {
            tmp.add(" " + string);
        }
        return tmp;
    }
}

class SymbolBlock extends AutomataPart implements KeyListener, ActionListener {
    JTextField textField;

    JButton addSymbol;
    JButton deleteSymbol;

    SymbolBlock(Color color, String panelName, boolean canAddValues, Automaton automaton, JButton addSymbol, JButton deleteSymbol) {
        super(color, panelName, automaton);

        if (canAddValues) {
            textField = new JTextField();
            textField.addKeyListener(this);

            this.addSymbol = addSymbol;
            this.deleteSymbol = deleteSymbol;

            addSymbol.setText(ADD);
            addSymbol.setEnabled(false);
            addSymbol.addActionListener(this);

            deleteSymbol.setText(DELETE);
            deleteSymbol.setEnabled(false);
            deleteSymbol.addActionListener(this);

            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(textField, gbc);

            gbc.gridwidth = 1;
            gbc.weightx = 0.5;
            gbc.gridy++;
            add(addSymbol, gbc);

            gbc.gridx++;
            add(deleteSymbol, gbc);

            gbc.weightx = 1;
            gbc.gridx = 0;
            gbc.gridy++;
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 5, 5, 5);

        setTable(new Object[][] {this.automaton.getSymbols().getNameSymbols().toArray()}, gbc, new String[]{"Stavy"}, deleteSymbol, canAddValues);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (textField.getText().length() != 1) {
            addSymbol.setEnabled(false);
            return;
        }

        boolean found = false;
        for (Symbol symbol : automaton.getSymbols())
            if (textField.getText().charAt(0) == symbol.getValue())
                found = true;
        addSymbol.setEnabled(!found);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addSymbol) {
            automaton.getSymbols().addSymbol(new Symbol(textField.getText().charAt(0)));
            automaton.getSymbols().sort();

            textField.setText("");

            refreshTableRows(new Object[][] {automaton.getSymbols().getNameSymbols().toArray()});

            addSymbol.setEnabled(false);
            deleteSymbol.setEnabled(false);
        }
        else if (e.getSource() == deleteSymbol) {
            int indexOfValue = 0;
            for (Symbol symbol: automaton.getSymbols()) {
                String value = " " + symbol.getValue();
                if (value.equals(table.getModel().getValueAt(table.getSelectedRow(), 0).toString())) {
                    indexOfValue = automaton.getSymbols().indexOf(symbol);
                    break;
                }
            }

            automaton.getSymbols().remove(indexOfValue);
            defaultTableModel.removeRow(table.getSelectedRow());

            deleteSymbol.setEnabled(false);
        }
    }
}

class RuleBlock extends AutomataPart implements ActionListener, ItemListener {
    JComboBox<String> topState, leftState, symbol, outState;

    JButton allStates;
    JButton initState;
    JButton finiteStates;

    JButton deleteButton;
    JButton addButton;

    RuleBlock(Color color, String panelName, boolean canAddValues, Automaton automaton) {
        super(color, panelName, automaton);

        allStates = new JButton("Všechny");
        allStates.setEnabled(false);

        initState = new JButton("Inicializační");
        finiteStates = new JButton("Koncové");

        if (canAddValues) {
            addButton = new JButton(ADD);
            addButton.addActionListener(this);

            deleteButton = new JButton(DELETE);
            deleteButton.setEnabled(false);
            deleteButton.addActionListener(this);

            topState = new JComboBox<>();
            leftState = new JComboBox<>();
            outState = new JComboBox<>();
            symbol = new JComboBox<>();

            topState.addItemListener(this);
            leftState.addItemListener(this);
            outState.addItemListener(this);
            symbol.addItemListener(this);

            refreshInputValues();

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.3;
            add(topState, gbc);
            gbc.gridx++;
            gbc.weightx = 0;
            add(new JLabel("X"), gbc);
            gbc.gridx++;
            gbc.weightx = 0.3;
            add(leftState, gbc);
            gbc.gridx++;
            gbc.weightx = 0;
            add(new JLabel("X"), gbc);
            gbc.gridx++;
            add(symbol, gbc);
            gbc.gridx++;
            add(new JLabel("->"), gbc);
            gbc.gridx++;
            gbc.weightx = 0.3;
            add(outState, gbc);

            gbc.gridx++;
            add(addButton, gbc);

            gbc.gridx = 0;
            gbc.gridy++;

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 2;
            add(allStates, gbc);

            gbc.gridx++;
            gbc.gridx++;
            add(initState, gbc);

            gbc.gridx++;
            gbc.gridx++;
            add(finiteStates, gbc);

            gbc.gridwidth = 1;
            gbc.gridx++;
            gbc.gridx++;
            gbc.gridx++;
            add(deleteButton, gbc);
        }
        else {
            gbc.weightx = 0.33;
            add(allStates, gbc);

            gbc.gridx++;
            add(initState, gbc);

            gbc.gridx++;
            add(finiteStates, gbc);
        }

        allStates.addActionListener(this);
        initState.addActionListener(this);
        finiteStates.addActionListener(this);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;

        ArrayList<String> top = new ArrayList<>();
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> symbol = new ArrayList<>();
        ArrayList<String> out = new ArrayList<>();

        ArrayList<String> times = getCharacters(top.size(), "x");
        ArrayList<String> arrow = getCharacters(top.size(), "--->");

        for (Rule rule: automaton.getRules()) {
            top.add(rule.getTop().getValue());
            left.add(rule.getLeft().getValue());
            symbol.add(Character.toString(rule.getSymbol().getValue()));
            out.add(rule.getOut().getValue());
        }

        ArrayList<String> tmp = new ArrayList<>();
        while (top.size() > 0)
            tmp.add(" " + top.remove(0));

        Object[][] data = {
                tmp.toArray(),
                times.toArray(),
                left.toArray(),
                times.toArray(),
                symbol.toArray(),
                arrow.toArray(),
                out.toArray()
        };

        String[] dataNames = {
                "Horní",
                "",
                "Vlevo",
                "",
                "Symbol",
                "",
                "Výstupní"
        };

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        gbc.insets = new Insets(0, 5, 5, 5);

        setTable(data, gbc, dataNames, deleteButton, canAddValues);
    }

    public ArrayList<String> getCharacters(int times, String character) {
        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            tmp.add(character);
        }
        return tmp;
    }

    public void refreshInputValues() {
        topState.removeAllItems();
        leftState.removeAllItems();
        outState.removeAllItems();
        symbol.removeAllItems();

        for (StateSet stateSet: automaton.getStates()) {
            topState.addItem(stateSet.getValue());
            leftState.addItem(stateSet.getValue());
            outState.addItem(stateSet.getValue());
        }

        for (Symbol symbolName: automaton.getSymbols()) {
            symbol.addItem(Character.toString(symbolName.getValue()));
        }

        addButton.setEnabled(!isRuleCreated());
    }

    boolean isRuleCreated() {
        for (Rule rule: automaton.getRules()) {
            if (rule.getTop().getValue().equals(topState.getSelectedItem().toString()))
                if (rule.getLeft().getValue().equals(leftState.getSelectedItem().toString()))
                    if (rule.getOut().getValue().equals(outState.getSelectedItem().toString()))
                        if (rule.getSymbol().getValue() == (symbol.getSelectedItem().toString().charAt(0)))
                            return true;
        }
        return false;
    }

    void setView(String view) {
        while (defaultTableModel.getRowCount() > 0)
            defaultTableModel.removeRow(0);

        switch (view) {
            case "ALL":
                allStates.setEnabled(false);
                initState.setEnabled(true);
                finiteStates.setEnabled(true);
                for (Rule rule: automaton.getRules()) {
                    defaultTableModel.addRow(new Object[]{
                            (" " + rule.getTop().getValue()),
                            "x",
                            rule.getLeft().getValue(),
                            "x",
                            rule.getSymbol().getValue(),
                            "--->",
                            rule.getOut().getValue()
                    });
                }
                break;
            case "INIT":
                allStates.setEnabled(true);
                initState.setEnabled(false);
                finiteStates.setEnabled(true);

                for (Rule rule: automaton.getRules()) {
                    if (rule.getTop().isInit() || rule.getLeft().isInit())
                        defaultTableModel.addRow(new Object[]{
                                (" " + rule.getTop().getValue()),
                                "x",
                                rule.getLeft().getValue(),
                                "x",
                                rule.getSymbol().getValue(),
                                "--->",
                                rule.getOut().getValue()
                        });
                }
                break;
            case "FINITE":
                allStates.setEnabled(true);
                initState.setEnabled(true);
                finiteStates.setEnabled(false);
                for (Rule rule: automaton.getRules()) {
                    if (rule.getOut().isFinite())
                        defaultTableModel.addRow(new Object[]{
                                (" " + rule.getTop().getValue()),
                                "x",
                                rule.getLeft().getValue(),
                                "x",
                                rule.getSymbol().getValue(),
                                "--->",
                                rule.getOut().getValue()
                        });
                }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            StateSet topStateSet = null, leftStateSet = null, outStateSet = null;

            for (StateSet stateSet: automaton.getStates()) {
                if (stateSet.getValue().equals(topState.getSelectedItem().toString()))
                    topStateSet = new StateSet(new State(stateSet.getValue(), stateSet.isInit() ? Role.INIT: null, stateSet.isFinite() ? Role.FINITE : null));
                if (stateSet.getValue().equals(leftState.getSelectedItem().toString()))
                    leftStateSet = new StateSet(new State(stateSet.getValue(), stateSet.isInit() ? Role.INIT: null, stateSet.isFinite() ? Role.FINITE : null));
                if (stateSet.getValue().equals(outState.getSelectedItem().toString()))
                    outStateSet = new StateSet(new State(stateSet.getValue(), stateSet.isInit() ? Role.INIT: null, stateSet.isFinite() ? Role.FINITE : null));
            }

            automaton.getRules().addRule(new Rule(topStateSet, leftStateSet, new Symbol(symbol.getSelectedItem().toString().charAt(0)), outStateSet));
            automaton.getRules().sort();

            refreshTableCells(automaton.getRules());
            addButton.setEnabled(!isRuleCreated());
        }
        else if (e.getSource() == deleteButton) {
            int indexOfValue = 0;
            for (Rule rule: automaton.getRules()) {
                if ((" " + rule.getTop().getValue()).equals(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()) &&
                        rule.getLeft().getValue().equals(table.getModel().getValueAt(table.getSelectedRow(), 2).toString()) &&
                        rule.getOut().getValue().equals(table.getModel().getValueAt(table.getSelectedRow(), 6).toString()) &&
                        rule.getSymbol().getValue() == (table.getModel().getValueAt(table.getSelectedRow(), 4).toString().charAt(0))) {
                    indexOfValue = automaton.getRules().indexOf(rule);
                    break;
                }
            }

            automaton.getRules().remove(indexOfValue);
            defaultTableModel.removeRow(table.getSelectedRow());

            deleteButton.setEnabled(false);
            addButton.setEnabled(!isRuleCreated());
        }
        else if (e.getSource() == initState) {
            setView("INIT");
        }
        else if (e.getSource() == allStates) {
            setView("ALL");
        }
        else if (e.getSource() == finiteStates) {
            setView("FINITE");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (topState.getSelectedItem() == null ||
                leftState.getSelectedItem() == null ||
                outState.getSelectedItem() == null ||
                symbol.getSelectedItem() == null ) {
            addButton.setEnabled(false);
            return;
        }

        addButton.setEnabled(true);
        for (Rule rule : automaton.getRules()) {
            if (rule.getTop().getValue().equals(topState.getSelectedItem()) &&
                    rule.getLeft().getValue().equals(leftState.getSelectedItem()) &&
                    rule.getOut().getValue().equals(outState.getSelectedItem()) &&
                    Character.toString(rule.getSymbol().getValue()).equals(symbol.getSelectedItem()))
                        addButton.setEnabled(false);
        }
    }
}
