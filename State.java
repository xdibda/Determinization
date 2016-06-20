/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: State.
 * Content: An instance representing state.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;

public class State implements Comparable<State> {

    ArrayList<Role> roles;
    String value;

    State(String value) {
        this.value = value;
        roles = new ArrayList<>();
    }

    State(String value, Role... roles) {
        this(value);
        for (Role role: roles) {
            if (role != null)
                this.roles.add(role);
        }
    }

    public boolean isFinite() {
        for (Role role: roles) {
            if (role == Role.FINITE)
                return true;
        }
        return false;
    }

    public boolean isInit() {
        for (Role role: roles) {
            if (role == Role.INIT)
                return true;
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(State o) {
        if (this.getValue().compareTo(o.getValue()) > 0) {
            return 1;
        }
        else if (this.getValue().compareTo(o.getValue()) < 0) {
            return -1;
        }
        return 0;
    }
}
