<?php
$percorso_caricamenti = $_SERVER["DOCUMENT_ROOT"] . DIRECTORY_SEPARATOR . "file_caricati";
session_start();

//Validazione input
if (isset($_GET['scrivi'])) {
    if(empty($_GET['nome_file']) ||
    empty($_GET['modalita']) ||
    empty($_GET['testo']) ) {
        $_SESSION['errore_scrittura'] = "Compilare tutti i campi";
        header("location: index.php");
        exit();
    }

    if(!preg_match("/^[rwa]\+?$/", $_GET['modalita'])){
        $_SESSION['errore_scrittura'] = "Specificare modalità di apertura corretta";
            header("location: index.php");
            exit();
    }

    //Aggiungi 'b' flag alla modalità di apertura in caso di file binario
    $modalita = $_GET['modalita'];
    if($_GET['binario'] == 'binario'){
        $modalita .= 'b';
    }

    //Apertura file
    $file = fopen($percorso_caricamenti . DIRECTORY_SEPARATOR . $_GET['nome_file'], $modalita);

    //Controlla se l'apertura del file è avvenuta con successo
    if($file==false){
        $_SESSION['errore_scrittura'] = "Errore durante l'apertura del file";
            header("location: index.php");
            exit();
    }

    //Scrive sul file. La funzione restituisce il numero di byte scritti (o 'false' in caso di scrittura fallita)
    $byte_scritti= fwrite($file , $_GET['testo']);
    if ($byte_scritti == false){
        $_SESSION['errore_scrittura'] = "fwrite() ha restituito false, si è verificato un errore durante la scrittura o la modalità di apertura scelta non consente la scrittura.";
        header("location: index.php");
        exit();
    } else {
        $_SESSION['messaggio_scrittura'] = "Sono stati scritti " . $byte_scritti . " byte sul file " . $_GET['nome_file'];
        header("location: index.php");
        exit();
    }
} else { 
    header("location: index.php");
        exit();
}