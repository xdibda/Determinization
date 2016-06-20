/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: SymbolStack.
 * Content: Set of symbols.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.util.ArrayList;
import java.util.Collections;

public class SymbolStack extends ArrayList<Symbol> {
    SymbolStack() {}

    SymbolStack(SymbolStack symbolStack) {
        for (Symbol symbol: symbolStack) {
            this.add(symbol);
        }
    }

    void addSymbol(Symbol symbol) {
        add(symbol);
    }

    void sort() {
        Collections.sort(this);
    }

    ArrayList<String> getNameSymbols() {
        ArrayList<String> tmp = new ArrayList<>();

        for (Symbol symbol: this) {
            tmp.add(" " + symbol.getValue());
        }

        return tmp;
    }
}
