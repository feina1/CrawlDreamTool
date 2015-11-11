import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Servlet implementation class crawl
 */
@WebServlet("/crawl")
public class crawl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public crawl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setContentType("text/html;charset=GBK");// 解决中文乱码
			// HashMap<String, ArrayList<Integer>> rules = (HashMap<String,
			// ArrayList<Integer>>) U
			// .jsonStr2object(U.file2str(U.cdtPath
			// + request.getParameter("rule")), HashMap.class);
			HashMap<String, ArrayList<String>> name_value = CrawlCode
					.crawlPageAccordingToRule(U.getHtmlDoc(request.getParameter("url")),
							request.getParameter("rule"));

			ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
			HashMap<String, ArrayList<String>> name_path=(HashMap<String, ArrayList<String>>) 
					U.jsonStr2object(request.getParameter("rule"), HashMap.class);
			
			for (String name : name_value.keySet()) {
				ArrayList<String> al = new ArrayList<String>();
				res.add(al);
				//add name
				al.add(name);
				//add value
				ArrayList<String> values = name_value.get(name);
				if (values.size() == 0)
					al.add("");
				else if (values.size() == 1)
					al.add(values.get(0));
				else if (values.size() > 1) {
					String s = "[";
					for (String v : values)
						s += v + ",";
					s += "]";
					al.add(s);
				}
				// add path
				al.add(U.arraylist2line(name_path.get(name)));
			}

			PrintWriter w = response.getWriter();
			w.write(U.object2jsonStr(res));
			System.out.println(U.object2jsonStr(res));
			w.close();
		} catch (Exception e) {
			 ArrayList<ArrayList<String>> res = new
			 ArrayList<ArrayList<String>>();
			 PrintWriter w = response.getWriter();
			 w.write("[[\"error occured at crawling step\",\"\",\"\"]]");
			 w.close();
			e.printStackTrace();
		}
		
	}

}
