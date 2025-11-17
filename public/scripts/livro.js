import Base from "./base.js";

class Livro extends Base {
  constructor() {
    super();

    // Elementos
    this.bookNameElement = document.querySelector(".book__title");
    this.bookAuthorElement = document.querySelector(".book__author-name");
    this.bookDescriptionElement = document.querySelector(".book__description");
    this.loadingOverlayElement = document.querySelector(".loading-overlay");
    this.bookImageElement = document.querySelector(".book__image");
    this.bookAuthorImageElement = document.querySelector(".book__author-image");
    this.newBookLoanForm = document.querySelector("#new-loan-form");

    this.clientId=1; // valor mocado
    this.bookId = 0;

    // Execução
    this.initialize();
  }

  getBookIdByUrl() {
    return new URLSearchParams(document.location.search).get("id");
  }

  renderBook(bookData) {
    this.bookNameElement.textContent = bookData.nome;
    this.bookAuthorElement.textContent = bookData.autor.nome;
    this.bookDescriptionElement.textContent = bookData.descricao;
    this.bookImageElement.style.backgroundImage = `url("${bookData.imagem}")`;
    this.bookAuthorImageElement.style.backgroundImage = `url("${bookData.autor.imagem}")`;

    this.bookAuthorElement.setAttribute("href", `autor.html?id=${bookData.autor.idAutor}`);

    // Detalhes
    const details = [
      ["stock", bookData.estoque],
      ["genre", bookData.genero],
      ["pages", bookData.paginas],
      ["isbn", bookData.isbn],
      ["publisher", bookData.editora],
      ["date", bookData.dataPublicacao],
    ];

    for (const detail of details) document.querySelector(`.book__detail--${detail[0]}`).textContent = detail[1];

    setTimeout(() => this.loadingOverlayElement.classList.add("hidden"), 400)
  }

  // Submit 'Solicitar Emprestimo'
  async submitSolicitarEmprestimoForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.newBookLoanForm);
    const response = await this.postSolicitarEmprestimo(
        this.bookId,
        this.clientId,
        body.dataDevolucao
      );


    this.displayMessage(response);
    this.cleanForm(this.newBookLoanForm);
    this.hidePopUp();
    this.getBook(this.bookId).then(json => this.renderBook(json.body));
  }

  async setUpBookListeners() {
    const startLoanBtn = document.querySelector(".loan_area__button");
    if( // caso esteja logado
      document.cookie.split('userToken=')[1]?.split(';')[0] != null
    ) {
      startLoanBtn.style.display = "unset"
    }

    startLoanBtn.addEventListener("click", () => this.showPopUp("#new-loan-form-popup"));
    this.newBookLoanForm.addEventListener("submit", async event => this.submitSolicitarEmprestimoForm(event));
  }

  async initialize() {

    this.bookId = this.getBookIdByUrl();

    this.getBook(this.bookId).then(json => this.renderBook(json.body));
    this.setUpBookListeners()
  }
}

const livro = new Livro();
