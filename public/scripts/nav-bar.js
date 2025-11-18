// hide all
document.querySelectorAll(".main-nav__link-user").forEach(elmt => {
    elmt.style.display = "none";
});
document.querySelectorAll(".main-nav__link-admin").forEach(elmt => {
    elmt.style.display = "none";
});

// if user is logged in
if(document.cookie.split('userToken=')[1]?.split(';')[0] != null) {
    // hides entrar/cadastro
    document.querySelectorAll(".main-nav__link-default").forEach(elmt => {
        elmt.style.display = "none";
    });

    // and user is a client.
    if(
        document.cookie.split('userId=')[1]?.split(';')[0] != null
    ) {
        document.querySelectorAll(".main-nav__link-user").forEach(elmt => {
            elmt.style.display = "unset";
        });
    } else { // else is a admin
        document.querySelectorAll(".main-nav__link-admin").forEach(elmt => {
            elmt.style.display = "unset";
        });
    }
}
