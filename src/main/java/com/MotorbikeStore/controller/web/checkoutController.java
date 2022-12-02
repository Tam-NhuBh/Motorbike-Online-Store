package com.MotorbikeStore.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MotorbikeStore.model.CartModel;
import com.MotorbikeStore.model.ProductDetailModel;
import com.MotorbikeStore.service.IProductDetailService;


@WebServlet("/checkout")
public class checkoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Inject
    private IProductDetailService productDetailService;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show number in cart
		Cookie[] listCookie = request.getCookies();
		float prepaidMoney = 0f;
		float totalPrice =0;
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
			
			ProductDetailModel model = new ProductDetailModel();
			
			List<ProductDetailModel> list = new ArrayList<>();
			for(String o: listAmotorId) {
				
				
				list.add(productDetailService.findOneByAmotorId(Integer.parseInt(o)));
			}
			for(ProductDetailModel o: list) {
				prepaidMoney = o.getPrice()*0.25f;
				o.setMoneyPrepaid(prepaidMoney);
				totalPrice += prepaidMoney;
			}
			model.setListResult(list);
			
			request.setAttribute("model", model);
		}
		cart.setTotalPrice(totalPrice);
		
		
		
		
		
		request.setAttribute("cart", cart);
		RequestDispatcher rd = request.getRequestDispatcher("views/web/checkout.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
