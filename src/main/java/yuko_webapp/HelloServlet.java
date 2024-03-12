package yuko_webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {



			    PrintWriter out = response.getWriter();

			    out.println("Hello Servlet!");

			 // Hello.jsp にページ遷移
			    RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
			    dispatch.forward(request, response);



			  }



}
