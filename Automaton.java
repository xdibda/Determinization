/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: Automaton.
 * Content: Creating an instance representing automaton.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

public class Automaton {
    StateQueue states;
    RuleStack rules;
    SymbolStack symbols;

    Automaton() {
        this.states = new StateQueue();
        this.rules = new RuleStack();
        this.symbols = new SymbolStack();
    }

    Automaton(StateQueue states, SymbolStack symbols, RuleStack rules) {
        this.states = new StateQueue(states);
        this.rules = new RuleStack(rules);
        this.symbols = new SymbolStack(symbols);
    }

    public StateSet getInitState() {
        for (StateSet state: states) {
            if (state.isInit())
                return state;
        }
        return null;
    }

    void addState(StateSet state) {
        states.addState(state);
    }

    void addUniqueRule(Rule rule) {
        boolean found = false;
        for (Rule in: rules) {
            if (in.getSymbol().getValue() == (rule.getSymbol().getValue()) && in.getTop().getValue().equals(rule.getTop().getValue()) && in.getLeft().getValue().equals(rule.getLeft().getValue()) && in.getOut().getValue().equals(rule.getOut().getValue())) {
                found = true;
            }
        }
        if (!found) rules.addRule(rule);
    }

    StateQueue getStates() {
        return states;
    }

    void addSymbols(SymbolStack symbolStack) {
        for (Symbol symbol: symbolStack) {
            this.symbols.add(symbol);
        }
    }

    void sort() {
        for (StateSet stateSet: this.states) {
            stateSet.sort();
        }

        this.states.sort();
        this.rules.sort();
    }

    SymbolStack getSymbols() {
        return symbols;
    }

    RuleStack getRules() {
        return rules;
    }
}
