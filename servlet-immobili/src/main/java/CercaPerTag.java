
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CercaPerTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CercaPerTag() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		String[] splittedPath = pathInfo.split("/");
		
		QueryService qs = new QueryService();
		try{
			BigInteger id_tag = new BigInteger(splittedPath[1]);
			HashMap<BigInteger, Tag> tags = qs.mostraTag();
			Tag tag = tags.get(id_tag);
			if (tag == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			HashMap<BigInteger, Immobile> possedimenti = qs.mostraImmobili(null, null, null, null, null, null, null, null, null, null, id_tag.toString());
			request.setAttribute("immobili", possedimenti);
			request.setAttribute("tag", tag);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/cercaPerTag.jsp");
			rd.forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
