package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.util.Assert;

public class HibernateRequestCycleListener extends AbstractRequestCycleListener {

	private final SessionFactory sessionFactory;
	private static ThreadLocal<Boolean> error = new ThreadLocal<Boolean>();

	public HibernateRequestCycleListener(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void onBeginRequest(RequestCycle cycle) {
		Assert.state(!ManagedSessionContext.hasBind(sessionFactory), "Session already bound to this thread");
		Session session = sessionFactory.openSession();
		ManagedSessionContext.bind(session);
		System.out.println("Begin transaction");
		session.beginTransaction();
		error.set(false);
	}
	
	@Override
	public void onEndRequest(RequestCycle cycle) {
		if (!error.get()) {
			System.out.println("Close transaction");
			commit();
		} else {
			System.out.println("Rollback");
			rollback();
		}
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		rollback();
		System.out.println("Exception");
		error.set(true);
		return null;
	}
	
	private void commit() {
		Session session = sessionFactory.getCurrentSession();
		Assert.state(session.isOpen(), "Can't commit a closed session!");
		System.out.println("Commit transaction");
		try {
			Transaction tx = session.getTransaction();
			if (tx.isActive()) {
				session.flush();
				System.out.println("Actual commit transaction");
				tx.commit();
			}
		} finally {
			close(session);
		}
	}

	private void rollback() {
		Session session = sessionFactory.getCurrentSession();
		Assert.state(session.isOpen(), "Can't rollback a closed session!");
		try {
			Transaction tx = session.getTransaction();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			close(session);
		}
	}

	private void close(Session session) {
		ManagedSessionContext.unbind(sessionFactory);
		session.close();
	}
}

