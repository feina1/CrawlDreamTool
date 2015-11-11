import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class getHtmlSource
 */
@WebServlet("/getPage")
public class getPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");//解决中文乱码
		PrintWriter w =response.getWriter();
		String res=CrawlCode.getBody(U.getHtmlDoc(request.getParameter("url"))).html().trim();
		w.write(res);
		w.close();
		

				
		
		
		//String html = U.getHtmlDoc(request.getParameter("url")).html();

//		int headIndex = html.indexOf("<head>");
//		if (headIndex == -1) {
//			System.err.println("no <head>");
//			return;
//		}
//		headIndex += 6;
//
//		int bodyIndex = html.lastIndexOf("</body>");
//		if (bodyIndex == -1) {
//			System.err.println("no </body>");
//			return;
//		}
//
//		html = html.substring(0, headIndex)
//				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style_ibm.css\">"
//				+ html.substring(headIndex, bodyIndex)
//				+ U.file2str(U.root + "console.txt") + html.substring(bodyIndex);
//		try {
//			U.str2file(html, U.root + "target.html");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
