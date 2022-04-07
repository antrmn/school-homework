
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
public class QueryService {
	public HashMap<BigInteger,Immobile> mostraImmobili() {
		final String findQuery = 
				"SELECT * FROM immobili";
		HashMap<BigInteger,Immobile> entities = new HashMap<BigInteger,Immobile>();
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);
				ResultSet rs = findStatement.executeQuery())
			{	
				if(rs.next() == false) {
					throw new SQLException("Nessuna corrispondenza trovata");
				} else {
					do {
						Immobile entity = new Immobile();
						entity.setId(new BigInteger(rs.getString("id")));
						entity.setTipo(Immobile.Tipo.valueOf(rs.getString("tipo")));
						entity.setSuperficie_mq(rs.getDouble("superficie_mq"));
						entity.setNumero_vani(rs.getInt("numero_vani"));
						entity.setAnno_fabbricazione(rs.getInt("anno_fabbricazione"));
						entity.setPrezzo(rs.getBigDecimal("prezzo"));
						entity.setProprietario(new BigInteger(rs.getString("proprietario")));
						entities.put(entity.getId(), entity);
					} while(rs.next());
				}
			} catch (SQLException e) {
				System.err.println("Operazione SQL fallita");
			}
		return entities;
	}
	
	public HashMap<BigInteger, Tag> mostraTag(){
		String query = "SELECT * FROM tag";
		HashMap<BigInteger, Tag> tags = new HashMap<BigInteger, Tag>();
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(query);
				ResultSet rs = findStatement.executeQuery())
			{	
				if(rs.next() == false) {
					throw new SQLException("Nessuna corrispondenza trovata");
				} else {
					do {
						Tag tag = new Tag();
						tag.setId(new BigInteger(rs.getString("id")));
						tag.setTitolo(rs.getString("titolo"));
						tags.put(tag.getId(), tag);
					} while(rs.next());
				}
			} catch (SQLException e) {
				System.err.println("Operazione SQL fallita");
			}
		return tags;
		
	}
	
	public HashMap<BigInteger, ArrayList<Tag>> mostraTagImmobile(){
		String query = "SELECT immobili.id, tag.id, titolo "
				+ "FROM immobili "
				+ "INNER JOIN immobili_xref_tag AS x "
				+ "ON x.id_immobile = immobili.id "
				+ "INNER JOIN tag ON x.id_tag = tag.id";
		
		HashMap<BigInteger, ArrayList<Tag>> tags_map = new HashMap<BigInteger, ArrayList<Tag>>(); 
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(query);
				ResultSet rs = findStatement.executeQuery())
			{	
				if(rs.next() == false) {
					throw new SQLException("Nessuna corrispondenza trovata");
				} else {
					do {
						Tag tag = new Tag();
						tag.setId(new BigInteger(rs.getString("tag.id")));
						tag.setTitolo(rs.getString("titolo"));
						ArrayList<Tag> list = tags_map.get(new BigInteger(rs.getString("immobili.id")));
						if(list == null)
							{
								list = new ArrayList<Tag>();
								tags_map.put(new BigInteger(rs.getString("immobili.id")), list);
							}
						list.add(tag);
					} while(rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Operazione SQL fallita");
			}
		return tags_map;
	}
	
	public HashMap<BigInteger,Immobile> mostraImmobili(String proprietario_id, String tipo, String superficie_min, String superficie_max,
			String vani_min, String vani_max, String anno_min, String anno_max,
			String prezzo_min, String prezzo_max, String tag) throws NumberFormatException{
		StringBuilder query = new StringBuilder();
		
		ArrayList<String> parameters = new ArrayList<String>();
		String select = "SELECT * FROM immobili ";
		String join = "INNER JOIN immobili_xref_tag AS x ON x.id_immobile = immobili.id "
				+ "INNER JOIN tag ON x.id_tag = tag.id ";
		String where = "WHERE 1=1 "; //cosi c'è sempre almeno una condizione nella query
		String prop_id_q = "AND proprietario = ? ";
		String tipo_q = "AND tipo = ? ";
		String sup_min_q = "AND superficie_mq >= ? ";
		String sup_max_q = "AND superficie_mq <= ? ";
		String vani_min_q = "AND numero_vani >= ? ";
		String vani_max_q = "AND numero_vani <= ? ";
		String anno_min_q = "AND anno_fabbricazione >= ? ";
		String anno_max_q = "AND anno_fabbricazione <= ? ";
		String prezzo_min_q = "AND prezzo >= ? ";
		String prezzo_max_q = "AND prezzo <= ? ";
		String tag_q = "AND tag.id = ? ";
		
		query.append(select);
		if(tag != null) {
			query.append(join);
			query.append(where);
			query.append(tag_q);
			parameters.add(tag);
		} else {
			query.append(where);
		}
		
		if(proprietario_id != null) {
			query.append(prop_id_q);
			parameters.add(proprietario_id);
		}
		
		if(tipo != null) {
			query.append(tipo_q);
			parameters.add(tipo);
		}
		
		if(superficie_min != null) {
			query.append(sup_min_q);
			parameters.add(superficie_min);
		}
		
		if(superficie_max != null) {
			query.append(sup_max_q);
			parameters.add(superficie_max);
		}
		
		if(vani_min != null) {
			query.append(vani_min_q);
			parameters.add(vani_min);
		}
		
		if(vani_max != null) {
			query.append(vani_max_q);
			parameters.add(vani_max);
		}
		
		if(anno_min != null) {
			query.append(anno_min_q);
			parameters.add(anno_min);
		}
		
		if(anno_max != null) {
			query.append(anno_max_q);
			parameters.add(anno_max);
		}
		
		if(prezzo_min != null) {
			query.append(prezzo_min_q);
			parameters.add(prezzo_min);
		}
		
		if(prezzo_max != null) {
			query.append(prezzo_max_q);
			parameters.add(prezzo_max);
		}
		System.out.println(query.toString());
		HashMap<BigInteger,Immobile> entities = new HashMap<BigInteger,Immobile>();
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(query.toString());)
			{	
			
				for(int i=0; i < parameters.size(); i++) {
					findStatement.setString(i+1, parameters.get(i));
				}
				
				try (ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						do {
							Immobile entity = new Immobile();
							entity.setId(new BigInteger(rs.getString("immobili.id")));
							entity.setTipo(Immobile.Tipo.valueOf(rs.getString("tipo")));
							entity.setSuperficie_mq(rs.getDouble("superficie_mq"));
							entity.setNumero_vani(rs.getInt("numero_vani"));
							entity.setAnno_fabbricazione(rs.getInt("anno_fabbricazione"));
							entity.setPrezzo(rs.getBigDecimal("prezzo"));
							entity.setProprietario(new BigInteger(rs.getString("proprietario")));
							entities.put(entity.getId(), entity);
						} while(rs.next());
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Operazione SQL fallita");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		return entities;
	}
	
	public HashMap<BigInteger,Immobile> mostraPossedimenti(BigInteger id_proprietario) {
		String select = "SELECT * FROM immobili"
				+ "	WHERE proprietario = ?";
		
		HashMap<BigInteger,Immobile> entities = new HashMap<BigInteger,Immobile>();
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(select);)
			{	
				findStatement.setString(1, id_proprietario.toString());
				try(ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						do {
							Immobile entity = new Immobile();
							entity.setId(new BigInteger(rs.getString("id")));
							entity.setTipo(Immobile.Tipo.valueOf(rs.getString("tipo")));
							entity.setSuperficie_mq(rs.getDouble("superficie_mq"));
							entity.setNumero_vani(rs.getInt("numero_vani"));
							entity.setAnno_fabbricazione(rs.getInt("anno_fabbricazione"));
							entity.setPrezzo(rs.getBigDecimal("prezzo"));
							entity.setProprietario(id_proprietario);
							entities.put(entity.getId(), entity);
						} while(rs.next());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Operazione SQL fallita");
				}
		return entities;
	}
	
	
	public HashMap<BigInteger,Proprietario> mostraProprietari(){
		final String findQuery = "SELECT proprietari.*, COUNT(*) AS n_possedimenti, SUM(superficie_mq) AS totale_superficie "
								+	"FROM immobili "
								+	"INNER JOIN proprietari "
								+ 	"ON immobili.proprietario = proprietari.id "
								+	"GROUP BY immobili.proprietario";
		HashMap<BigInteger,Proprietario> entities = new HashMap<BigInteger,Proprietario>();
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);
				ResultSet rs = findStatement.executeQuery())
			{	
				
				if(rs.next() == false) {
					throw new SQLException("Nessuna corrispondenza trovata");
				} else {
					do {
						Proprietario entity = new Proprietario();
						entity.setId(new BigInteger(rs.getString("id")));
						entity.setNome(rs.getString("nome"));
						entity.setCognome(rs.getString("cognome"));
						entity.setNumero_possedimenti(rs.getInt("n_possedimenti"));
						entity.setTotale_superficie(rs.getInt("totale_superficie"));
						entities.put(entity.getId(), entity);
					} while(rs.next());
				}
			} catch (SQLException e) {
				System.err.println("Operazione SQL fallita");
				e.printStackTrace();
			}
		return entities;
	}
	
	public Proprietario cercaProprietario(BigInteger id) {
		final String findQuery = "SELECT * FROM proprietari WHERE id=?;";
		Proprietario entity = null;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);)
			{	
				
				findStatement.setString(1, id.toString());
				
				try (ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						entity = new Proprietario();
						entity.setId(new BigInteger(rs.getString("id")));
						entity.setNome(rs.getString("nome"));
						entity.setCognome(rs.getString("cognome"));
					}
				}
			} catch (SQLException e) {
				System.err.println("Operazione SQL fallita");
			}
		
			return entity;
	}
	
	public Immobile cercaImmobile(BigInteger id) {
		final String findQuery = "SELECT * FROM immobili WHERE id=?;";
		Immobile entity = null;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);)
			{	
				
				findStatement.setString(1, id.toString());
				
				try (ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						entity = new Immobile();
						entity.setId(new BigInteger(rs.getString("id")));
						entity.setTipo(Immobile.Tipo.valueOf(rs.getString("tipo")));
						entity.setSuperficie_mq(rs.getDouble("superficie_mq"));
						entity.setNumero_vani(rs.getInt("numero_vani"));
						entity.setAnno_fabbricazione(rs.getInt("anno_fabbricazione"));
						entity.setPrezzo(rs.getBigDecimal("prezzo"));
						entity.setProprietario(new BigInteger(rs.getString("id")));
					}
				}
			} catch (SQLException e) {
				System.err.println("Operazione SQL fallita");
			}
		
			return entity;
	}
	
	public HashMap<BigInteger,Proprietario> filtraProprietari(String tipo, String tag) {
		StringBuilder query = new StringBuilder();
		
		ArrayList<String> parameters = new ArrayList<String>();
		String select = "SELECT proprietari.id, nome, cognome FROM immobili ";
		String join_tag = " "
				+ "INNER JOIN immobili_xref_tag AS x ON x.id_immobile = immobili.id "
				+ "INNER JOIN tag ON x.id_tag = tag.id ";
		String join = "INNER JOIN proprietari ON proprietari.id = proprietario ";
		String where = "WHERE 1=1 "; //cosi c'è sempre almeno una condizione nella query
		String tipo_q = "AND tipo = ? ";
		String tag_q = "AND tag.id = ? ";
		
		query.append(select);
		if(tag != null) {
			query.append(join_tag);
			query.append(join);
			query.append(where);
			query.append(tag_q);
			parameters.add(tag);
		} else {
			query.append(join);
			query.append(where);
		}
		
		if(tipo != null) {
			query.append(tipo_q);
			parameters.add(tipo);
		}
		System.out.println(query.toString());
		HashMap<BigInteger,Proprietario> entities = new HashMap<BigInteger,Proprietario>();
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(query.toString());)
			{	
			
				for(int i=0; i < parameters.size(); i++) {
					findStatement.setString(i+1, parameters.get(i));
				}
				
				try (ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						do {
							Proprietario entity = new Proprietario();
							entity.setId(new BigInteger(rs.getString("id")));
							entity.setNome(rs.getString("nome"));
							entity.setCognome(rs.getString("cognome"));
							entities.put(entity.getId(), entity);
						} while(rs.next());
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Operazione SQL fallita");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		return entities;
	}

	public int ContaPossedimenti(BigInteger id) {
		final String findQuery = 
				"SELECT COUNT(*) AS totale FROM immobili WHERE proprietario = ?";
		int totale = 0;
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);)
			{	
				findStatement.setString(1, id.toString());
				try(ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
					totale = rs.getInt("totale"); 
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Operazione SQL fallita");
			}
		return totale;
	}
	
	public int calcolaTotaleSuperficie(BigInteger id)
	{
		final String findQuery = 
				"SELECT SUM(superficie_mq) AS superficie_totale FROM immobili WHERE proprietario = ?";
		int totale = 0;
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement findStatement = conn.prepareStatement(findQuery);)
			{	
				findStatement.setString(1, id.toString());
				try(ResultSet rs = findStatement.executeQuery()){
					if(rs.next() == false) {
						throw new SQLException("Nessuna corrispondenza trovata");
					} else {
						totale = rs.getInt("superficie_totale"); 
					}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
				System.err.println("Operazione SQL fallita");
			}
		return totale;
	}
}
