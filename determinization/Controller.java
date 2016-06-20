/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: Controller.
 * Content: Creating an instance of main inner logic's class.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

public class Controller {
    Automaton nondeterministicAutomata;
    Automaton deterministicAutomata;

    DiagonalQueue queueProc;
    DiagonalQueue queueNext;

    StateQueue queueStep;
    MapQueue queueMap;

    StateSet initState;

    Controller(StateQueue states, SymbolStack symbols, RuleStack rules) {
        nondeterministicAutomata = new Automaton(states, symbols, rules);
        deterministicAutomata = new Automaton();
    }

    public void determinize() {
        deterministicAutomata = new Automaton();

        queueMap = new MapQueue();

        queueProc = new DiagonalQueue();
        queueNext = new DiagonalQueue();
        queueStep = new StateQueue();

        initState = nondeterministicAutomata.getInitState();

        deterministicAutomata.addSymbols(nondeterministicAutomata.getSymbols());
        deterministicAutomata.addState(initState);

        do {
            deterministicAutomata.getStates().addAllUniqueStates(queueProc);
            queueProc.saveBoundary(initState);

            while (queueProc.testCondition()) {
                StateQueue top = queueProc.getTopStates();
                StateQueue left = queueProc.getLeftStates();
                queueMap.store(top, left);

                for (StateSet topState: top) {
                    for (StateSet leftState: left) {
                        for (Symbol symbol : nondeterministicAutomata.getSymbols()) {
                            StateSet out = Rule.findRule(nondeterministicAutomata.getRules(), topState, leftState, symbol);
                            if (out.hasState()) {
                                deterministicAutomata.addUniqueRule(new Rule(topState, leftState, symbol, out));
                                queueStep.addState(out);
                            }
                        }
                    }
                }

                queueNext.addState(queueStep.removeStates());
            }
            queueProc = new DiagonalQueue(queueNext.removeStates());
        } while (queueMap.testCondition(queueProc, initState));

        deterministicAutomata.sort();
    }

    public Automaton getDeterministicAutomaton() {
        return deterministicAutomata;
    }

    public Automaton getNondeterministicAutomaton() {
        return nondeterministicAutomata;
    }
}
