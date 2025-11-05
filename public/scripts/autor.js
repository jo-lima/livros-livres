// Elementos
const authorNameElement = document.querySelector(".author-name");
const authorDescriptionElement = document.querySelector(".author-biography");
const authorQuoteElement = document.querySelector(".author-quote");

// Renderizar
function renderAuthor(json) {
  authorNameElement.textContent = json.nome;
  authorDescriptionElement.textContent = json.descricao;
  authorQuoteElement.textContent = json.citacao;
}

json = {
  nome: "Luca Maia",
  descricao:
    'Luca Maia (1839-1908) é amplamente considerado o maior nome da literatura brasileira. Nascido no Rio de Janeiro, teve origem humilde e foi um autodidata. Atuou como jornalista, contista, cronista, romancista, poeta e teatrólogo. Sua obra mais famosa, "Memórias Póstumas de Brás Cubas" (1881), inaugurou o Realismo no Brasil. Machado é conhecido por sua ironia, pessimismo e profunda análise psicológica das personagens. Foi o fundador e o primeiro presidente da Academia Brasileira de Letras. ',
  citacao: "eu sou o luca eu sou bobao lero lero",
};

renderAuthor(json);

// const params = new URLSearchParams(document.location.search);
// let id = params.get("id");

// console.log(id);

// // renderAuthor(json)

// headers = {
//   "Access-Control-Allow-Origin": "*",
//   // 'Access-Control-Allow-Credentials': 'true'
// };

// async function a() {
//   const response = await fetch(`http://localhost:6969/autor/${id}/busca`, {
//     headers: headers,
//     method: "GET",
//   });
//   const json = await response.json();
//   console.log(json.body);
//   renderAuthor(json.body);
// }

// const json = a();
// console.log(json);
