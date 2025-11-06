// Elementos
const bookNameElement = document.querySelector(".book__title");
const bookAuthorElement = document.querySelector(".book__author-name");
const bookDescriptionElement = document.querySelector(".book__description");

const params = new URLSearchParams(document.location.search);
let id = params.get("id");

function renderBook(json) {
  bookNameElement.textContent = json.nome;
  bookAuthorElement.textContent = json.autor.nome;
  bookDescriptionElement.textContent = json.descricao;

  // Detalhes
  document.querySelector(".book__detail--genre").textContent = json.genero;
  document.querySelector(".book__detail--pages").textContent = json.paginas;
  document.querySelector(".book__detail--isbn").textContent = json.isbn;
  document.querySelector(".book__detail--publisher").textContent = json.editora;
  document.querySelector(".book__detail--date").textContent =
    json.dataPublicacao;
  json.dataLancamento;
}

async function a() {
  const response = await fetch(`http://localhost:6969/livro/${id}/busca`, {
    method: "GET",
  });
  const json = await response.json();
  console.log(json);
  renderBook(json.body);
}

a();
