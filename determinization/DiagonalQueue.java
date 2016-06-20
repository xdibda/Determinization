/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: DiagonalQueue.
 * Content: One step of the processing tesselation's automata.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;

public class DiagonalQueue extends ArrayList<StateQueue> {
    DiagonalQueue() {}

    DiagonalQueue(DiagonalQueue diagonalQueue) {
        for (StateQueue states: diagonalQueue) {
            this.add(states);
        }
    }

    DiagonalQueue addBoundary(StateSet state) {
        StateQueue init = new StateQueue(state);

        DiagonalQueue boundary = new DiagonalQueue(this);

        boundary.add(0, init);
        boundary.add(init);

        return boundary;
    }

    void saveBoundary(StateSet state) {
        StateQueue init = new StateQueue(state);

        this.add(init);
        this.add(0, init);
    }

    void addState(StateQueue queue) {
        this.add(queue);
    }

    StateQueue getTopStates() {
        return this.remove(0);
    }

    StateQueue getLeftStates() {
        return this.get(0);
    }

    DiagonalQueue removeStates() {
        DiagonalQueue tmp = new DiagonalQueue(this);
        this.clear();
        return tmp;
    }

    boolean testCondition() {
        return this.size() > 1;
    }
}
