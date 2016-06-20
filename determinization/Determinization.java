/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: Determinizazion.
 * Content: Main app's class.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory
*/

package determinization;

import java.awt.*;

public class Determinization extends Canvas {
    public static final String TITLE = "Determinizace teselačního automatu";
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static void main(String[] args) {
        Determinization determinization = new Determinization();

        determinization.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        determinization.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        determinization.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        StateQueue states = new StateQueue();
        SymbolStack symbols = new SymbolStack();
        RuleStack rules =  new RuleStack();

        Controller controller = new Controller(states, symbols, rules);

        new AppWindow(TITLE, WIDTH, HEIGHT, controller);
    }
}
