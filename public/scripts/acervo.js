// Elementos
const booksGrid = document.querySelector(".library-books__grid");

// Body da request
body = {
  idLivro: null,
  nome: null,
  genero: null,
  paginas: null,
  isbn: null,
  descricao: null,
  estoque: null,
  editora: null,
  dataPublicacao: null,
  ativo: null,
};

// Headers
headers = {
  "Content-Type": "application/json",
};

// Renderizar livros
function renderBooks(json) {
  // Apaga o conteúdo do acervo
  booksGrid.innerHTML = "";

  // Loopa a array de livros
  json.forEach((book) => {
    bookHtml = `
    <div class="library-books__book">
        <a href="./livro.html?id=${
          book.idLivro
        }" class="library-books__book-image-link">
            <div class="library-books__book-image"></div>
        </a>
        <div class="library-books__book-information-container text--center">
            <p class="library-books__book-title">${book.nome}</p>
            <a href="autor.html" class="library-books__book-author">${
              book.descricao
            }</a>
            <p class="library-books__book-year">${
              book.dataPublicacao.split("-")[0]
            }</p>
        </div>
    </div>
    `;

    // Insere na grid
    booksGrid.insertAdjacentHTML("beforeend", bookHtml);
  });
}

async function listBooks() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: headers,
  });
  const json = await response.json();
  console.log(json.body);
  renderBooks(json.body);
}

listBooks();
