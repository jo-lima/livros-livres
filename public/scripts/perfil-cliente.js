
// probably will change, using dashboardbase for now.
class PerfilCliente extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.pendingList = document.querySelector(".pendencias-list__pendencias");

    // Execução
    this.initialize();
  }

  // Renderiza os livros na tabela
  async renderPending(pendingData) {
    // this.displayMessage(pendingData);

    this.pendingList.innerHTML = "";

    const semPendencias = `
      <div class="pendencias__fim-pendencias">
        <p class="fim-pendencias__aviso">Sem mais pendencias :^)</p>
      </div>
    `

    pendingData.body.forEach(loan => {

      const pendingLoanHtml = `
        <div class="pendencias__pendencia">
        <p class="pendencia__nome">${loan.livro.nome}:</p>
        <div class="pendencia__data-coleta pendencia__data">
            <p class="data-coleta__title">Data Prevista de Coleta:</p>
            <p class="data-coleta__info">${loan.dataColeta}</p>
        </div>
        <div class="pendencia__data-devolucao pendencia__data">
            <p class="data-devolucao__title">Data Prevista de Devolução:</p>
            <p class="data-devolucao__info">${loan.dataPrevistaDevolucao}</p>
        </div>
        <div class="pendencia__detalhar-pendencia">
            <button class="detalhar-pendencia__button">Detalhes</button>
        </div>
        </div>`;

      this.pendingList.insertAdjacentHTML("beforeend", pendingLoanHtml);
      this.pendingList.insertAdjacentHTML("beforeend", "<hr>");
    });

    this.pendingList.insertAdjacentHTML("beforeend", semPendencias);
  }

// Renderizar e atualizar os cards
  async listRenderPendences() {
    this.getAllEmprestimos().then((json) => {
        console.log(json)
      this.renderPending(json);
    });
  }

  // Inicialização
  initialize() {
    this.listRenderPendences()
  }
}

const perfilCliente = new PerfilCliente();
