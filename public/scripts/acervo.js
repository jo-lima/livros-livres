// Elementos
const booksGrid = document.querySelector(".library-books__grid");

// Headers
headers = {
  "Content-Type": "application/json",
};

// Request do livro
async function listAllBooks() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "POST",
    body: "{}", // Lista por todos os livros
    headers: headers,
  });

  return await response.json();
}

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
            <a href="autor.html?id=${
              book.autor.idAutor
            }" class="library-books__book-author">${book.autor.nome}</a>
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

// Execução
listAllBooks().then((json) => {
  renderBooks(json.body);
});
