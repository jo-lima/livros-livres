import Base from "./base.js";

class Livro extends Base {
  constructor() {
    super();

    // Elementos
    this.bookNameElement = document.querySelector(".book__title");
    this.bookAuthorElement = document.querySelector(".book__author-name");
    this.bookDescriptionElement = document.querySelector(".book__description");
    this.loadingOverlayElement = document.querySelector(".loading-overlay");

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

    this.bookAuthorElement.setAttribute(
      "href",
      `autor.html?id=${bookData.autor.idAutor}`
    );

    // Detalhes
    const details = [
      ["genre", bookData.genero],
      ["pages", bookData.paginas],
      ["isbn", bookData.isbn],
      ["publisher", bookData.editora],
      ["date", bookData.dataPublicacao],
    ];

    for (const detail of details) {
      document.querySelector(`.book__detail--${detail[0]}`).textContent =
        detail[1];
    }

    setTimeout(() => {
      this.loadingOverlayElement.classList.add("hidden");
    }, 400);
  }

  async initialize() {
    this.getBook(this.getBookIdByUrl()).then((json) => {
      this.renderBook(json.body);
    });
  }
}

const livro = new Livro();
