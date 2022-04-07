
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;


public class Immobile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Tipo { Appartamento, Villa	}
	
	private BigInteger id;
	private Tipo tipo;
	private double superficie_mq;
	private int numero_vani;
	private int anno_fabbricazione;
	private BigDecimal prezzo;
	private BigInteger proprietario;
	private ArrayList<Tag> tags;
	
	public Immobile() { }
	
	public BigInteger getId() {
		return id;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public double getSuperficie_mq() {
		return superficie_mq;
	}
	public void setSuperficie_mq(double superficie_mq) {
		this.superficie_mq = superficie_mq;
	}
	public int getNumero_vani() {
		return numero_vani;
	}
	public void setNumero_vani(int numero_vani) {
		this.numero_vani = numero_vani;
	}
	public int getAnno_fabbricazione() {
		return anno_fabbricazione;
	}
	public void setAnno_fabbricazione(int anno_fabbricazione) {
		this.anno_fabbricazione = anno_fabbricazione;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	public BigInteger getProprietario() {
		return proprietario;
	}
	public void setProprietario(BigInteger proprietario) {
		this.proprietario = proprietario;
	}

	public void setId(BigInteger id) {
		this.id = id;
		
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	
}
