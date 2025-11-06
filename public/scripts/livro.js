// Elementos
const bookNameElement = document.querySelector(".book__title");
const bookAuthorElement = document.querySelector(".book__author-name");
const bookDescriptionElement = document.querySelector(".book__description");

function renderBook(json) {
  bookNameElement.textContent = json.nome;
  bookAuthorElement.textContent = json.autor;
  bookDescriptionElement.textContent = json.descricao;
}

async function a() {
  const response = await fetch(`http://localhost:6969/livro/4/busca`, {
    method: "GET",
  });
  const json = await response.json();
  console.log(json);
  renderBook(json.body);
}

a();
