async function confirmEmail(email) {
    const response = await fetch("http://localhost:6969/cliente/enviar-validacao-email", {
      method: "POST",
      body: JSON.stringify({ email }),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}

async function confirmCodeEmail(email, codigo) {
    const response = await fetch("http://localhost:6969/cliente/verificar-validacao-email", {
      method: "POST",
      body: JSON.stringify({ email, codigo}),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}

const btnHiddenEmail = document.querySelector('.button__register-user');
const codeEmail = document.querySelector('.div-box__confirm-email');
const wrongEmail = document.querySelector(".register-user__wrong-email");
const emailInput = document.querySelector('.form-box__register-user--emailinput');
const messageElement = document.querySelector(".dashboard__message");
const codigoInput = document.querySelector('.form-box__register-user--confirmcode');

//displayMessage(json);
let emailconfirmado = false;
btnHiddenEmail.addEventListener("click", async (event) => {
  event.preventDefault();
  const email = emailInput.value.trim();
  const codigo = codigoInput.value.trim();

  if(!emailconfirmado && email != ""){

    codeEmail.classList.remove("hidden");
    emailInput.disabled = true;
    wrongEmail.classList.remove("hidden");

    const response = await confirmEmail(email);
    console.log(response);
    emailconfirmado = true;
}else{
    const responseCode = await confirmCodeEmail(email, codigo)
    console.log(responseCode)
    if(responseCode.statusCode === 200){
        //guardar variavel para conseguir pegar em outra pagina
        sessionStorage.setItem("emailConfirmado", email);
        //manda pra outra pagina
        //replace nao deixa voltar pra pagina anterior (no caso essa) seria util? deixa ele voltar?
        window.location.replace('/public/html/pages/cadastro/cadastro_emailconfirmado.html')
        // emailInput.disabled = true;
        console.log(responseCode);
    }
}
});


wrongEmail.addEventListener("click", async(event)=>{
    event.preventDefault();
    emailInput.disabled = false;
    codeEmail.classList.add("hidden");
    wrongEmail.classList.add("hidden");
    emailconfirmado = false;
})


//Cadastro do cliente 
