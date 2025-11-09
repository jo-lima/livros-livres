// Elementos
const authorNameElement = document.querySelector(".author-name");
const authorDescriptionElement = document.querySelector(".author-biography");
const authorQuoteElement = document.querySelector(".author-quote");
const loadingOverlayElement = document.querySelector(".loading-overlay");

// Capturando o ID do autor
const params = new URLSearchParams(document.location.search);
const id = params.get("id");

// Request do autor
async function getAuthor() {
  const response = await fetch(`http://localhost:6969/autor/${id}/busca`, {
    method: "GET",
  });

  return await response.json();
}

// Renderizar
function renderAuthor(json) {
  // Atualizando os campos do autor
  authorNameElement.textContent = json.nome;
  authorDescriptionElement.textContent = json.descricao;
  authorQuoteElement.textContent = json.citacao;

  // Esconder tela de loading
  setTimeout(() => {
    loadingOverlayElement.classList.add("hidden");
  }, 300);
}

// Execução
getAuthor(id).then((json) => {
  renderAuthor(json.body);
});
