import DashboardBase from "../scripts/dashboard-base.js";

class DashboardLivro extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.booksTableElement = document.querySelector(".dashboard__table--books tbody");
    this.authorsListElement = document.querySelectorAll(".dashboard-authors-list");
    // Novo livro
    this.newBookForm = document.querySelector("#new-book-form");
    this.newBookButton = document.querySelector("#new-book-button");
    // Editar livro    
    this.editBookForm = document.querySelector("#edit-book-form");

    // Execução
    this.initialize();
  }

  // Renderiza os livros na tabela
  async renderBooks(booksData) {
    // TODO - Lidar
    if (booksData.statusCode == 404) {
      this.displayMessage(booksData);
      return;
    }

    this.booksTableElement.innerHTML = "";

    booksData.body.forEach((book) => {
      let buttonHtml;

      if (book.ativo) {
        buttonHtml = `
          <button class="disable-book-button dashboard__action-button">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M18.364 18.364A9 9 0 0 0 5.636 5.636m12.728 12.728A9 9 0 0 1 5.636 5.636m12.728 12.728L5.636 5.636" />
            </svg>
          </button>`;
      } else {
        buttonHtml = `
          <button class="enable-book-button dashboard__action-button">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
            </svg>
          </button>`;
      }

      const bookHtml = `
        <tr data-id="${book.idLivro}">
          <td>${book.nome}</td>
          <td>${book.autor.nome}</td>
          <td>${book.genero}</td>
          <td>${book.paginas}</td>
          <td>${book.isbn}</td>
          <td>${book.estoque}</td>
          <td>${book.editora}</td>
          <td>${book.dataPublicacao}</td>
          <td>${book.ativo == true ? "Ativo" : "Inativo"}</td>
          <td>
            <div class="dashboard__action-buttons">
              ${buttonHtml}
              <button class="edit-book-button dashboard__action-button">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                </svg>
              </button>
            </div
          </td>
        </tr>`;

      this.booksTableElement.insertAdjacentHTML("beforeend", bookHtml);
    });
  }

  // Atualizando os cards
  async updateCards(booksData) {
    // TODO - Lidar
    if (booksData.statusCode == 404) {
      this.displayMessage(booksData);
      return;
    }

    // Livros disponíveis
    document.querySelector(".dashboard__card--books-amount").textContent = booksData.body.length;

    // Livros em estoque - Soma todos os book.estoque em um só valor
    document.querySelector(".dashboard__card--books-stock").textContent = booksData.body.reduce((acc, book) => acc + book.estoque, 0);
  }

  // Populando lista de autores
  renderAuthorsList(authorsData) {
    this.authorsListElement.innerHTML = "";

    authorsData.forEach((author) => {
      const authorHtml = `<option value="${author.idAutor}">${author.nome}</option>`;

      this.authorsListElement.forEach(list => list.insertAdjacentHTML("beforeend", authorHtml));
    });
  }

  // Enviar formulário da criação de livro
  async submitNewBookForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.newBookForm);
    const response = await this.createBook(body);

    this.displayMessage(response);
    this.cleanForm(this.newBookForm);
    this.hidePopUp();
    this.listRenderBooks();
  }

  // Enviar formulário da edicao de livro
  async submitEditBookForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.editBookForm);
    const response = await this.editBook(this.editBookForm.dataset.idLivro, body);

    this.displayMessage(response);
    this.cleanForm(this.editBookForm);
    this.hidePopUp();
    this.listRenderBooks();
  }

  // Ações individuais dos livros
  async handleBookActions(event) {
    const target = event.target.closest(".dashboard__action-button");

    if (target == null) return;

    const rowId = target.closest("tr").dataset.id;

    let json;

    // Inativar/Ativar livro
    if (target.classList.contains("disable-book-button") || target.classList.contains("enable-book-button")) {
      json = target.classList.contains("disable-book-button") ? await this.disableBook(rowId) : await this.enableBook(rowId);

      this.listRenderBooks();
      this.displayMessage(json);
    }

    // Editar
    if (target.classList.contains("edit-book-button")) {
      const response = await this.getBook(rowId);

      this.editBookForm.querySelectorAll('input, textarea').forEach(field => field.value = response.body[field.name]);

      this.editBookForm.querySelector('select[name="autorId"]').value = response.body.autor.idAutor;

      this.editBookForm.dataset.idLivro = rowId;
      this.showPopUp("#edit-book-form-popup");
    }
  }

  // Aplicando os EventListeners
  async setUpBookListeners() {
    this.booksTableElement.addEventListener("click", async event => this.handleBookActions(event));
    // Novo livro
    this.newBookButton.addEventListener("click", () => this.showPopUp("#new-book-form-popup"));
    this.newBookForm.addEventListener("submit", async event => this.submitNewBookForm(event));
    // Editar autor
    this.editBookForm.addEventListener("submit", async event => this.submitEditBookForm(event));
  }

  // Renderizar e atualizar os cards
  async listRenderBooks() {
    this.getAllBooks().then((json) => {
      this.updateCards(json);
      this.renderBooks(json);
    });
  }

  // Inicialização
  initialize() {
    this.listRenderBooks();
    this.getAllAuthors().then(json => this.renderAuthorsList(json.body));
    this.setUpBookListeners();
  }
}

const dashboardLivro = new DashboardLivro();
