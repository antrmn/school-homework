


import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FiltraProprietari extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FiltraProprietari() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QueryService qs = new QueryService();
		String tipo = request.getParameter("tipo");
		if(tipo == null || tipo.isEmpty()) {
			tipo = null;
		}
		
		String tag = request.getParameter("tag");
		if(tag == null || tag.isEmpty()) {
			tag = null;
		}
		
		
		HashMap <BigInteger, Proprietario> proprietari = qs.filtraProprietari(tipo,tag);
		
		for(BigInteger key : proprietari.keySet()) {
			Proprietario prop = proprietari.get(key);
			prop.setNumero_possedimenti(qs.ContaPossedimenti(key));
			prop.setTotale_superficie(qs.calcolaTotaleSuperficie(key));
		}
		request.setAttribute("tags_lista", qs.mostraTag());
		request.setAttribute("tipi", Immobile.Tipo.values());
		request.setAttribute("proprietari", proprietari);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/elencaProprietari.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
