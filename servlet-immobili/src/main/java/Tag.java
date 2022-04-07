
import java.io.Serializable;
import java.math.BigInteger;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String titolo;
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	};
}
