package mvc;

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
					cView.cleanLaboratoryView();
				break;
				
			case "Image load":
				cView.loadImage();
				break;
			
			case "Analyze":
				break;
			
			case "Save result":
				try{
					cModel.setPatient(cView.readPatientView());
					cModel.savePatientData();
					cModel.saveResultImage();
				} catch (AppException exception){
					exception.show_exception();
				}
				
				break;
				
		}
	}
	
}
