package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.AuthEndPoints;
import api.endpoints.BookingEndPoints;
import api.payload.Booking;
import api.payload.Bookingdates;
import io.restassured.response.Response;

public class BookingTest {

	Faker faker;
	Booking bookingPayload;
	int bookingId;
	String token;
	
	@BeforeClass
	public void setupData() {
		
		token = AuthEndPoints.generateToken();
		faker=new Faker();
		bookingPayload=new Booking();
		
		bookingPayload.setFirstname("Chins");
		bookingPayload.setLastname("Kumar");
		bookingPayload.setTotalprice(100);
		bookingPayload.setDepositpaid(true);
		
		Bookingdates bookingDates=new Bookingdates();
		bookingDates.setCheckin("2018-01-01");
		bookingDates.setCheckout("2019-01-01");
				
		bookingPayload.setBookingdates(bookingDates);
		bookingPayload.setAdditionalneeds("Breakfast");
		
	}
	
	@Test(priority = 1)
	public void testPostBooking() {
		Response response=BookingEndPoints.createBooking(bookingPayload);
		response.then().log().all();
		
		//Assert.assertEquals("booking.firstname", equals(bookingPayload.getFirstname()));
		
		String contentType = response.getHeader("Content-Type");
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
		
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("booking.firstname"), bookingPayload.getFirstname());
	    Assert.assertEquals(response.jsonPath().getString("booking.lastname"), bookingPayload.getLastname());
	    Assert.assertEquals((Integer) response.jsonPath().getInt("booking.totalprice"), bookingPayload.getTotalprice());
	    Assert.assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), bookingPayload.getDepositpaid());
	    Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"), bookingPayload.getBookingdates().getCheckin());
	    Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"), bookingPayload.getBookingdates().getCheckout());
	    Assert.assertEquals(response.jsonPath().getString("booking.additionalneeds"), bookingPayload.getAdditionalneeds());
			
	    bookingId = response.jsonPath().getInt("bookingid");
	    
	}
	
	@Test(priority = 2,dependsOnMethods = "testPostBooking")
	public void testGetBooking() {
		Response response = BookingEndPoints.readBooking(String.valueOf(bookingId));
	    response.then().log().all();

	    Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 3, dependsOnMethods = "testGetBooking")
	public void testUpdateBooking() {
	    // Update some fields
	    bookingPayload.setFirstname("UpdatedName");
	    bookingPayload.setLastname("UpdatedLast");
	    bookingPayload.setAdditionalneeds("Lunch");

	    Response response = BookingEndPoints.updateBooking(String.valueOf(bookingId), bookingPayload,token);
	    response.then().log().all();

	    Assert.assertEquals(response.getStatusCode(), 200);

	    // Validate updated fields
	    Assert.assertEquals(response.jsonPath().getString("firstname"), "UpdatedName");
	    Assert.assertEquals(response.jsonPath().getString("lastname"), "UpdatedLast");
	    Assert.assertEquals(response.jsonPath().getString("additionalneeds"), "Lunch");
	}
	
	@Test(priority = 4, dependsOnMethods = "testUpdateBooking")
	public void testDeleteBooking() {
	    Response response = BookingEndPoints.deleteBooking(String.valueOf(bookingId),token);
	    response.then().log().all();

	    Assert.assertEquals(response.getStatusCode(), 201); // API returns 201 for successful delete
	}
}
