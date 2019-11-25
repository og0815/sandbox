import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/*")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {               
		PrintWriter out = response.getWriter();
		out.println("<h1>Hello from Servlet</h1><hr />");
                out.println("<h2>Headers</h2><ul>");
                for(Enumeration<String> ename = request.getHeaderNames(); ename.hasMoreElements(); ) {
                    String name = ename.nextElement();
                    out.print("<li>" + name + " = " + request.getHeader(name) + "</li>");
                }
                out.println("<ul>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
