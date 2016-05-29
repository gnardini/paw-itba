package ar.edu.itba.it.paw.web;

import java.net.URL;
import java.net.URLClassLoader;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.pages.InternalErrorPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.CryptoMapper;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.page.RestaurantsPage;

@Component
public class App extends WebApplication {

	private final SessionFactory sessionFactory;
	private final UserRepo userRepo;

	@Autowired
	public App(SessionFactory sessionFactory, UserRepo userRepo) {
		this.sessionFactory = sessionFactory;
		this.userRepo = userRepo;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return RestaurantsPage.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
		setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
		getApplicationSettings().setPageExpiredErrorPage(RestaurantsPage.class);
		getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
	}
	
	 public String getClasspathString() {
	     StringBuffer classpath = new StringBuffer();
	     ClassLoader applicationClassLoader = this.getClass().getClassLoader();
	     if (applicationClassLoader == null) {
	         applicationClassLoader = ClassLoader.getSystemClassLoader();
	     }
	     URL[] urls = ((URLClassLoader)applicationClassLoader).getURLs();
	      for(int i=0; i < urls.length; i++) {
	          classpath.append(urls[i].getFile()).append("\r\n");
	      }    
	      return classpath.toString();
	  }
	
	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSessionManager(request, userRepo);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		
		return converterLocator;
	}
	
}
