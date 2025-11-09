// Listando todos os livros
async function listAllBooks() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "POST",
    body: "{}", // Lista todos os livros
    headers: {
      "Content-Type": "application/json",
    },
  });

  return await response.json();
}

// Renderizando os livros
const booksTableElement = document.querySelector(
  ".dashboard__table--books tbody"
);

async function renderBooks(bookArray) {
  // Limpa a grid antes de renderizar
  booksTableElement.innerHTML = "";

  // Loopa pelos livros
  bookArray.forEach((book) => {
    let buttonHtml;

    if (book.ativo == true) {
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

    booksTableElement.insertAdjacentHTML("beforeend", bookHtml);
  });
}

// Atualizando os cards
async function updateCards(booksArray) {
  // Livros disponíveis
  document.querySelector(".dashboard__card--books-amount").textContent =
    booksArray.length;

  // Livros em estoque
  document.querySelector(".dashboard__card--books-stock").textContent =
    booksArray.reduce((acc, book) => {
      return acc + book.estoque; // Soma todos os book.estoque em um só valor
    }, 0);
}

// Renderizar e atualizar os cards
async function listRenderBooks() {
  listAllBooks().then((json) => {
    updateCards(json.body);
    renderBooks(json.body);
  });
}

// Listando todos os autores
async function listAllAuthors() {
  const body = {
    nome: "",
    descricao: "",
    citacao: "",
    ativo: null,
  };

  const response = await fetch(`http://localhost:6969/autor/lista`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: {
      "Content-Type": "application/json",
    },
  });

  return await response.json();
}

// Populando lista de autores
const authorsListElement = document.querySelector("#authors-list");

function renderAuthorsList(authorsArray) {
  authorsListElement.innerHTML = "";

  authorsArray.forEach((author) => {
    const authorHtml = `<option value="${author.idAutor}">${author.nome}</option>`;

    authorsListElement.insertAdjacentHTML("beforeend", authorHtml);
  });
}

// Criando livro
async function createBook(data) {
  const response = await fetch("http://localhost:6969/livro/novo", {
    method: "POST",
    body: JSON.stringify(data), // Formulário do autor
    headers: {
      "Content-Type": "application/json",
    },
  });

  return await response.json();
}

// Abrir pop-up
document.querySelector("#new-book-button").addEventListener("click", showPopUp);

// Formulário
const newBookForm = document.querySelector("#new-book-form");

document
  .querySelector("#new-book-submit")
  .addEventListener("click", async function (event) {
    // Evitando o submit do form
    event.preventDefault();

    // Capturando campos do formulário
    const bookData = new FormData(newBookForm);
    const data = {};

    bookData.forEach((value, key) => (data[key] = value));
    const response = await createBook(data);

    displayMessage(response);

    // Limpando formulário
    const inputs = newBookForm.querySelectorAll("input, textarea, select");
    inputs.forEach((input) => (input.value = ""));

    // Fechar pop-up
    hidePopUp();

    // Atualizando a listagem
    listRenderBooks();
  });

// Ações
booksTableElement.addEventListener("click", async function (event) {
  target = event.target.closest(".dashboard__action-button");

  if (target == null) return;

  let json;

  // Inativar livro
  if (
    target.classList.contains("disable-book-button") ||
    target.classList.contains("enable-book-button")
  ) {
    const row = target.closest("tr");

    const response = await fetch(
      `http://localhost:6969/livro/${row.dataset.id}/${
        target.classList.contains("disable-book-button") ? "inativar" : "ativar"
      }`,
      {
        method: "POST",
      }
    );

    json = await response.json();
  }

  listRenderBooks();
  displayMessage(json);
});

// Execução
listRenderBooks();
listAllAuthors().then((json) => {
  renderAuthorsList(json.body);
});
