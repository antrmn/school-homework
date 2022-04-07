
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MostraDettagliProprietario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MostraDettagliProprietario() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		String[] splittedPath = pathInfo.split("/");
		
		QueryService qs = new QueryService();
	
		
		try{
			System.out.println(splittedPath[1]);
			String tipo = request.getParameter("tipo");
			if(tipo == null || tipo.isEmpty()) {
				tipo = null;
			}
			
			String tag = request.getParameter("tag");
			if(tag == null || tag.isEmpty()) {
				tag = null;
			}
			
			String sup_min = request.getParameter("superficie_min");
			if(sup_min == null || sup_min.isEmpty()) {
				sup_min = null;
			}
			
			String sup_max = request.getParameter("superficie_max");
			if(sup_max == null || sup_max.isEmpty()) {
				sup_max = null;
			}
			
			String vani_min = request.getParameter("vani_min");
			if(vani_min == null || vani_min.isEmpty()) {
				vani_min = null;
			}
			
			String vani_max = request.getParameter("vani_max");
			if(vani_max == null || vani_max.isEmpty()) {
				vani_max = null;
			}
			
			String anno_min = request.getParameter("anno_min");
			if(anno_min == null || anno_min.isEmpty()) {
				anno_min = null;
			}
			
			String anno_max = request.getParameter("anno_max");
			if(anno_max == null || anno_max.isEmpty()) {
				anno_max = null;
			}
			
			String prezzo_min = request.getParameter("prezzo_min");
			if(prezzo_min == null || prezzo_min.isEmpty()) {
				prezzo_min = null;
			}
			
			String prezzo_max = request.getParameter("prezzo_max");
			if(prezzo_max == null || prezzo_max.isEmpty()) {
				prezzo_max = null;
			}
			
			BigInteger id_proprietario = new BigInteger(splittedPath[1]);
			HashMap <BigInteger, Immobile> immobili = qs.mostraImmobili(splittedPath[1], tipo, sup_min, sup_max, vani_min, vani_max, anno_min, anno_max, prezzo_min, prezzo_max, tag);
			Proprietario proprietario = qs.cercaProprietario(id_proprietario);
			if (proprietario == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			proprietario.setNumero_possedimenti(qs.ContaPossedimenti(proprietario.getId()));
			proprietario.setTotale_superficie(qs.calcolaTotaleSuperficie(proprietario.getId()));
			HashMap<BigInteger, ArrayList<Tag>> tags_immobili = qs.mostraTagImmobile();
			for(BigInteger Key : tags_immobili.keySet()) {
				Immobile poss = immobili.get(Key);
				if(poss != null) {
					poss.setTags(tags_immobili.get(Key));
				}
			}
			request.setAttribute("tags_lista", qs.mostraTag());
			request.setAttribute("proprietario", proprietario);
			request.setAttribute("immobili", immobili);
			request.setAttribute("tipi", Immobile.Tipo.values());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/possedimenti.jsp");
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
