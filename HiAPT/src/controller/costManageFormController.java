package controller;

import login.Opener;

public class costManageFormController {
	
	private Opener opener = new Opener();
	
	public void costSelectFormOpen() {
		opener.costSelectFormOpen();
	}
	
	public void costRegFormOpen() {
		opener.costRegFormOpen();
	}
	
	public void costUnpaidFormOpen() {
		opener.costUnpaidFormOpen();
	}

}
