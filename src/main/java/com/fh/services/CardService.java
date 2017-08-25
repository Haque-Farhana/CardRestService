package com.fh.services;
 
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fh.entity.Card;
import com.fh.entity.ManageCard;

@Path("/CardService")
public class CardService 
{
	
	private final static String SUCCESS = "<response>SUCCESS</response>";
	private final static String FAILURE = "<response>FAILURE</response>";
	
	ManageCard card = new ManageCard();
	
	@GET
	@Path("/card/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Card getCard(@PathParam("id") int id)
	{
		System.out.println("Hello");
		return card.getCard(id);		
	}


	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_XML)
	public List<Card> getCardList()
	{
		return card.listCard();
	}
 
	@POST
	@Path("/modify")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String modifyCard(@FormParam("id") int id, 
			@FormParam("card_no") int card_no, @FormParam("ccv") int ccv, @FormParam("amount") double amount)
	{ 
	
		Card c = new Card(id, card_no, ccv, amount);
		if (card.updateCard(c) == id)
		{
			return SUCCESS;
		}
		else
		{
			return FAILURE;
		}		
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String removeCard(@PathParam("id") int id) {
		System.out.println("Inside Delete Card Services");
		if (card.deleteCard(id) == id)
			return SUCCESS;
		else
			return FAILURE;
	}

	@POST
	@Path("/checkCard")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String modifyCard(@FormParam("card_no") int card_no, @FormParam("ccv") int ccv) {

		// Card c = new Card(id, card_no, ccv, amount);
		if (card.checkCard(card_no, ccv)) {
			return SUCCESS;
		} else {
			return FAILURE;
		}
	}
 
}
