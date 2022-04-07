
import java.net.*;
import java.io.*;
 
public class CalcolatriceServer {
    public static void main(String[] args) throws IOException {
        int portNumber;
        if(args.length >= 1)                                //
        {                                                   //
            portNumber = Integer.parseInt(args[0]);         //  Parametri da console per la porta, come nel client
        } else {                                            //
            portNumber = 5244;                              //  Porta di default
        }                                                   //

        System.out.println("In attesa della connessione del client sulla porta " + portNumber + ".");
        try (                                                           //
            ServerSocket serverSocket =                                 //
                new ServerSocket(portNumber);                           //
            Socket clientSocket = serverSocket.accept();                //
            PrintWriter out =                                           //  Istanza socket e classi per input/output in try()
                new PrintWriter(clientSocket.getOutputStream(), true);  //
        		                                                        //
            BufferedReader in = new BufferedReader(                     //
                new InputStreamReader(clientSocket.getInputStream()));  //
        		
        ) {
            System.out.println("Connessione al client effettuata. \n");
            String inputLine, outputLine;
            CalcolatriceProtocol protocol = new CalcolatriceProtocol();        //Istanza classe CalcolatriceProtocol, dove l'input viene elaborato viene generato il risultato
            out.println("Inserisci operazione da svolgere. (Sintassi: operando1 +|-|*|/ operando2. \"Esci\" per terminare.)");
            while ((inputLine = in.readLine()) != null) {   //  Legge stringhe in entrata inviate dal client. Resta nel ciclo fin quando la stringa in entrata non è null (connessione terminata)
            	outputLine = protocol.elabora(inputLine);   //  La stringa viene passata al metodo elabora, che restituisce una stringa col risultato dell'operazione (o un messaggio di errore o il messaggio di chiusura)
                out.println(outputLine);                    //  Invia la stringa outputLine al client
                if (outputLine.equals("Chiusura..."))       //
                {
                    System.out.println("Server terminato.");
                    break;
                }
            }
            
        } catch (IOException e) {                                                   //Eccezioni
            System.err.println("Errore durante tentativo di ascolto della porta "
                + portNumber + " o tentativo di connessione");
            System.err.println(e.getMessage());
        }
    }
}

