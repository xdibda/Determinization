/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: Symbol.
 * Content: An instance of symbol.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

public class Symbol implements Comparable<Symbol> {
    char value;

    Symbol(char value) {
        this.value = value;
    }

    char getValue() {
        return value;
    }

    @Override
    public int compareTo(Symbol o) {
        if (this.getValue() > o.getValue()) {
            return 1;
        }
        else if (this.getValue() < o.getValue()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
