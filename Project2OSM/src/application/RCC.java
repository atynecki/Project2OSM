/**
 * @name RCC (red cells counter)
 * @brief simple program for counting erythrocytes in microscopic images of the blood
 * @version 1.0
 *
 * @section DESCRIPTION
 * TODO uzupelniæ! 
 * It is simple application enabling ....   Graphical User Interface based on MCV (Model-View-Controller) pattern. 
 */

package application;

import java.awt.EventQueue;
import mvc.*;

public class RCC {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				AppModel model = new AppModel();
				AppView view = new AppView();
				AppController controller = new AppController(model, view);
				view.setController(controller);
				view.setVisible(true);
			}
		});

	}

}
