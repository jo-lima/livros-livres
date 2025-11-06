body = {
  idLivro: null,
  nome: "diario",
  genero: null,
  paginas: null,
  isbn: null,
  descricao: null,
  estoque: null,
  editora: null,
  dataPublicacao: null,
  ativo: null,
};

async function a() {
  const response = await fetch(`http://localhost:6969/livro/lista`, {
    method: "GET",
    body: JSON.stringify(body),
  });
  const json = await response.json();
  console.log(json);
  //   renderBook(json.body);
}

a();

json = {
  statusCode: 200,
  message: "",
  body: [
    {
      idLivro: 1,
      autor: null,
      nome: "Diario de um Zumbi",
      genero: "livro",
      paginas: 155,
      isbn: "1234",
      descricao: "descricao",
      estoque: 10,
      editora: "luca mais",
      dataPublicacao: "2000-01-01",
      ativo: true,
    },
    {
      idLivro: 2,
      autor: null,
      nome: "Diario de um Enderman",
      genero: "livro",
      paginas: 155,
      isbn: "1234",
      descricao: "descricao",
      estoque: 10,
      editora: "luca mais",
      dataPublicacao: "2000-01-01",
      ativo: true,
    },
    {
      idLivro: 3,
      autor: null,
      nome: "Diario de um Luca mais!!!!",
      genero: "livro",
      paginas: 155,
      isbn: "1234",
      descricao: "descricao",
      estoque: 10,
      editora: "luca mais",
      dataPublicacao: "2000-01-01",
      ativo: true,
    },
    {
      idLivro: 4,
      autor: null,
      nome: "Diario de um PINTO PITNJOERGNPRETHNJTRE XD",
      genero: "livro",
      paginas: 155,
      isbn: "1234",
      descricao: "descricao",
      estoque: 10,
      editora: "luca mais",
      dataPublicacao: "2000-01-01",
      ativo: true,
    },
  ],
};

const booksGrid = document.querySelector(".library-books__grid");

function renderBooks(json) {
  json.forEach((book) => {});
}
