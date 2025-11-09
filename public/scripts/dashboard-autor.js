// Elementos
const authorsTableElement = document.querySelector(
  ".dashboard__table--authors tbody"
);

const authorsAmountElement = document.querySelector(
  ".dashboard__card--authors-amount"
);

// Listando todos os autores
body = {
  nome: "",
  descricao: "",
  citacao: "",
  ativo: null,
};

headers = {
  "Content-Type": "application/json",
};

async function listAllAuthors() {
  const response = await fetch(`http://localhost:6969/autor/lista`, {
    method: "POST",
    body: JSON.stringify(body), // Lista todos os autores
    headers: headers,
  });

  return await response.json();
}

// Renderizando os autores
async function renderAuthors(authorsArray) {
  // Limpa a grid antes de renderizar
  authorsTableElement.innerHTML = "";

  // Loopa pelos autores
  authorsArray.forEach((author) => {
    authorHtml = `
        <tr>
            <td>${author.idAutor}</td>
            <td>${author.nome}</td>
            <td>${author.descricao}</td>
            <td>${author.citacao}</td>
            <td>${author.ativo == true ? "Ativo" : "Inativo"}</td>
        </tr>`;

    authorsTableElement.insertAdjacentHTML("beforeend", authorHtml);
  });
}

// Atualizando os cards
async function updateCards(authorsArray) {
  authorsAmountElement.textContent = authorsArray.length;
}

// Execução
listAllAuthors().then((json) => {
  updateCards(json.body);
  renderAuthors(json.body);
});
