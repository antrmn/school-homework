import java.util.regex.*;
import java.math.*;
public class CalcolatriceProtocol {
	Pattern p = Pattern.compile("^(?<intero1>[-+]?\\d+)[\\.,]?(?<decimale1>\\d*)(?<operatore>[-+*/])(?<intero2>[-+]?\\d+)[\\.,]?(?<decimale2>\\d*)$");
	//							^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	//							Espressione regolare. Serve a verificare che in input sia inserita un'operazione valida (come ad esempio 2+2).
	//							Questa viene compilata in un'istanza della classe Pattern
	public String elabora(String input)
	{
		if (input.equalsIgnoreCase("esci")){		//	Se input è uguale a "esci"
			return "Chiusura...";					//	Allora restituisci questa stringa
		}											//	ed esci dalla funzione

		String output;
		input = input.replaceAll("\\s","");  //rimuove tutti gli spazi dalla stringa di input
		Matcher m = p.matcher(input);	//Oggetto creato per confrontare la stringa al Pattern
		if(m.find()) {   //Controlla se l'oggetto Matcher trova una corrispondenza con il pattern (tramite il metodo booleano find())
			if (m.group("decimale1").isEmpty() && m.group("decimale2").isEmpty() && (m.group("operatore").charAt(0) != '/')) {	//controlla se entrambi i numeri sono interi e se l'operazione richiesta non è la divisione
					BigInteger operando1 = new BigInteger(m.group("intero1"));  //In caso affermativo vengono istanziati oggetti BigInteger
					BigInteger operando2 = new BigInteger(m.group("intero2"));	//
					BigInteger risultato = null;								//
					switch (m.group("operatore").charAt(0)) {			//
						case '+': {										//
							risultato = operando1.add(operando2);		//In base all'operatore inserito viene svolta l'operazione apposita
							break;										//(se inserito + va fatta la somma, se - la sottrazione ecc...)
						}												//
						case '-': {										//
							risultato = operando1.subtract(operando2);	//
							break;
						}
						case '*': {
							risultato = operando1.multiply(operando2);
							break;
						}
					}
					output = operando1.toString() + ' ' + m.group("operatore") + ' ' + operando2.toString() + " = " + risultato.toString();
					return output;
				}

			BigDecimal operando1 = new BigDecimal(m.group("intero1") + '.' + m.group("decimale1"));	//Se almeno uno dei due operandi ha la parte decimale
			BigDecimal operando2 = new BigDecimal(m.group("intero2") + '.' + m.group("decimale2")); //allora istanzia BigDecimal
			BigDecimal risultato = null;
			switch (m.group("operatore").charAt(0)) {
				case '+': {
					risultato = operando1.add(operando2);
					break;
				}
				case '-': {
					risultato = operando1.subtract(operando2);
					break;
				}
				case '*': {
					risultato = operando1.multiply(operando2);
					break;
				}
				case '/': {
					if(operando2.compareTo(BigDecimal.ZERO) == 0)
					{
						output = "Errore: impossibile dividere per zero.";
						return output;
					} else {
						risultato = operando1.divide(operando2, 15, RoundingMode.HALF_UP);
					}
					break;
				}
			}
			output = operando1.toString() + ' ' + m.group("operatore") + ' ' + operando2.toString() + " = " + risultato.toString();
			return output;
		}	else output = "Errore: espressione non valida.";
		return output;
	}
}