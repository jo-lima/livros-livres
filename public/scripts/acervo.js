import Requests from "./requests.js";
import "./nav-bar.js";
const token = document.cookie;
console.log(token);

class Acervo extends Requests {
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

  renderNoErrorMessage(){
    const errorMessage = document.createElement('div')
    errorMessage.classList.add('library-books__error-message')
    errorMessage.innerHTML = `<p>Nenhum livro encontrado</p>`
    this.booksGrid.append(errorMessage)
  }

  async initialize() {
    this.getAllBooks().then((json) => {
      if (json.statusCode == 404) {
        this.renderNoErrorMessage();
        return
      }

      this.renderAllBooks(json);
    });
  }
}

const acervo = new Acervo();
