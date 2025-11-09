// // Abrir pop-up
//

// // Formulário
// const newAuthorForm = document.querySelector("#new-author-form");

// document
//   .querySelector("#new-author-submit")
//   .addEventListener("click", async function (event) {
//     // Evitando o submit do formulário
//     event.preventDefault();

//     // Capturando campos do formulário
//     const authorData = new FormData(newAuthorForm);
//     const data = {};

//     authorData.forEach((value, key) => (data[key] = value));
//     const response = await createAuthor(data);

//     displayMessage(response);

//     // Limpando formulário
//     const inputs = newAuthorForm.querySelectorAll("input, textarea");
//     inputs.forEach((input) => (input.value = ""));

//     // Fechar pop-up
//     hidePopUp();

//     // Atualizando a listagem
//     listRenderAuthors();
//   });

// // Editar autor
// async function editAuthor(id, data) {
//   const response = await fetch(`http://localhost:6969/autor/${id}/atualizar`, {
//     method: "POST",
//     body: JSON.stringify(data), // Formulário do autor
//     headers: {
//       "Content-Type": "application/json",
//     },
//   });

//   return await response.json();
// }

// // Formulário
// const editAuthorForm = document.querySelector("#edit-author-form");
// const editAuthorSubmit = document.querySelector("#edit-author-submit");

// editAuthorSubmit.addEventListener("click", async function (event) {
//   // Evitando o submit do formulário
//   event.preventDefault();

//   // Capturando campos do formulário
//   const authorData = new FormData(editAuthorForm);
//   const data = {};

//   authorData.forEach((value, key) => (data[key] = value));
//   const response = await editAuthor(editAuthorSubmit.dataset.idAuthor, data);

//   displayMessage(response);

//   // Limpando formulário
//   const inputs = editAuthorForm.querySelectorAll("input, textarea");
//   inputs.forEach((input) => (input.value = ""));

//   // Fechar pop-up
//   hidePopUp();

//   // Atualizando a listagem
//   listRenderAuthors();
// });

// // Coluna de ações
// authorsTableElement.addEventListener("click", async function (event) {
//   target = event.target.closest(".dashboard__action-button");

//   if (target == null) return;

//   let json;
//   const row = target.closest("tr");

//   // Inativar autor
//   if (
//     target.classList.contains("disable-author-button") ||
//     target.classList.contains("enable-author-button")
//   ) {
//     const response = await fetch(
//       `http://localhost:6969/autor/${row.dataset.id}/${
//         target.classList.contains("disable-author-button")
//           ? "inativar"
//           : "ativar"
//       }`,
//       {
//         method: "POST",
//       }
//     );

//     json = await response.json();

//     displayMessage(json);

//     listRenderAuthors();
//   }

//   // Exibir formulário para editar autor
//   if (target.classList.contains("edit-author-button")) {
//     const response = await fetch(
//       `http://localhost:6969/autor/${row.dataset.id}/busca`
//     );

//     const json = await response.json();

//     // Exibir formulário com os valores preenchidos
//     document.querySelector(".dashboard__popup-input--name").value =
//       json.body.nome;
//     document.querySelector(".dashboard__popup-input--quote").value =
//       json.body.citacao;

//     document.querySelector(".dashboard__popup-input--description").value =
//       json.body.descricao;

//     // Linkando o ID do autor da linha com o botão de submit - ta meio feio gente desculpa :c
//     editAuthorSubmit.dataset.idAuthor = row.dataset.id;

//     showPopUp(".dashboard__popup--edit-author");
//   }
// });

// // Execução
// async function listRenderAuthors() {
//   listAllAuthors().then((json) => {
//     updateCards(json);
//     renderAuthors(json);
//   });
// }

// listRenderAuthors();

class DashboardAutor extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.authorsTableElement = document.querySelector(
      ".dashboard__table--authors tbody"
    );

    this.newAuthorForm = document.querySelector("#new-author-form");

    // Setup dos EventListeners
    this.setUpListeners();

    // Execução
    this.initialize();
  }

  renderAllAuthors(authorsData) {
    // TODO - Lidar
    if (authorsData.statusCode == 404) return;

    // Limpa a grid antes de renderizar
    this.authorsTableElement.innerHTML = "";

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

      this.authorsTableElement.insertAdjacentHTML("beforeend", authorHtml);
    });
  }

  // Atualizando os cards
  updateCards(authorsData) {
    // TODO - Lidar
    if (authorsData.statusCode == 404) return;

    // Autores registrados
    document.querySelector(".dashboard__card--authors-amount").textContent =
      authorsData.body.length;
  }

  setUpListeners() {
    // Abrir pop-up da criação de autor
    document
      .querySelector("#new-author-button")
      .addEventListener("click", () => {
        this.showPopUp(".dashboard__popup--new-author");
      });
  }

  async initialize() {
    // Renderizando autores
    await this.getAllAuthors().then((json) => {
      this.renderAllAuthors(json);
      this.updateCards(json);
    });
  }
}

const dashboardAuthor = new DashboardAutor();
