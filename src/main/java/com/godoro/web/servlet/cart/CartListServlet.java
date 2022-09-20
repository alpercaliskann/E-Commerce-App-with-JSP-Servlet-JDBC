package com.godoro.web.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.godoro.core.utils.XmlHelper;
import com.godoro.database.entity.Cart;
import com.godoro.database.manager.CartManager;
import com.godoro.xml.CartXmlManager;

@WebServlet("/admin/carts")
public class CartListServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		
		
		CartManager cartManager = new CartManager();
		
		List<Cart> cartList = null;
		try {
			cartList = cartManager.list();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		CartXmlManager cartXmlManager = new CartXmlManager();
		Document document = null;
		try {
			document = cartXmlManager.format(cartList, "carts","cart");
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/xml; charset=UTF-8");
		try {
			XmlHelper.dump(document,response.getOutputStream());
		} catch (TransformerException | IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
