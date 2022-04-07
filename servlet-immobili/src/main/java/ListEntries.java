
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListEntries extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListEntries() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		QueryService qs = new QueryService();
		
		HashMap<BigInteger,Immobile> immobili = qs.mostraImmobili();
		HashMap<BigInteger, Proprietario> proprietari = qs.mostraProprietari();
		HashMap<BigInteger, ArrayList<Tag>> tags_immobili = qs.mostraTagImmobile();
		for(BigInteger Key : tags_immobili.keySet()) {
			immobili.get(Key).setTags(tags_immobili.get(Key));
		}
		request.setAttribute("tags_lista", qs.mostraTag());
		request.setAttribute("immobili", immobili);
		request.setAttribute("proprietari", proprietari);
		request.setAttribute("tipi", Immobile.Tipo.values());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
