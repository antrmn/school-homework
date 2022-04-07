import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

class Main {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Inserisci nome");
        String nome = br.readLine();
        System.out.println("Inserisci cognome");
        String cognome = br.readLine();
        System.out.println("Inserisci data di nascita yyyy-mm-dd");
        String dataDiNascita = br.readLine();
        System.out.println("Maschio o femmina? [m/f]");
        boolean maschio = br.readLine().equals("m");

        System.out.print(CodiceFiscale.genera(cognome, nome, dataDiNascita, maschio, ""));


    }
}
