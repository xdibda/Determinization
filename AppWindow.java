/*
 * This application has been made for my Bachelor's thesis. All right belongs to FIT VUT in Brno and myself.
 * Year: 2015/2016
 * Date: 10.5.2016
 * Author: Lukáš Dibďák.
 * Application: Determinization of on-line tesselation automata.
 * Class: AppWindow.
 * Content: Creates the main app window.
 * License: There is a license for this code, more at /src/licenses/LICENSE_VUT.txt or in readme.txt in root directory 
*/

package determinization;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    private MainPanel mainPanel;

    AppWindow(String title, int width, int height, Controller controller) {
        super(title);

        setLayout(new BorderLayout());

        mainPanel = new MainPanel(controller);
        add(mainPanel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
