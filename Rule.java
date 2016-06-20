/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: Rule.
 * Content: An instance representing a rule.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;

public class Rule implements Comparable<Rule> {
    StateSet top, left, out;

    Symbol symbol;

    Rule(StateSet top, StateSet left, Symbol symbol, StateSet out) {
        this.top = top;
        this.left = left;
        this.symbol = symbol;
        this.out = out;
    }

    public StateSet getLeft() {
        return left;
    }

    public StateSet getOut() {
        return out;
    }

    public StateSet getTop() {
        return top;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public static StateSet findRule(ArrayList<Rule> rules, StateSet top, StateSet left, Symbol symbol) {
        StateSet tmpStates = new StateSet();
        for (State topState: top) {
            for (State leftState: left) {
                for (Rule rule: rules) {
                    if (rule.getTop().getValue().equals(new StateSet(topState).getValue()) && rule.getLeft().getValue().equals(new StateSet(leftState).getValue()) && rule.getSymbol().getValue() == symbol.getValue()) {
                        tmpStates.addUniqueState(rule.getOut());
                    }
                }
            }
        }
        return tmpStates;
    }

    @Override
    public int compareTo(Rule o) {
        if (this.top.compareTo(o.top) > 0) {
            return 1;
        }
        else if (this.top.compareTo(o.top) < 0) {
            return -1;
        }
        else {
            if (this.left.compareTo(o.left) > 0) {
                return 1;
            }
            else if (this.left.compareTo(o.left) < 0) {
                return -1;
            }
            else {
                if (this.symbol.compareTo(o.symbol) > 0) {
                    return 1;
                }
                else if (this.symbol.compareTo(o.symbol) < 0) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    }
}
