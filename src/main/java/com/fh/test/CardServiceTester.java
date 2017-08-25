package com.fh.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.fh.entity.Card;

public class CardServiceTester { // TODO: move to test package

   private Client client;
	private String REST_SERVICE_URL = "http://localhost:9080/CardRestService/rest/CardService";
   private static final String SUCCESS_RESULT="<response>SUCCESS</response>";
   private static final String PASS = "pass";
   private static final String FAIL = "fail";

   private void init(){
      this.client = ClientBuilder.newClient();
   }

   public static void main(String[] args){
      CardServiceTester tester = new CardServiceTester();
      //initialize the tester
      tester.init();
      //test get all Cars Web Service Method
      tester.testGetAllCards();
      //test get Car Web Service Method 
      tester.testGetCard();
      //test update Car Web Service Method
      tester.testUpdateCard();
      //test add Car Web Service Method
    //  tester.testAddCard();
      //test delete Car Web Service Method
    //  tester.testDeleteCard();
   }
   //Test: Get list of all Cars
   //Test: Check if list is not empty
   private void testGetAllCards(){
      GenericType<List<Card>> list = new GenericType<List<Card>>() {};
      List<Card> Cars = client
         .target(REST_SERVICE_URL + "/list")
         .request(MediaType.APPLICATION_XML)
         .get(list);
      String result = PASS;
      if(Cars.isEmpty()){
         result = FAIL;
      }
      System.out.println("Test case name: testGetAllCards, Result: " + result );
   }
   //Test: Get Car of id 1
   //Test: Check if Car is same as sample Car
   private void testGetCard(){
      Card sampleCard = new Card();
      sampleCard.setId(1);

      Card Card = client
         .target(REST_SERVICE_URL)
         .path("/card/{Carid}")
         .resolveTemplate("Carid", 1)
         .request(MediaType.APPLICATION_XML)
         .get(Card.class);
      String result = FAIL;
      if(sampleCard != null && sampleCard.getId() == Card.getId()){
         result = PASS;
      }
      System.out.println("Test case name: testGetCar, Result: " + result );
   }
   //Test: Update Card of id 1
   //Test: Check if result is success XML.
   private void testUpdateCard(){
      Form form = new Form();
      form.param("id", "1");
		form.param("card_no", "1234567890");
		form.param("ccv", "123");
		form.param("amount", "1000.00");
     
      String callResult = client
         .target(REST_SERVICE_URL + "/modify")
         .request(MediaType.APPLICATION_XML)
         .post(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testUpdateCard, Result: " + result );
   }
   //Test: Add Car of id 2
   //Test: Check if result is success XML.
   private void testAddCard(){
      Form form = new Form();
      form.param("id", "2");
		form.param("card_no", "1234567891");
		form.param("ccv", "456");
		form.param("amount", "2000.00");
      
      String callResult = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .post(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      System.out.println("sdaff" + callResult);
   
      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testAddCar, Result: " + result );
   }
   //Test: Delete Car of id 2
   //Test: Check if result is success XML.
   private void testDeleteCar(){
      String callResult = client
         .target(REST_SERVICE_URL)
         .path("/{Carid}")
         .resolveTemplate("Carid", 2)
         .request(MediaType.APPLICATION_XML)
         .delete(String.class);

      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testDeleteCar, Result: " + result );
   }
}