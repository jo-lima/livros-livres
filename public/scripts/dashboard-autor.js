// Listando todos os autores
async function listAllAuthors() {
  const body = {
    nome: "",
    descricao: "",
    citacao: "",
    ativo: null,
  };

  const response = await fetch(`http://localhost:6969/autor/lista`, {
    method: "POST",
    body: JSON.stringify(body), // Lista todos os autores
    headers: {
      "Content-Type": "application/json",
    },
  });

  return await response.json();
}

// Renderizando os autores
const authorsTableElement = document.querySelector(
  ".dashboard__table--authors tbody"
);

async function renderAuthors(authorsArray) {
  // Limpa a grid antes de renderizar
  authorsTableElement.innerHTML = "";

  // Loopa pelos autores
  authorsArray.forEach((author) => {
    const authorHtml = `
        <tr>
            <td>${author.idAutor}</td>
            <td>${author.nome}</td>
            <td>${author.descricao}</td>
            <td>${author.citacao}</td>
            <td>${author.ativo == true ? "Ativo" : "Inativo"}</td>
        </tr>`;

    authorsTableElement.insertAdjacentHTML("beforeend", authorHtml);
  });
}

// Atualizando os cards
function updateCards(authorsArray) {
  // Autores registrados
  document.querySelector(".dashboard__card--authors-amount").textContent =
    authorsArray.length;
}

// Criando autor
async function createAuthor(data) {
  const response = await fetch(`http://localhost:6969/autor/novo`, {
    method: "POST",
    body: JSON.stringify(data), // Formulário do autor
    headers: {
      "Content-Type": "application/json",
    },
  });

  return await response.json();
}

// Abrir pop-up
document
  .querySelector("#new-author-button")
  .addEventListener("click", showPopUp);

// Criando autor
const newAuthorForm = document.querySelector("#new-author-form");

document
  .querySelector("#new-author-submit")
  .addEventListener("click", async function (event) {
    // Evitando o submit do form
    event.preventDefault();

    // Capturando campos do formulário
    const authorData = new FormData(newAuthorForm);
    const data = {};

    authorData.forEach((value, key) => (data[key] = value));
    const response = await createAuthor(data);

    console.log(response);

    // Limpando formulário
    const inputs = newAuthorForm.querySelectorAll("input, textarea");
    inputs.forEach((input) => (input.value = ""));

    // Fechar pop-up
    hidePopUp();
  });

// Execução
listAllAuthors().then((json) => {
  updateCards(json.body);
  renderAuthors(json.body);
});
