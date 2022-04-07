
import java.io.Serializable;
import java.math.BigInteger;

public class Proprietario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String nome;
	private String cognome;
	private int totale_superficie;
	private int numero_possedimenti;
	
	public Proprietario() { }

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getNumero_possedimenti() {
		return numero_possedimenti;
	}

	public void setNumero_possedimenti(int numero_possedimenti) {
		this.numero_possedimenti = numero_possedimenti;
	}

	public int getTotale_superficie() {
		return totale_superficie;
	}

	public void setTotale_superficie(int totale_superficie) {
		this.totale_superficie = totale_superficie;
	}
}
