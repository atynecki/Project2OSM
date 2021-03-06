package mvc;

/**
 * @class AppController
 * @brief class representing application control. Implements user interaction, model and views update.
 * Contains AppModel, AppView
 * @implements ActionListener
 */

import java.awt.event.*;

public class AppController implements ActionListener {

	private AppModel cModel = null;
	private AppView cView = null;
	
	/** parameterized constructors */
	public AppController (AppModel model, AppView view) {
		this.cModel = model;
		this.cView = view;
		this.cModel.setController(this);
	}

	/**
	 * @fn actionPerformed()
	 * @brief action event handler
	 * @param action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Object actionSource = e.getSource();
		
		switch(actionCommand){
			case "Close":
				System.exit(0);
				break;
			
			case "About":
				cView.setAboutFrame();
				break;
				
			case "Clear":
				if(actionSource.equals(cView.getAppButtonPatientClear()))
					cView.cleanPatientView();
				else if(actionSource.equals(cView.getAppButtonLaboratoryClear()))
					cView.cleanClinicView();
				break;
				
			case "Image load":
				cView.setImageView(cModel.loadImage(cView.getSelectedPath()));
				break;
			
			case "Analyze":
				cView.setResultView(cModel.processImage(), cModel.getNumberEryth());
				cView.setOrderNumberView(cModel.generateOrderNumber());
				break;
			
			case "Save result":
			 try{
					cModel.setPatient(cView.readPatientView());
					cModel.setClinic(cView.readClinicView());
					cModel.saveData();
				} catch (AppException exception){
					exception.show_exception();
				}
				
				break;
		}
	}
	
}
