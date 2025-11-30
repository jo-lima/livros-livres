//pegar o email que veio da outra pagina
const emailConfirm = sessionStorage.getItem("emailConfirmado");
console.log(emailConfirm)


async function createCliente(cpf, nome, email, senha, endereco,telefone) {
    const response = await fetch("http://localhost:6969/cliente/novo", {
      method: "POST",
      body: JSON.stringify({cpf, nome, email, senha, endereco, telefone}),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}

//cosntantes dos inputs
const btnRegisterSubmit = document.querySelector('.form__register-user');

const cpfInput = document.querySelector('.form-box__register-user--cpf')
const nameInput = document.querySelector('.form-box__register-user--name')
const addressInput = document.querySelector('.form-box__register-user--address')
const telInput = document.querySelector('.form-box__register-user--tel')
const passwdInput = document.querySelector('.form-box__register-user--passwd')

//desativar a caixa de email e aparecer o email (que veio da outra pagina)
const emailInputDisabled = document.querySelector('.form-box__register-user--email')
emailInputDisabled.value = emailConfirm;
emailInputDisabled.disabled = true;


btnRegisterSubmit.addEventListener("submit", async(event)=>{
  event.preventDefault();
  console.log('vai mano')
    const cpf = cpfInput.value.trim();
    const clientname = nameInput.value.trim();
    const address = addressInput.value.trim();
    const tel = telInput.value.trim();
    const passwd = passwdInput.value.trim();
    const responseCode = await createCliente(cpf, clientname, emailConfirm ,passwd, address, tel)
    console.log(responseCode)
    if(responseCode.statusCode === 200){
      window.location.replace('/public/html/pages/login.html')
        base.displayMessage(responseCode)
  }else{
    base.displayMessage(responseCode)
  }
})

//\D qualquer coisa que não seja numero substitui por nada (ou seja, não permite numero dogs regexxx)
cpfInput.addEventListener('input', () => {
  cpfInput.value = cpfInput.value.replace(/\D/g, ""); 
});

telInput.addEventListener('input', () => {
  telInput.value = telInput.value.replace(/\D/g, ""); 
});

// regex proibe numero e caracter especial
nameInput.addEventListener('input', () => {
  nameInput.value = nameInput.value.replace(/[^A-Za-zÀ-ÿ\s]/g, ""); 
});