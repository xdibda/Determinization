/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: StateSet.
 * Content: Set of states.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;
import java.util.Collections;

public class StateSet extends ArrayList<State> implements Comparable<StateSet> {
    StateSet() {}

    StateSet(State state) {
        this.add(state);
    }

    void addUniqueState(StateSet states) {
        for (State state: states) {
            boolean found = false;
            for (State in: this) {
                if (in == state) {
                    found = true;
                }
            }
            if (!found) this.add(state);
        }
    }

    String getValue() {
        String tmp = null;
        for (State state: this) {
            if (tmp == null) {
                tmp = state.getValue();
            }
            else {
                tmp += "_" + state.getValue();
            }
        }
        return tmp;
    }

    boolean isFinite() {
        for (State state: this) {
            if (state.isFinite()) {
                return true;
            }
        }
        return false;
    }

    boolean isInit() {
        for (State state: this) {
            if (state.isInit()) {
                return true;
            }
        }
        return false;
    }

    boolean hasState() {
        return this.size() > 0;
    }

    @Override
    public int compareTo(StateSet o) {
        if (this.size() > o.size()) {
            return 1;
        }
        else if (this.size() < o.size()) {
            return -1;
        }
        else {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getValue().compareTo(o.get(i).getValue()) < 0)
                    return -1;
                else if (this.get(i).getValue().compareTo(o.get(i).getValue()) > 0)
                    return 1;
                else
                    continue;
            }
        }
        return 0;
    }

    void sort() {
        Collections.sort(this);
    }
}
