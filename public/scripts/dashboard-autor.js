import DashboardBase from "./dashboard-base.js";

class DashboardAutor extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.authorsTableElement = document.querySelector(
      ".dashboard__table--authors tbody"
    );
    this.newAuthorForm = document.querySelector("#new-author-form");
    this.editAuthorForm = document.querySelector("#edit-author-form");
    this.editAuthorSubmit = document.querySelector("#edit-author-submit");

    // Execução
    this.initialize();
  }

  // Renderiza os autores na tabela
  renderAllAuthors(authorsData) {
    // TODO - Lidar
    if (authorsData.statusCode == 404) {
      this.displayMessage(authorsData);
      return;
    }

    this.authorsTableElement.innerHTML = "";

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
    if (authorsData.statusCode == 404) {
      this.displayMessage(authorsData);
      return;
    }

    // Autores registrados
    document.querySelector(".dashboard__card--authors-amount").textContent =
      authorsData.body.length;
  }

  // Enviar formulário de criação de autor
  async submitNewAuthorForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.newAuthorForm);
    const response = await this.createAuthor(body);

    this.displayMessage(response);
    this.cleanForm(this.newAuthorForm);
    this.hidePopUp();
    this.listRenderAuthors();
  }

  // Enviar formulário de edição de autor
  async submitEditAuthorForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.editAuthorForm);
    const response = await this.editAuthor(
      this.editAuthorSubmit.dataset.idAuthor,
      body
    );

    this.displayMessage(response);
    this.cleanForm(this.editAuthorForm);
    this.hidePopUp();
    this.listRenderAuthors();
  }

  async handleAuthorActions(event) {
    const target = event.target.closest(".dashboard__action-button");

    if (target == null) return;

    const rowId = target.closest("tr").dataset.id;

    let json;

    // Inativar/Ativar autor
    if (
      target.classList.contains("disable-author-button") ||
      target.classList.contains("enable-author-button")
    ) {
      json = target.classList.contains("disable-author-button")
        ? await this.disableAuthor(rowId)
        : await this.enableAuthor(rowId);

      this.displayMessage(json);
      this.listRenderAuthors();
    }

    // Exibir formulário para editar autor
    if (target.classList.contains("edit-author-button")) {
      const response = await this.getAuthor(rowId);

      document.querySelector(".dashboard__popup-input--name").value =
        response.body.nome;
      document.querySelector(".dashboard__popup-input--quote").value =
        response.body.citacao;
      document.querySelector(".dashboard__popup-input--description").value =
        response.body.descricao;
      document.querySelector(".dashboard__popup-input--image").value =
        response.body.imagem;

      this.editAuthorSubmit.dataset.idAuthor = rowId; // Linkando o ID do autor da linha com o botão de submit - ta meio feio gente desculpa :c
      this.showPopUp(".dashboard__popup--edit-author");
    }
  }

  // Aplicando os EventListeners
  setUpAuthorListeners() {
    document
      .querySelector("#new-author-button")
      .addEventListener("click", () =>
        this.showPopUp(".dashboard__popup--new-author")
      );
    document
      .querySelector("#new-author-submit")
      .addEventListener("click", (event) => this.submitNewAuthorForm(event));
    this.authorsTableElement.addEventListener("click", (event) =>
      this.handleAuthorActions(event)
    );
    this.editAuthorSubmit.addEventListener("click", (event) =>
      this.submitEditAuthorForm(event)
    );
  }

  // Listando e renderizando autores
  async listRenderAuthors() {
    await this.getAllAuthors().then((json) => {
      this.renderAllAuthors(json);
      this.updateCards(json);
    });
  }

  async initialize() {
    this.listRenderAuthors();
    this.setUpAuthorListeners();
  }
}

const dashboardAuthor = new DashboardAutor();
