package com.MotorbikeStore.controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MotorbikeStore.model.CartModel;


@WebServlet("/meetingPlan")
public class meetingPlanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//show number in cart
		Cookie[] listCookie = request.getCookies();
		String valueCartCookie = "";
		if(listCookie != null) {
			for(Cookie o: listCookie) {
				if(o.getName().equals("cart")) {
					valueCartCookie += o.getValue();
					
				}
			}
		}
		CartModel cart = new CartModel();
		if(!valueCartCookie.isEmpty()) {
			String[] listAmotorId = new String[100];
			if(valueCartCookie != null && valueCartCookie.length() != 0 ) {
				listAmotorId = valueCartCookie.split("/");
			}
			cart.setQuantity(listAmotorId.length);
		}
		request.setAttribute("cart", cart);
		RequestDispatcher rd = request.getRequestDispatcher("views/web/meetingPlan.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
