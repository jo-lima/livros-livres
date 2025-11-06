// Elementos
const booksTableElement = document.querySelector(
  ".dashboard__books-table tbody"
);

const booksAmountElement = document.querySelector(
  ".dashboard__card--books-amount"
);

const booksStockElement = document.querySelector(
  ".dashboard__card--books-stock"
);

// Listando todos os livros
headers = {
  "Content-Type": "application/json",
};

async function listAllBooks() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "POST",
    body: "{}", // Lista todos os livros
    headers: headers,
  });

  return await response.json();
}

// Renderizando os livros
async function renderBooks(bookArray) {
  // Limpa a grid antes de renderizar
  booksTableElement.innerHTML = "";

  // Loopa pelos livros
  bookArray.forEach((book) => {
    bookHtml = `  
      <tr>
        <td><a href="livro.html?id=${book.idLivro}">${book.nome}</a></td>
        <td><a href="autor.html?id=${book.autor.idAutor}">${
      book.autor.nome
    }</a></td>
        <td>${book.genero}</td>
        <td>${book.paginas}</td>
        <td>${book.isbn}</td>
        <td>${book.estoque}</td>
        <td>${book.editora}</td>
        <td>${book.dataPublicacao}</td>
        <td>${book.ativo == true ? "Ativo" : "Inativo"}</td>
      </tr>`;

    booksTableElement.insertAdjacentHTML("beforeend", bookHtml);
  });
}

// Atualizando os cards
async function updateCards(booksArray) {
  booksAmountElement.textContent = booksArray.length;
  booksStockElement.textContent = booksArray.reduce((acc, book) => {
    return acc + book.estoque; // Soma todos os book.estoque em um só valor
  }, 0);
}

// Execução
listAllBooks().then((json) => {
  updateCards(json.body);
  renderBooks(json.body);
});
