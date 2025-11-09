class DashboardBase {
  SERVER_URL = "localhost:6969";

  constructor() {
    // Pop-up
    this.popUpOverlay = document.querySelector(".dashboard__popup-overlay");

    // Mensagem
    this.messageElement = document.querySelector(".dashboard__message");
    this.messageTimer;

    // Setup dos EventListeners
    this.setUpListeners();
  }

  // Requests
  async createAuthor(body) {
    // Valor padrão
    default_body = {
      nome: "",
      descricao: "",
      citacao: "",
    };

    const response = await fetch(`http://${this.SERVER_URL}/autor/novo`, {
      method: "POST",
      body: JSON.stringify(Object.assign(default_body, body)), // Formulário do autor
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json();
  }

  async getAllAuthors() {
    const response = await fetch(`http://${this.SERVER_URL}/autor/lista`, {
      method: "POST",
      body: JSON.stringify({
        nome: "",
        descricao: "",
        citacao: "",
        ativo: null,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json();
  }

  async getAuthor(id) {
    const response = await fetch(`http://${this.SERVER_URL}/autor/${id}/busca`);

    return await response.json();
  }

  async getAllBooks() {
    const response = await fetch(`http://${this.SERVER_URL}/livro/lista`, {
      method: "POST",
      body: "{}",
      headers: {
        "Content-Type": "application/json",
      },
    });

    return await response.json();
  }

  async getBook(id) {
    const response = await fetch(`http://${this.SERVER_URL}/livro/${id}/busca`);

    return await response.json();
  }

  // Mensagem
  displayMessage(json) {
    this.messageElement.textContent = json.message;
    this.messageElement.style.backgroundColor =
      json.statusCode == 200 ? "var(--text-color--green--regular)" : "red";

    if (this.messageElement.classList.contains("dashboard__message--hidden"))
      this.messageElement.classList.remove("dashboard__message--hidden");

    // Para o timeout anterior
    this.messageTimer && clearTimeout(this.messageTimer);

    this.messageTimer = setTimeout(() => {
      this.messageElement.classList.add("dashboard__message--hidden");
    }, 4000);
  }

  // Pop-Up
  showPopUp(popUpClass) {
    document.querySelector(popUpClass).classList.remove("hidden");
    this.popUpOverlay.classList.remove("hidden");
  }

  hidePopUp() {
    const popUps = document.querySelectorAll(".dashboard__popup");
    popUps.forEach((popUp) => popUp.classList.add("hidden"));
    this.popUpOverlay.classList.add("hidden");
  }

  // Setup dos EventListeners
  setUpListeners() {
    // Fechar pop-up no espaço de fora
    this.popUpOverlay.addEventListener("click", (event) => {
      if (!event.target.classList.contains("dashboard__popup-overlay")) return;
      this.hidePopUp();
    });

    // Fechar pop-up no botão 'X'
    document
      .querySelectorAll(".dashboard__popup-close")
      .forEach((button) =>
        button.addEventListener("click", () => this.hidePopUp())
      );

    // ================ AREA DE DEBUG================
    // this.xd.addEventListener("click", () => this.getBook(1));
    // ================================
  }
}

a = new DashboardBase();
