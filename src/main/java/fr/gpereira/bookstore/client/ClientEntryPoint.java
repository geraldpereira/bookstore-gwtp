package fr.gpereira.bookstore.client;


public class ClientEntryPoint implements com.gwtplatform.mvp.client.ApplicationController {

	@Override
	public void init() {
	}

	@Override
	public void onModuleLoad() {
		// Configure RestyGWT
        // Date format !
        org.fusesource.restygwt.client.Defaults.setDateFormat(null);
	}
}
