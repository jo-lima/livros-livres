// Elementos
const authorsTableElement = document.querySelector(
  ".dashboard__table--authors tbody"
);

const authorsAmountElement = document.querySelector(
  ".dashboard__card--authors-amount"
);

// Listando todos os autores
const body = {
  nome: "",
  descricao: "",
  citacao: "",
  ativo: null,
};

const headers = {
  "Content-Type": "application/json",
};

async function listAllAuthors() {
  const response = await fetch(`http://localhost:6969/autor/lista`, {
    method: "POST",
    body: JSON.stringify(body), // Lista todos os autores
    headers: headers,
  });

  return await response.json();
}

// Renderizando os autores
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
async function updateCards(authorsArray) {
  authorsAmountElement.textContent = authorsArray.length;
}

// Criando autor
const newAuthorButton = document.querySelector("#new-author-button");
const newAuthorForm = document.querySelector("#new-author-form");
const newAuthorSubmitButton = document.querySelector("#new-author-submit");
const popUpElement = document.querySelector(".dashboard__popup-container");

async function createAuthor(data) {
  const response = await fetch(`http://localhost:6969/autor/novo`, {
    method: "POST",
    body: JSON.stringify(data), // Formulário do autor
    headers: headers,
  });

  return await response.json();
}

// Abrir pop-up
newAuthorButton.addEventListener("click", function () {
  popUpElement.classList.remove("hidden");
});

// Fechar pop-up
const closePopUpButton = document.querySelector(".dashboard__popup-close");

closePopUpButton.addEventListener("click", function () {
  popUpElement.classList.add("hidden");
});

popUpElement.addEventListener("click", function (e) {
  if (!e.target.classList.contains("dashboard__popup-container")) return;

  popUpElement.classList.add("hidden");
});

newAuthorSubmitButton.addEventListener("click", async function (event) {
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
  popUpElement.classList.add("hidden");
});

// Execução
listAllAuthors().then((json) => {
  updateCards(json.body);
  renderAuthors(json.body);
});
