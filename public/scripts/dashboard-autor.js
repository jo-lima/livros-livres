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

async function renderAuthors(authorsData) {
  // TODO - Lidar
  if (authorsData.statusCode == 404) return;

  // Limpa a grid antes de renderizar
  authorsTableElement.innerHTML = "";

  // Loopa pelos autores
  authorsData.body.forEach((author) => {
    let buttonHtml;

    if (author.ativo) {
      buttonHtml = `
      <button class="disable-author-button dashboard__action-button">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M18.364 18.364A9 9 0 0 0 5.636 5.636m12.728 12.728A9 9 0 0 1 5.636 5.636m12.728 12.728L5.636 5.636" />
        </svg>
      </button>`;
    } else {
      buttonHtml = `
      <button class="enable-author-button dashboard__action-button">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
        </svg>
      </button>`;
    }

    const authorHtml = `
        <tr data-id="${author.idAutor}">
            <td>${author.idAutor}</td>
            <td>${author.nome}</td>
            <td>${author.descricao}</td>
            <td>${author.citacao}</td>
            <td>${author.ativo == true ? "Ativo" : "Inativo"}</td>
            <td>
            <div class="dashboard__action-buttons">
              ${buttonHtml}
              <button class="edit-author-button dashboard__action-button">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                </svg>
              </button>
            </div>
            </td>
        </tr>`;

    authorsTableElement.insertAdjacentHTML("beforeend", authorHtml);
  });
}

// Atualizando os cards
function updateCards(authorsData) {
  // TODO - Lidar
  if (authorsData.statusCode == 404) return;

  // Autores registrados
  document.querySelector(".dashboard__card--authors-amount").textContent =
    authorsData.body.length;
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
  .addEventListener("click", function () {
    showPopUp(".dashboard__popup--new-author");
  });

// Formulário
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

    displayMessage(response);

    // Limpando formulário
    const inputs = newAuthorForm.querySelectorAll("input, textarea");
    inputs.forEach((input) => (input.value = ""));

    // Fechar pop-up
    hidePopUp();

    // Atualizando a listagem
    listRenderAuthors();
  });

// Execução
async function listRenderAuthors() {
  listAllAuthors().then((json) => {
    updateCards(json);
    renderAuthors(json);
  });
}

authorsTableElement.addEventListener("click", async function (event) {
  target = event.target.closest(".dashboard__action-button");

  if (target == null) return;

  let json;
  const row = target.closest("tr");

  // Inativar autor
  if (
    target.classList.contains("disable-author-button") ||
    target.classList.contains("enable-author-button")
  ) {
    const response = await fetch(
      `http://localhost:6969/autor/${row.dataset.id}/${
        target.classList.contains("disable-author-button")
          ? "inativar"
          : "ativar"
      }`,
      {
        method: "POST",
      }
    );

    json = await response.json();
  }

  // Editar autor
  if (target.classList.contains("edit-author-button")) {
    showPopUp(".dashboard__popup--edit-author");
  }

  listRenderAuthors();
  displayMessage(json);
});

listRenderAuthors();
