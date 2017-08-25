package com.fh.entity;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageCard { // TODO: move to DAL package (CardDAO)
	private static SessionFactory factory;

	// TODO: Card details hashing

	public ManageCard() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Card getCard(int id) {
		Session session = factory.openSession();
		System.out.println("Inside getCard");
		Transaction tx = null;
		Card card = null;
		try {
			tx = session.beginTransaction();
			card = (Card) session.get(Card.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return card;
	}

	public List<Card> listCard() {
		Session session = factory.openSession();
		System.out.println("Inside ListCard");
		Transaction tx = null;
		List<Card> cardList = null;
		try {
			tx = session.beginTransaction();
			cardList = session.createQuery("FROM Card").list();
			/*
			 * for (Iterator iterator = flightList.iterator(); iterator.hasNext();) {
			 * FlightDetail flight = (FlightDetail) iterator.next();
			 * System.out.print("Source: " + flight.getSource());
			 * System.out.print(" :: Flight#: " + flight.getPrice()); }
			 */
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cardList;
	}

	public int addCard(Card card) {
		Session session = factory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			Card c = (Card) session.get(Card.class, card.getId()); // TODO: Why are we getting the card
			c.setCard_no(card.getCard_no());
			c.setCcv(card.getCard_no());
			c.setAmount(card.getAmount());
			id = c.getId();
			session.save(c);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	/* Method to UPDATE flight number of a flight */
	public int updateCard(Card card) {
		Session session = factory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			Card c = (Card) session.get(Card.class, card.getId());
			c.setCard_no(card.getCard_no());
			c.setCcv(card.getCard_no());
			c.setAmount(card.getAmount());
			id = c.getId();
			session.update(c);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	/* Method to DELETE an flight from the records */
	public int deleteCard(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		int getID = 0;
		Card card = null;
		try {
			tx = session.beginTransaction();
			card = (Card) session.get(Card.class, id);

			System.out.println("Inside Manage Delete Card");
			getID = card.getId();
			session.delete(card);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return getID;
	}

	public boolean checkCard(int card_no, int ccv) { // TODO: name it as validateCard
		Session session = factory.openSession();
		Transaction tx = null;
		boolean isValid = false;
		try {
			tx = session.beginTransaction();
			if (session
					.createQuery("FROM Card c WHERE c.card_no = '" + card_no + "' AND c.ccv = '" + ccv + "'") != null) {
				isValid = true;
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isValid;
	}

	// public boolean checkLogin(String username, String password) {
	// Session session = factory.openSession();
	// System.out.println("Inside ListFlight");
	// Transaction tx = null;
	// // String pnr = null;
	// boolean isValid = false;
	// try {
	// tx = session.beginTransaction();
	// if (session.createQuery("FROM Passenger p WHERE p.username = '" + username +
	// "' AND p.password = '"
	// + password + "'") != null) {
	// isValid = true;
	// // pnr = randomPNRGenerator(6);
	// }
	// else
	// System.out.println("Result not found!!");
	// tx.commit();
	// } catch (HibernateException e) {
	// if (tx != null)
	// tx.rollback();
	// e.printStackTrace();
	// } finally {
	// session.close();
	// }
	// // return pnr;
	// return isValid;
	// }
}
