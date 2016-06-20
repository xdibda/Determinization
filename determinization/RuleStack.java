/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: RuleStack.
 * Content: Set of rules.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;
import java.util.Collections;

public class RuleStack extends ArrayList<Rule> {
    RuleStack() {}

    RuleStack(RuleStack rules) {
        for (Rule rule: rules) {
            this.addRule(rule);
        }
    }

    public boolean addRule(Rule rule) {
        return this.add(rule);
    }

    void sort() {
        Collections.sort(this);
    }
}
