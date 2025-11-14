async function confirmPwdEmail(email) {
    const response = await fetch("http://localhost:6969/cliente/enviar-email-esqueci-senha", {
      method: "POST",
      body: JSON.stringify({ email }),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}

async function confirmPwdCode(email, codigo) {
    const response = await fetch("http://localhost:6969/cliente/validar-token-troca-senha", {
      method: "POST",
      body: JSON.stringify({ email, codigo}),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}

async function newPwd(token,email, senha) {
    const response = await fetch("http://localhost:6969/cliente/alterar-senha", {
      method: "POST",
      body: JSON.stringify({senha}),
      headers: {
        "Content-Type": "application/json",
        "token": token
      },
    });

    return await response.json(); 
}

const btnSubmitForm = document.querySelector('.form__register-user');
const codeEmail = document.querySelector('.div-box__confirm-email');
const wrongEmail = document.querySelector(".register-user__wrong-email");
const emailInput = document.querySelector('.form-box__register-user--emailinput');
const messageElement = document.querySelector(".dashboard__message");
const codigoInput = document.querySelector('.form-box__register-user--confirmcode');

const pwdField = document.querySelector('.div-box__pwd-email')
const pwdInput = document.querySelector('.form-box__register-user--newPwd')


let emailconfirmado = false;
let token = "";
let email = ""
btnSubmitForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  email = emailInput.value.trim();
  const codigo = codigoInput.value.trim();
  const senha = pwdInput.value.trim()
  
  if(!emailconfirmado && email != ""){
    codeEmail.classList.remove("hidden");
    emailInput.disabled = true;
    wrongEmail.classList.remove("hidden");

    const response = await confirmPwdEmail(email);
    if(response.statusCode === 200){
    
    emailconfirmado = true;
    }
    return;
  }

    if(pwdField.classList.contains("hidden")){
    const responseCode = await confirmPwdCode(email, codigo)
    // console.log(responseCode)

    if(responseCode.statusCode === 200){
        token = responseCode.body.token;
        // console.log(token)
        document.cookie = `tokenUser=${token}; path=/;`;
        pwdField.classList.remove("hidden");
    } return;
    }
    if(!pwdField.classList.contains("hidden")){

    // console.log(token)
    // console.log(email)    
    // console.log(senha)
    
    const responsePwd = await newPwd(token, email, senha)
    
    // console.log(responsePwd)
    if(responsePwd.statusCode === 200){
        window.location.replace('/public/html/pages/acervo.html')
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
