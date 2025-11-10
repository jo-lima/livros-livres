class DashboardLivro extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.booksTableElement = document.querySelector(".dashboard__table--books tbody");
    this.authorsListElement = document.querySelector("#authors-list");
    this.newBookForm = document.querySelector("#new-book-form");

    // Execução
    this.initialize();
  }

  async renderBooks(booksData) {
    // TODO - Lidar
    if (booksData.statusCode == 404) {
      this.displayMessage(booksData);
      return;
    }

    // Limpa a grid antes de renderizar
    this.booksTableElement.innerHTML = "";

    // Loopa pelos livros
    booksData.body.forEach(book => {
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
          <td>${buttonHtml}</td>
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

    authorsData.forEach(author => {
      const authorHtml = `<option value="${author.idAutor}">${author.nome}</option>`;

      this.authorsListElement.insertAdjacentHTML("beforeend", authorHtml);
    });
  }

  // Enviar formulário da criação de livro
  async submitNewBookForm(event) {
    // Evitando o submit do form
    event.preventDefault();

    // Capturando campos do formulário
    const body = this.formDataObject(this.newBookForm);

    const response = await this.createBook(body);

    this.displayMessage(response);

    // Limpando formulário
    this.cleanForm(this.newBookForm)

    // Fechar pop-up
    this.hidePopUp();

    // Atualizando a listagem
    this.listRenderBooks();
  }

  // Ações
  async handleBookActions(event) {
    const target = event.target.closest(".dashboard__action-button");

    if (target == null) return;

    const rowId = target.closest("tr").dataset.id;

    let json;

    // Inativar/Ativar livro
    if (target.classList.contains("disable-book-button") || target.classList.contains("enable-book-button")) {
      json = target.classList.contains("disable-book-button") ? await this.disableBook(rowId) : await this.enableBook(rowId)

      this.listRenderBooks();
      this.displayMessage(json);
    }
  }

  async setUpBookListeners() {
    document.querySelector("#new-book-button").addEventListener("click", () => this.showPopUp(".dashboard__popup--new-book"));
    document.querySelector("#new-book-submit").addEventListener("click", async (event) => this.submitNewBookForm(event));
    this.booksTableElement.addEventListener("click", async (event) => this.handleBookActions(event));
  }

  // Renderizar e atualizar os cards
  async listRenderBooks() {
    this.getAllBooks().then((json) => {
      this.updateCards(json);
      this.renderBooks(json);
    });
  }

  initialize() {
    this.listRenderBooks();
    this.getAllAuthors().then(json => this.renderAuthorsList(json.body));
    this.setUpBookListeners();
  }
}

const dashboardLivro = new DashboardLivro();
