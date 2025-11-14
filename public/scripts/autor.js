import Requests from "./requests.js";

class Autor extends Requests {
  constructor() {
    super();

    // Elementos
    this.authorNameElement = document.querySelector(".author-name");
    this.authorDescriptionElement = document.querySelector(".author-biography");
    this.authorQuoteElement = document.querySelector(".author-quote");
    this.authorImageElement = document.querySelector(".author-image");
    this.loadingOverlayElement = document.querySelector(".loading-overlay");

    // Execução
    this.initialize();
  }

  getAuthorIdByUrl() {
    return new URLSearchParams(document.location.search).get("id");
  }

  renderAuthor(authorData) {
    // Atualizando os campos do autor
    this.authorNameElement.textContent = authorData.nome;
    this.authorDescriptionElement.textContent = authorData.descricao;
    this.authorQuoteElement.textContent = authorData.citacao;
    this.authorImageElement.style.backgroundImage = `url("${authorData.imagem}")`;

    // Esconder tela de loading
    setTimeout(() => {
      this.loadingOverlayElement.classList.add("hidden");
    }, 300);
  }

  async initialize() {
    this.getAuthor(this.getAuthorIdByUrl()).then((json) => {
      this.renderAuthor(json.body);
    });
  }
}

const autor = new Autor();
