/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: MapQueue.
 * Content: Mapping doubles.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.HashMap;
import java.util.Map;

public class MapQueue extends HashMap<StateSet, StateQueue> {
    void store(StateQueue top, StateQueue left) {
        for (StateSet topState: top) {
            for (StateSet leftState: left) {
                boolean found = false;
                for (StateSet key: this.keySet()) {
                    if (key.equals(topState)) found = true;
                }
                StateQueue tmp;
                if (found) {
                    tmp = this.get(topState);
                    tmp.addState(leftState);
                    this.put(topState, tmp);
                }
                else {
                    tmp = new StateQueue(leftState);
                    this.put(topState, tmp);
                }
            }
        }
    }

    boolean testCondition(DiagonalQueue queue, StateSet init) {
        DiagonalQueue tmp = queue.addBoundary(init);

        while (tmp.testCondition()) {
            StateQueue top = tmp.getTopStates();
            StateQueue left = tmp.getLeftStates();

            for (StateSet topState: top) {
                for (StateSet leftState: left) {
                    boolean mapFound = false;
                    for (Map.Entry<StateSet, StateQueue> set: this.entrySet()) {
                        if (set.getKey().equals(topState)) {
                            for (StateSet value: set.getValue()) {
                                if (value.equals(leftState))
                                    mapFound = true;
                            }
                        }
                    }
                    if (!mapFound) return true;
                }
            }
        }
        return false;
    }
}
