package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//this class is for crud operation

public class BookingEndPoints {

	public static Response createBooking(Booking payload) 
	{		
		Response response= given()
				          .contentType(ContentType.JSON)
				          .body(payload)
			      .when().post(Routes.post_url);
			    
				return response;				
	}
	
	public static Response readBooking(String bookingId) 
	{		
		Response response= given()	
				              .pathParam("bookingid", bookingId)
			          .when().get(Routes.get_url);			    
	                return response;				
	}
	
	public static Response updateBooking(String bookingId,Booking payload,String token) 
	{
		
		Response response= given()
				          .contentType(ContentType.JSON)
				          .pathParam("bookingid", bookingId)
				          .cookie("token", token)
				          .body(payload)
			      .when().put(Routes.put_url);
			    
				return response;				
	}
	
	public static Response deleteBooking(String bookingId,String token) 
	{		
		Response response= given()	
				              .pathParam("bookingid", bookingId)
				              .cookie("token", token)
			          .when().delete(Routes.del_url);			    
	                return response;				
	}
	
	
	
	
}
