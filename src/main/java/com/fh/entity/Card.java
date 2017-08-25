package com.fh.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Card
{
	private int card_no;
	private int ccv;
	private double amount;
	private int id;
	
	public Card() {

	}

	public Card(int id, int card_no, int ccv, double amount) {
		this.id = id;
		this.card_no = card_no;
		this.ccv = ccv;
		this.amount = amount;

	}

	public int getCard_no() {
		return card_no;
	}

	@XmlElement
	public void setCard_no(int card_no) {
		this.card_no = card_no;
	}

	public int getCcv() {
		return ccv;
	}

	@XmlElement
	public void setCcv(int ccv) {
		this.ccv = ccv;
	}

	public double getAmount() {
		return amount;
	}

	@XmlElement
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}


}
