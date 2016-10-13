package com.project.controller;

/**
 * Class to manage the Resource API 
 * @author Vivek Mittal
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.BookingsVO;
import com.project.service.BookingsFacade;


@Controller
public class BookingsAPIController {
	
	//To interact with the facade layer
	@Autowired
	private BookingsFacade  bookingsFacade;
	
	//To get the beans
	@Autowired
	private ApplicationContext context;
	
	/**
	 * Following function returns the list of all pending bookings.
	 * @return Response object showing the list of all pending bookings.
	 */
	@RequestMapping(value = "/bookings/getPendingbookings", method = RequestMethod.GET)
	public @ResponseBody Response getPendingBookingsList() {
		//Getting the result from the facade
		List<BookingsVO> result = bookingsFacade.pendingBookingsList();
		
		//Sending back the response to the client
		if(result != null) {
			System.out.println("OK");
			return new Response(200, result);
		} else {
			System.out.println("Wrong");
			return new Response(400, "No Pending bookings");
		}
	}
}
