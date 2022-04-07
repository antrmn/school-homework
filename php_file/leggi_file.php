<?php
$percorso_caricamenti = $_SERVER["DOCUMENT_ROOT"] . DIRECTORY_SEPARATOR . "file_caricati";
session_start();

if(isset($_GET['leggi'])) //Validazione input (Controlla se il bottone 'submit' è stato premuto)
{
    if(empty($_GET['nome_file']) || 
    empty($_GET['modalita']) ||
    empty($_GET['numero_byte']) ) { //Validazione input (Controlla se tutti i campi richiesti sono stati compilati)
        $_SESSION['messaggio_lettura'] = "Compilare tutti i campi";
        header("location: index.php");
        exit();
    }

    if(!preg_match("/^[rwa]\+?$/", $_GET['modalita'])){    //Validazione input (Controlla se è stato inserita correttamente la modalità di apertura mediante regex)
        $_SESSION['messaggio_lettura'] = "Specificare modalità di apertura corretta";
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
        $_SESSION['messaggio_lettura'] = "Errore durante l'apertura del file";
            header("location: index.php");
            exit();
    }

    //Legge su file. La funzione restituisce i byte letti sotto forma di stringa (o 'false' in caso di scrittura fallita)
    $file_letto = fread($file, $_GET['numero_byte']);
    if($file_letto == false){
            $_SESSION['messaggio_lettura'] = "Si è verificato un errore durante la lettura del file";
            header("location: index.php");
            exit();
    }
} else {
    header("location: index.php");
        exit();
} ?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Test file PHP</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "stylesheet" type = "text/css" href = "style/header_style.css" />
        <link rel = "stylesheet" type = "text/css" href = "style/leggi_file.css" />
    </head>
    <body>
    <a href="index.php" id="header_link">
    <div class="header">
        <h1>Test File in PHP</h1>
    </div>
    </a>
        <!-- Se la seguente condizione è soddisfatta viene mostrato a schermo il blocco HTML --> 
        <!-- Viene usato la sintassi alternativa per l'istruzione echo ('=') per stampare i valori dell'array associativo $_FILES -->
        <?php if ($file_letto != false): ?>
            <div id="file_letto">
                <h2>Contenuto file</h2>
                <h3>Percorso: <span class="attribute"><?=$percorso_caricamenti . DIRECTORY_SEPARATOR . $_GET['nome_file']?> </span></h3>
                <h3>Byte da leggere: <span class="attribute"><?=$_GET['numero_byte']?></span></h3> <br>
                <div id="contenuto"><?=$file_letto?></div>
            </div>
        <?php endif; ?>
    </body>
</html>