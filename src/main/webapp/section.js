function showAndHideSection(section, show){
    document.getElementById(section + 'Show').style.display = show ? 'none' : 'block';
    document.getElementById(section + 'Hide').style.display = show ? 'block' : 'none';
    document.getElementById(section).style.display = show ? 'block' : 'none';
}
