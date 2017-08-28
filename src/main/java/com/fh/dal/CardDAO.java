package com.fh.dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fh.entity.Card;

public class CardDAO {
	private static SessionFactory factory;

	// TODO: Card details hashing

	public CardDAO() {
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
			// Card c = (Card) session.get(Card.class, card.getId()); // TODO: Why are we
			// getting the card
			// c.setCard_no(card.getCard_no());
			// c.setCcv(card.getCard_no());
			// c.setAmount(card.getAmount());
			// id = c.getId();
			// session.save(c);
			session.save(card);
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

	public boolean validateCard(int card_no, int ccv, double amount) {
		Session session = factory.openSession();
		Transaction tx = null;
		boolean isValid = false;
		//Query card = null;
		try {
			tx = session.beginTransaction();
			int count = ((Long) session
					.createQuery(
							"Select count(*) FROM Card c WHERE c.card_no = '" + card_no + "' AND c.ccv = '" + ccv
									+ "' AND c.amount >= '" + amount + "'")
					.uniqueResult()).intValue();
			// .list();
			//
			System.out.println("Inside Vaidate Card DAO, COUNT= " + count);
			if (count > 0) {
				isValid = true;
			} else {
				isValid = false;
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

	public static String getEncryptedPassword(String clearTextPassword) {
		String first12digits = "";
		String[] splits = clearTextPassword.split("(?!^)", 13);
		String last4digits = splits[splits.length - 1];
		for (int i = 0; i < splits.length - 1; i++) {
			first12digits = first12digits + splits[i];
		}

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(first12digits.getBytes());
			String s = new sun.misc.BASE64Encoder().encode(md.digest());
			s = s + last4digits;
			return s;
		} catch (NoSuchAlgorithmException e) {
			// _log.error("Failed to encrypt password.", e);
		}
		return "";
	}

}
