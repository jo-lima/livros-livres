// import Base from "./dashboard-base.js";

// const base = new Base();

const btnLogin = document.querySelector('.user-form');
const input_matricula = document.querySelector('.login_email');
const input_senha = document.querySelector('.login_senha');


async function loginFuncionario(usuario,senha) {
    const response = await fetch("http://152.250.104.13:6969/funcionario/login", {
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

    const matricula = input_matricula.value.trim();
    const senha = input_senha.value.trim();

    const responseCode = await loginFuncionario(matricula, senha);

    console.log(responseCode)
    if(responseCode.statusCode === 200){
        // base.displayMessage(responseCode)
        console.log(responseCode);

        const userToken = responseCode.body.usuario.token; // TODO: ???????????? why usuario???????
        const userType = "FUNCIONARIO";
        document.cookie = `userToken=${userToken}; path=/;`;
        document.cookie = `userType=${userType}; path=/;`;
        window.location.href = ('/public/html/pages/dashboard-autor.html')
    }else{
        base.displayMessage(responseCode)
    }
})

