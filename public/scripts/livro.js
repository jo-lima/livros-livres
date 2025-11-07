// Elementos
const bookNameElement = document.querySelector(".book__title");
const bookAuthorElement = document.querySelector(".book__author-name");
const bookDescriptionElement = document.querySelector(".book__description");
const loadingOverlayElement = document.querySelector(".loading-overlay");

// Capturando o ID do livro
const params = new URLSearchParams(document.location.search);
const id = params.get("id");

// Request do livro
async function getBook(id) {
  const response = await fetch(`http://localhost:6969/livro/${id}/busca`, {
    method: "GET",
  });

  return await response.json();
}

// Renderizando o livro
function renderBook(json) {
  bookNameElement.textContent = json.nome;
  bookAuthorElement.textContent = json.autor.nome;
  bookDescriptionElement.textContent = json.descricao;

  bookAuthorElement.setAttribute("href", `autor.html?id=${json.autor.idAutor}`);

  // Detalhes
  const details = [
    ["genre", json.genero],
    ["pages", json.paginas],
    ["isbn", json.isbn],
    ["publisher", json.editora],
    ["date", json.dataPublicacao],
  ];

  for (const detail of details) {
    document.querySelector(`.book__detail--${detail[0]}`).textContent =
      detail[1];
  }

  setTimeout(() => {
    loadingOverlayElement.classList.add("hidden");
  }, 400);
}

// Execução
getBook(id).then((json) => {
  renderBook(json.body);
});
