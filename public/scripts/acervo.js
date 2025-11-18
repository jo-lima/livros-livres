import Requests from "./requests.js";
import "./nav-bar.js";

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

      if (!book.ativo) return;

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
      this.addSearchFunctionality();
      this.renderGenres();
    });
  }
  addSearchFunctionality() {
  const searchInput = document.querySelector(".library-books__search");

  if (!searchInput) return;

  let todosOsLivros = [];

  // Primeiro carrega todos os livros
  this.getAllBooks().then((json) => {
    todosOsLivros = json.body || [];
    
    // Busca quando o usuário pressiona Enter
    searchInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        const query = searchInput.value.trim().toLowerCase();

        if (query === "") {
          // Se o campo estiver vazio, mostra tudo de novo
          this.renderAllBooks({ body: todosOsLivros });
        } else {
          // Filtra localmente por título OU autor
          this.filtrarLivrosLocalmente(todosOsLivros, query);
        }
      }
    });
  });
}

// Filtra localmente por título ou autor
filtrarLivrosLocalmente(livros, query) {
  const livrosFiltrados = livros.filter(livro => {
    const tituloMatch = livro.nome?.toLowerCase().includes(query);
    const autorMatch = livro.autor?.nome?.toLowerCase().includes(query);
    return tituloMatch || autorMatch;
  });

  if (livrosFiltrados.length > 0) {
    this.renderAllBooks({ body: livrosFiltrados });
  } else {
    this.booksGrid.innerHTML = "<p>Nenhum livro ou autor encontrado.</p>";
  }
}

async renderGenres() {
  const genresContainer = document.querySelector(".library-filters__genres");

  try {
    // Busca todos os livros no backend
    const response = await this.getAllBooks();

    if (!response.body || !response.body.length) {
      genresContainer.innerHTML = "<p>Nenhum gênero encontrado.</p>";
      return;
    }

    // Extrai todos os gêneros únicos da lista de livros
    const genres = [...new Set(response.body.map((book) => book.genero))];

    // Limpa o container
    genresContainer.innerHTML = "";

    // Cria dinamicamente as checkboxes de cada gênero
    genres.forEach((genre) => {
      const genreDiv = document.createElement("div");
      genreDiv.classList.add("library-filters__genre");

      genreDiv.innerHTML = `
        <input type="checkbox" class="library-filters__genre-check" value="${genre}" id="genre-${genre}">
        <label for="genre-${genre}" class="library-filters__genre-label">${genre}</label>
      `;

      genresContainer.appendChild(genreDiv);
    });

    // Agora adiciona o evento de filtro
    this.addGenreFilter();
  } catch (error) {
    console.error("Erro ao carregar gêneros:", error);
    genresContainer.innerHTML = "<p>Erro ao carregar gêneros.</p>";
  }
}

addGenreFilter() {
  const checkboxes = document.querySelectorAll(".library-filters__genre-check");

  if (!checkboxes.length) return;

  checkboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", async () => {
      // Permite apenas um gênero ativo por vez
      checkboxes.forEach((cb) => {
        if (cb !== checkbox) cb.checked = false;
      });

      // Se o checkbox foi desmarcado → mostra tudo
      if (!checkbox.checked) {
        const allBooks = await this.getAllBooks();
        this.renderAllBooks(allBooks);
        return;
      }

      // Busca no backend os livros com o gênero selecionado
      try {
        const response = await this.sendPostRequest(
          `http://${this.SERVER_URL}/livro/lista`,
          { genero: checkbox.value } // envia apenas um gênero
        );

        if (response.body && response.body.length > 0) {
          this.renderAllBooks(response);
        } else {
          this.booksGrid.innerHTML = "<p>Nenhum livro encontrado para esse gênero.</p>";
        }
      } catch (error) {
        console.error("Erro ao filtrar gênero:", error);
        this.booksGrid.innerHTML = "<p>Erro ao filtrar gênero.</p>";
      }
    });
  });
}

}

const acervo = new Acervo();
