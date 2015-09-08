package ar.edu.itba.it.paw.controller;

import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanelController extends ControlPanelController {
	
	@Override
	protected String getJspLocation() {
		return JspLocationUtils.ADMIN_PANEL;
	}
}
