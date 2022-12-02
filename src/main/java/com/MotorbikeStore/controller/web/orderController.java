package com.MotorbikeStore.controller.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MotorbikeStore.model.CartModel;
import com.MotorbikeStore.model.customerModel;
import com.MotorbikeStore.model.paymentModel;
import com.MotorbikeStore.service.IProductDetailService;
import com.MotorbikeStore.service.IcustomerService;
import com.MotorbikeStore.service.IpaymentService;
import com.MotorbikeStore.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/order")
public class orderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private IpaymentService paymentService;
	@Inject
	private IcustomerService customerService;
	@Inject 
	private IProductDetailService productDetailService;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		request.setCharacterEncoding("UTF-8");

		response.setContentType("application/json");
		customerModel customer = HttpUtil.of(request.getReader()).toModel(customerModel.class);
		
	
		//get cookie
		Cookie[] listCookie = request.getCookies();
		CartModel cart = new CartModel();
		float totalPrice = 0;
		String valueCartCookie = "";
		if (listCookie != null) {
			for (Cookie o : listCookie) {
				if (o.getName().equals("cart")) {
					valueCartCookie += o.getValue();

				}
			}

		}

		if (!valueCartCookie.isEmpty()) {
			int cusId = customerService.save(customer);
			paymentModel payment = new paymentModel();
			String[] listAmotorId = new String[100];
			if (valueCartCookie != null && valueCartCookie.length() != 0) {
				listAmotorId = valueCartCookie.split("/");
			}
			
			for (String o : listAmotorId) {
				payment.setPrice(productDetailService.findOneByAmotorId(Integer.parseInt(o)).getPrice());
				payment.setCusId(cusId);
				payment.setaMotorId(Integer.parseInt(o));
				paymentService.save(payment);
			}
			
			
			//delete cookie
			valueCartCookie = "";
			for(Cookie o: listCookie) {
				if(o.getName().equals("cart")) {
					o.setMaxAge(0);
					response.addCookie(o);
					
				}
			}
			Cookie c = new Cookie("cart", valueCartCookie);
			c.setMaxAge(2*24*60*60);
			response.addCookie(c);
			
		}
		response.sendRedirect("cart");
		
		
		
		
		
		

	}

}
