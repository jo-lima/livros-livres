
// probably will change, using dashboardbase for now.
class ClientProfile extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.pendingList = document.querySelector(".pendencias-list__pendencias");
    this.pendingDetailDescritive = document.querySelector("info-descritiva__data");
    this.pendingDetailVisual = document.querySelector("pendencia-atual__info-visual");
    this.currentPending = 0; // trocar para valor dinamico

    this.pendingDetailsButtons = document.querySelectorAll(".detalhar-pendencia__button");
    // Execução
    this.initialize();
  }

  // Renderiza lista de pendencias na esquerda
  async renderPendingList(pendingData) {
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
            <button 
              class="detalhar-pendencia__button" 
              dataset-emprestimoId=${loan.idEmprestimo}
            >
            Detalhes
            </button>
        </div>
        </div>`;

      this.pendingList.insertAdjacentHTML("beforeend", pendingLoanHtml);
      this.pendingList.insertAdjacentHTML("beforeend", "<hr>");
    });

    this.pendingList.insertAdjacentHTML("beforeend", semPendencias);
  }

  async renderPendingDetails(pendingData){
    console.log(pendingData);
  }

  async handleCurrentPending(event){
    console.log('oiee')
    const target = event.target.closest(".dashboard__action-button");

    if (target == null) return;

    this.currentPending = event.dataset.emprestimoId;
  }

// Aplicando os EventListeners
  async setUpProfileListeners() {
    console.log(this.pendingDetailsButtons, "asd")
    this.pendingDetailsButtons.forEach((btn) => {
      btn.addEventListener("click", async (event) => console.log("asda"));
      console.log(btn);
    }) 
  }

// Renderizar e atualizar os cards
  async listRenderPendences() {
    this.getAllEmprestimos().then((json) => {
      this.renderPendingList(json);
    });
    this.getEmprestimoById(this.currentPending).then((json) => {
      this.renderPendingDetails(json);
    });
  }

  // Inicialização
  initialize() {
    this.listRenderPendences();
    this.setUpProfileListeners();
  }
}

const clientProfile = new ClientProfile();