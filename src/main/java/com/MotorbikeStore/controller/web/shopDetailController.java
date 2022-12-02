package com.MotorbikeStore.controller.web;

import java.io.IOException;
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
import com.MotorbikeStore.model.PictureModel;
import com.MotorbikeStore.model.ProductDetailModel;
import com.MotorbikeStore.model.commentModel;
import com.MotorbikeStore.model.specificationModel;
import com.MotorbikeStore.service.ICommentService;
import com.MotorbikeStore.service.IPictureService;
import com.MotorbikeStore.service.IProductDetailService;
import com.MotorbikeStore.service.ISpecificationService;

@WebServlet("/notaddshopDetail")
public class shopDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private IProductDetailService ProductDetailService;
	@Inject
	private IPictureService PictureService;
	@Inject
	private ICommentService commentService;
	@Inject
	private ISpecificationService specificationService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		float moneyPrepaid = 0f;
		int aMotorId = Integer.parseInt(request.getParameter("id"));
		ProductDetailModel model = new ProductDetailModel();
		model = ProductDetailService.findOneByAmotorId(aMotorId);
		moneyPrepaid = model.getPrice()*0.25f;
		model.setMoneyPrepaid(moneyPrepaid);
		// Select alsolike
		ProductDetailModel modelAlso = new ProductDetailModel();
		PictureModel modelpicAlso = new PictureModel();
		
		List<ProductDetailModel> listAlso = ProductDetailService.filterBranch(model.getBranch());
		for (ProductDetailModel o : listAlso) {

			modelpicAlso = PictureService.findOneByAmotorId(o.getaMotorId());
			if (modelpicAlso != null) {
				o.setPicName(modelpicAlso.getPicName());
			}

		}
		modelAlso.setListResult(listAlso);
		request.setAttribute("modelAlso", modelAlso);
		//
		specificationModel modelSpe = new specificationModel();
		modelSpe = specificationService.findOne(model.getMotorsId());

		request.setAttribute("model", model);
		PictureModel model2 = new PictureModel();
		PictureModel model3 = new PictureModel();

		List<PictureModel> list = PictureService.findByAMotorId(aMotorId);

		for (PictureModel o : list) {
			model3.setPicName(o.getPicName());
			list.remove(0);
			break;
		}
		model2.setListResult(list);

		// show number in cart
		Cookie[] listCookie = request.getCookies();
		String valueCartCookie = "";
		if (listCookie != null) {
			for (Cookie o : listCookie) {
				if (o.getName().equals("cart")) {
					valueCartCookie += o.getValue();

				}
			}
		}
		CartModel cart = new CartModel();
		if (!valueCartCookie.isEmpty()) {
			String[] listAmotorId = new String[100];
			if (valueCartCookie != null && valueCartCookie.length() != 0) {
				listAmotorId = valueCartCookie.split("/");
			}
			cart.setQuantity(listAmotorId.length);
		}
		request.setAttribute("modelSpe", modelSpe);
		request.setAttribute("cart", cart);
		request.setAttribute("model3", model3);
		request.setAttribute("model2", model2);

		commentModel modelComment = new commentModel();
		commentModel modelComment2 = new commentModel();
		List<commentModel> listComment = commentService.findByaMotorId(aMotorId);
		modelComment2.setQuantity(listComment.size());
		modelComment.setListResult(listComment);

		request.setAttribute("modelComment", modelComment);
		request.setAttribute("modelComment2", modelComment2);
		RequestDispatcher rd = request.getRequestDispatcher("views/web/shopDetail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
