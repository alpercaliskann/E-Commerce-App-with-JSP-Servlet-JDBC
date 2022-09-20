package com.godoro.web.servlet.category;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.godoro.core.utils.XmlHelper;
import com.godoro.database.entity.Category;
import com.godoro.database.manager.CategoryManager;
import com.godoro.xml.CategoryXmlManager;

@WebServlet("/api/findCategory")
public class CategoryFindServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		long categoryId =Long.parseLong(request.getParameter("id"));
		
		CategoryManager categoryManager = new CategoryManager();
		Category category = null;
		try {
			category = categoryManager.find(categoryId);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		

		Document document = null;
		try {
			CategoryXmlManager categoryXmlManager = new CategoryXmlManager();
			document = categoryXmlManager.format(category,"category");
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
