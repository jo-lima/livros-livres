// Tabela
const booksTableElement = document.querySelector(
  ".dashboard__books-table tbody"
);

// Listando todos os livros
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

headers = {
  "Content-Type": "application/json",
};

async function listAllBooks() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: headers,
  });

  const json = await response.json();
  return json;
}

// Renderizando os livros
async function renderBooks(bookArray) {
  booksTableElement.innerHTML = "";
  bookArray.forEach((book) => {
    bookHtml = `
      <tr>
        <td>${book.nome}</td>
        <td>${book.autor.nome}</td>
        <td>${book.genero}</td>
        <td>${book.paginas}</td>
        <td>${book.isbn}</td>
        <td>${book.estoque}</td>
        <td>${book.editora}</td>
        <td>${book.dataPublicacao}</td>
        <td>${book.ativo}</td>
      </tr>`;

    booksTableElement.insertAdjacentHTML("beforeend", bookHtml);
  });
}

// Atualizando os cards
async function updateCards(booksArray) {
  const booksAmountElement = document.querySelector(
    ".dashboard__card--books-amount"
  );
  const booksStockElement = document.querySelector(
    ".dashboard__card--books-stock"
  );

  booksAmountElement.textContent = booksArray.length;
  booksStockElement.textContent = booksArray.reduce((acc, book) => {
    return acc + book.estoque;
  }, 0);
}

listAllBooks().then((json) => {
  updateCards(json.body);
  renderBooks(json.body);
});
