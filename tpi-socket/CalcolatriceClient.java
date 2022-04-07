import java.io.*;
import java.net.*;

public class CalcolatriceClient {

    public static void main(String[] args) throws IOException {
        String hostName;
        int portNumber;

        if (args.length == 1){                          //
            portNumber = Integer.parseInt(args[0]);     //
            hostName = "localhost";                     //
        } else if (args.length >= 2){                   //      Argomenti da passare via console.
            portNumber = Integer.parseInt(args[0]);     //      È possibile cambiare porta / nomehost all'avvio del programma in questo modo
            hostName = args[1];                         //
        } else{                                         //
            hostName = "localhost";                     //
            portNumber = 5244;                          //
        }

        System.out.println("Tentativo di connessione al server su " + hostName + " alla porta " + portNumber +".");
        try (
            Socket socket = new Socket(hostName, portNumber);               //
                                                                            //
            PrintWriter out =                                               //
                new PrintWriter(socket.getOutputStream(), true);            //      Istanzio socket e le varie classi per input/output
        		                                                            //      Da notare come tutto sia scritto dentro un try()
            BufferedReader in =                                             //      In questo modo le risorse vengono automaticamente
                new BufferedReader(                                         //      rilasciate quando il codice tra parentesi graffe {} termina
                    new InputStreamReader(socket.getInputStream()));        //
        		                                                            //      Il socket si chiude quindi automaticamente e non serve socket.close()
            BufferedReader stdIn =                                          //
                new BufferedReader(                                         //
                    new InputStreamReader(System.in));                      //
        ) {
            System.out.println("Connessione al server effettuata." );
            String userInput, rispostaServer;
            while ((rispostaServer = in.readLine()) != null) {                  //  Resta nel ciclo while fin quando la stringa che riceve dal server è uguale a null
                System.out.println("[" + hostName + "] " + rispostaServer);     //  Perchè quando la stringa della risposta del server è null significa che il server ha terminato la connessione.
                if(rispostaServer.contains("Chiusura..."))
                {
                    break;                                                  //Se il server scrive "Chiusura..." esci dal ciclo while (break).
                }
                do {
                    System.out.print("> ");                                 //
                    userInput = stdIn.readLine();                           //  Input da tastiera.
                } while((userInput.trim().isEmpty()));                      //  Se l'input è vuoto (o contiene solo spazi) allora non inviare al server e ripeti inserimento

                out.println(userInput);      //Invia input da tastiera al server
            }

        } catch (UnknownHostException e) {                          //
            System.err.println("Host sconosciuto");                 //
            System.exit(1);                                         //
        } catch (IOException e) {                                   //  Eccezioni
            System.err.println("Non è possibile connettersi a " +   //
                hostName);                                          //
            System.exit(1);                                         //
        } 
    }
}