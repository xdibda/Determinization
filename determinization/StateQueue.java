/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: StateQueue.
 * Content: One cell of picture.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;
import java.util.Collections;

public class StateQueue extends ArrayList<StateSet> {
    StateQueue() {}

    StateQueue(StateSet state) {
        this.add(state);
    }

    StateQueue(ArrayList<StateSet> states) {
        for (StateSet state: states) {
            this.add(state);
        }
    }

    void addState(StateSet state) {
        this.add(state);
    }

    void addUniqueState(StateSet state) {
        boolean found = false;
        for(StateSet loopstate: this) {
            if (state.getValue().equals(loopstate.getValue())) {
                found = true;
            }
        }
        if (!found) {
            this.add(state);
        }
    }

    void addAllUniqueStates(DiagonalQueue states) {
        for (StateQueue stateQueue: states) {
            for (StateSet state: stateQueue) {
                addUniqueState(state);
            }
        }
    }

    StateQueue removeStates() {
        StateQueue tmp = new StateQueue(this);
        this.clear();
        return tmp;
    }

    void sort() {
        Collections.sort(this);
    }

    ArrayList<String> getNameStates() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (StateSet stateSet: this) {
            arrayList.add(stateSet.getValue());
        }
        return arrayList;
    }

    boolean hasInitState() {
        for (StateSet stateSet: this) {
            if (stateSet.isInit())
                return true;
        }
        return false;
    }
}
