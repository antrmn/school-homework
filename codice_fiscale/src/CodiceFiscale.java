import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

class CodiceFiscale {


    public static String genera(String cognome, String nome, String dataDiNascita, boolean maschio, String luogoDiNascita) {
        StringBuilder codice = new StringBuilder(16);

        codice.append(calcolaCodiceCognome(cognome));
        codice.append(calcolaCodiceNome(nome));

        LocalDate data = LocalDate.parse(dataDiNascita);
        DateFormat formatoAnno = new SimpleDateFormat("yy");
        DateFormat formatoGiorno = new SimpleDateFormat("dd");

        codice.append(formatoAnno.format(data.getYear()));
        codice.append(ottieniIdentificativoMese(data.getMonth()));

        codice.append(maschio ? formatoGiorno.format(data.getDayOfYear()) : formatoGiorno.format(data.getDayOfYear()) );

        codice.append("xxxx");

        codice.append('x');

        return codice.toString();
    }

    private static boolean vocale(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    private static String calcolaCodiceCognome(String cognome){
        String cognomeNormalizzato = cognome.replaceAll("\\s", "");

        StringBuilder codice = new StringBuilder(3);
        boolean prendiVocali = false;

        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < cognome.length(); i++) {
                if (codice.length() == 3)
                    break;

                if (prendiVocali == vocale(cognome.charAt(i))) {
                    codice.append(cognome.charAt(i));
                }
            }
            prendiVocali = true;
        }

        while(codice.length() < 3){
            codice.append('X');
        }

        return codice.toString();

    }

    private static String calcolaCodiceNome(String nome){
        StringBuilder codice = new StringBuilder(3);
        boolean prendiVocali = false;

        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < nome.length(); i++) {
                if (prendiVocali) {
                    if (codice.length() == 3) {
                        break;
                    }
                    if (vocale(nome.charAt(i))) {
                        codice.append(nome.charAt(i));
                    }
                } else {
                    if (!vocale(nome.charAt(i))) {
                        if (codice.length() == 3) {
                            codice.deleteCharAt(1);
                            codice.append(nome.charAt(i));
                            break;
                        }
                        codice.append(nome.charAt(i));
                    }
                }
            }
            prendiVocali = true;
        }

        while(codice.length() < 3){
            codice.append('X');
        }

        return codice.toString();

    }

    private static char ottieniIdentificativoMese(Month mese){
        switch(mese){
            case JANUARY:
                return 'A';
            case FEBRUARY:
                return 'B';
            case MARCH:
                return 'C';
            case APRIL:
                return 'D';
            case MAY:
                return 'E';
            case JUNE:
                return 'H';
            case JULY:
                return 'L';
            case AUGUST:
                return 'M';
            case SEPTEMBER:
                return 'P';
            case OCTOBER:
                return 'R';
            case NOVEMBER:
                return 'S';
            case DECEMBER:
                return 'T';
        }
        return 0;
    }
}
