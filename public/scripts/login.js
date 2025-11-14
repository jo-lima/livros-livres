// import Base from "./dashboard-base.js";

// const base = new Base();

const btnLogin = document.querySelector('.user-form');
const input_email = document.querySelector('.login_email');
const input_senha = document.querySelector('.login_senha');


async function loginUser(usuario,senha) {
    const response = await fetch("http://localhost:6969/cliente/login", {
      method: "POST",
      body: JSON.stringify({ usuario, senha }),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json(); 
}


btnLogin.addEventListener("submit", async (event) =>{
    event.preventDefault();

    const email = input_email.value.trim();
    const senha = input_senha.value.trim();
    
    const responseCode = await loginUser(email, senha);
    
    console.log(responseCode)
    if(responseCode.statusCode === 200){
        // base.displayMessage(responseCode)
        
        const token = responseCode.body.token;
        document.cookie = `tokenUser=${token}; path=/;`;
        window.location.href = ('/public/html/pages/acervo.html')
    }else{
        base.displayMessage(responseCode)
    }
})

