var tabella = document.getElementById('tabella');

for(var i=0; i<biblioteca.length; i++)
{
    var riga = tabella.insertRow();

    var codiceLibroCella = riga.insertCell();
    codiceLibroCella.innerHTML = biblioteca[i].codiceLibro;

    var autoreCella = riga.insertCell();
    autoreCella.innerHTML = biblioteca[i].autore;    

    var titoloCella = riga.insertCell();
    titoloCella.innerHTML = biblioteca[i].titolo;

    var editoreCella = riga.insertCell();
    editoreCella.innerHTML = biblioteca[i].editore;

    var annoCella = riga.insertCell();
    annoCella.innerHTML = biblioteca[i].anno;

    var prezzoCella = riga.insertCell();
    prezzoCella.innerHTML = biblioteca[i].prezzo;

    var genereCella = riga.insertCell();
    genereCella.innerHTML = biblioteca[i].genere;

}