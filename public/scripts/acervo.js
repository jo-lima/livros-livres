import Base from "./base.js";

class Acervo extends Base {
  constructor() {
    super();

    // Elementos
    this.booksGrid = document.querySelector(".library-books__grid");

    // Execução
    this.initialize();
  }

  renderAllBooks(booksData) {
    // Apaga o conteúdo do acervo
    this.booksGrid.innerHTML = "";

    // Loopa a array de livros
    booksData.body.forEach((book) => {
      const bookHtml = `
        <div class="library-books__book">
          <a href="./livro.html?id=${
            book.idLivro
          }" class="library-books__book-image-link">
          <div class="library-books__book-image"
            style="
                background-image: url('${book.imagem}');
              ">
          </div>
          </a>
          <div class="library-books__book-information-container text--center">
          <p class="library-books__book-title">${book.nome}</p>
          <a href="autor.html?id=${
            book.autor.idAutor
          }" class="library-books__book-author">${book.autor.nome}</a>
          <p class="library-books__book-year">${
            book.dataPublicacao.split("-")[0]
          }</p>
          </div>
        </div>`;

      // Insere na grid
      this.booksGrid.insertAdjacentHTML("beforeend", bookHtml);
    });
  }

  async initialize() {
    this.getAllBooks().then((json) => {
      this.renderAllBooks(json);
    });
  }
}

const acervo = new Acervo();
