<?php
$percorso_caricamenti = $_SERVER["DOCUMENT_ROOT"] . DIRECTORY_SEPARATOR . "file_caricati";
session_start();

//Validazione input
if(empty($_GET['nome_file'])){
        header("location: index.php");
        exit();
    }

$percorso_file = $percorso_caricamenti . DIRECTORY_SEPARATOR . $_GET['nome_file'];

//Controlla se il file specificato esiste
if(!file_exists($percorso_file)){
    $_SESSION['errore_rimozione'] = "File inesistente.";
        header("location: index.php");
        exit();
}

//Rimuove il file e controlla se da esito negativo (restituisce false) o positivo (true)
if(!unlink($percorso_file)){
    $_SESSION['errore_rimozione'] = "Errore durante la rimozione del file.";
}

header("location: index.php");
exit();