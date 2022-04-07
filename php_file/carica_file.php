<?php
session_start();

if(!isset($_POST["submit"])){
    $_SESSION['errore_upload'] = "Inserire un file da caricare.";
    header("location: index.php");
    exit();
}

 //Legge il valore nell'array associativo alla chiave 'error' e controlla se il file è stato caricato con successo
if($_FILES["file_caricato"]["error"] != UPLOAD_ERR_OK){
    $_SESSION['errore_upload'] = "Errore nel caricamento del file da parte del server. [";

    switch($_FILES["file_caricato"]["error"]){
        case UPLOAD_ERR_INI_SIZE: {
            $_SESSION['errore_upload'] .= "Value: 1; The uploaded file exceeds the upload_max_filesize directive in php.ini. ]";
            break;
        }
        case UPLOAD_ERR_FORM_SIZE: {
            $_SESSION['errore_upload'] .= "Value: 2; The uploaded file exceeds the MAX_FILE_SIZE directive that was specified in the HTML form. ]";
            break;
        }
        case UPLOAD_ERR_PARTIAL: {
            $_SESSION['errore_upload'] .= "Value: 3; The uploaded file was only partially uploaded. ]";
            break;
        }
        case UPLOAD_ERR_NO_FILE: {
            $_SESSION['errore_upload'] .= "Value: 4; No file was uploaded. ]";
            break;
        }
        case UPLOAD_ERR_NO_TMP_DIR: {
            $_SESSION['errore_upload'] .= "Value: 6; Missing a temporary folder. Introduced in PHP 5.0.3. ]";
            break;
        }
        case UPLOAD_ERR_CANT_WRITE: {
            $_SESSION['errore_upload'] .= "Value: 7; Failed to write file to disk. Introduced in PHP 5.1.0. ]";
            break;
        }
        case UPLOAD_ERR_EXTENSION: {
            $_SESSION['errore_upload'] .= "Value: 8; A PHP extension stopped the file upload. PHP does not provide a way to ascertain which extension caused the file upload to stop; examining the list of loaded extensions with phpinfo() may help. Introduced in PHP 5.2.0. ]";
            break;
        }
        default: {
            $_SESSION['errore_upload'] .= "Errore non contemplato. ]";
            break;
        }
    }
    header("location: index.php");
    exit();
}

$percorso_caricamenti = $_SERVER["DOCUMENT_ROOT"] . DIRECTORY_SEPARATOR . "file_caricati"; //Stringa contenente il percorso alla cartella per i file caricati
$percorso_file = $percorso_caricamenti . DIRECTORY_SEPARATOR . $_FILES["file_caricato"]["name"]; //Stringa contenente il percorso al file caricato
$spostamento_avvenuto = move_uploaded_file($_FILES["file_caricato"]["tmp_name"], $percorso_file); //Sposta il file dal percorso temporaneo alla directory su definita
?>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Test file PHP</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel = "stylesheet" type = "text/css" href = "style/header_style.css" />
        <link rel = "stylesheet" type = "text/css" href = "style/carica_file.css" />
    </head>
    <body>
        <a href="index.php" id="header_link">
        <div class="header">
            <h1>Test File in PHP</h1>
        </div>
        </a>

        <div class="container">
            <h2>File caricato con successo</h2>
            <ul id="attributi_file">
                <!-- Sono stampati a schermo gli attributi del file dall'array associativo $_FILES -->
                <li> Nome: <span class="attribute"><?=$_FILES['file_caricato']['name']?></span> </li>
                <li> Tipo: <span class="attribute"><?=$_FILES['file_caricato']['type']?></span> </li>
                <li> Dimensioni: <span class="attribute"><?=$_FILES['file_caricato']['size'] ?> byte </span></li>
                <li> Percorso temporaneo: <span class="attribute"><?=$_FILES['file_caricato']['tmp_name']?></span> </li>
            </ul>

            <!-- Stampa a schermo un messaggio che segnala l'esito dell'operazione di spostamento del file caricato -->
            <?php if ($spostamento_avvenuto): ?>
                <div>
                    Il file è stato salvato dal server al percorso <span class="attribute"><?=$percorso_file?></span>.
                </div>
                <div id="contenuto_file_frame">
                    <h3>Contenuto file</h3>
                    <div id="contenuto">
                        <?=file_get_contents($percorso_file);?>
                    </div>
                </div>
            <?php else: ?>
                <div>
                    Errore durante lo spostamento del file al percorso <?=$percorso_file?> del server.
                </div>
            <?php endif; ?>
        </div>

    </body>
</html>