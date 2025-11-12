
// probably will change, using dashboardbase for now.
class ClientProfile extends DashboardBase {
  constructor() {
    super();

    // Elementos
    this.pendingList = document.querySelector(".pendencias-list__pendencias");
    this.pendingDetailDescritive = document.querySelector(".pendencia-atual__info-descritiva");
    this.pendingDetailVisual = document.querySelector(".pendencia-atual__info-visual");
    this.currentPending = 1; // trocar para valor dinamico
    // Execução
    this.initialize();
  }

  // Renderiza lista de pendencias na esquerda
  renderPendingList(pendingData) {
    // this.displayMessage(pendingData);

    this.pendingList.innerHTML = "";

    const semPendencias = `
      <div class="pendencias__fim-pendencias">
        <p class="fim-pendencias__aviso">Sem mais pendencias :^)</p>
      </div>
    `
    if(pendingData!=undefined){
      pendingData.forEach(loan => {
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
                data-emprestimo-id="${loan.idEmprestimo}"
              >
              Detalhes
              </button>
          </div>
          </div>`;

        this.pendingList.insertAdjacentHTML("beforeend", pendingLoanHtml);
        this.pendingList.insertAdjacentHTML("beforeend", "<hr>");
      });
    } else {
      this.pendingList.insertAdjacentHTML("beforeend", semPendencias);
      this.pendingList.insertAdjacentHTML("beforeend", "<hr>");
    }

    console.log("Render Pending List")

  }

  renderPendingDetails(pendingData){
    console.log(pendingData, "asd");
    const pendingDescritiveDetails = `
      <div class="pendencia-atual__info-descritiva">
        <div class="info-descritiva__data">

          <div class="data__data-limite data__item">
            <span class="data-limite_title">Data Limite Devolução:</span>
            <span class="data-limite__info item__info">${pendingData.dataPrevistaDevolucao}</span>
          </div>

          <div class="data__data-limite data__item">
            <span class="data-limite__title">Dias até limite da devolução:</span>
            <span class="data-limite__info item__info">XX Dias</span>
          </div>

          <div class="data__data-coleta data__item">
            <span class="data-coleta__title">Data Coleta:</span>
            <span class="data-coleta__info item__info">${pendingData.dataColeta}</span>
          </div>
          <p class="data__item">Descrição: ${pendingData.livro.descricao}</p>
        </div>

        <div class="info-descritiva__acoes">
          <button class="acoes__prorrogar">Prorrogar</button>
          <button class="acoes__cancelar">Cancelar Solicitação</button>
        </div>

      </div>
    `;

    const pendingVisualDetails = `
      <div class="pendencia-atual__info-visual">
        <div class="info-visual__livro-capa">
          <img src="${pendingData.livro.imagem}" alt="Foto Livro">
        </div>
        <div class="info-visual__livro_identity">
          <p class="livro_identity__titulo">${pendingData.livro.nome}</p>
          <p class="livro_identity__autor">${pendingData.livro.autor.nome}</p>
        </div>
      </div>
    `;

    this.pendingDetailVisual.innerHTML = pendingVisualDetails;
    this.pendingDetailDescritive.innerHTML = pendingDescritiveDetails;
  }

  async handleCurrentPending(event){
    const target = event.target.closest(".detalhar-pendencia__button");
    if (target == null) return;

    this.currentPending = target.dataset.emprestimoId;
    this.listRenderPendences()
  }

// Aplicando os EventListeners
  async setUpProfileListeners() {
    const pendingDetailsButtons = document.querySelectorAll(".detalhar-pendencia__button");

    pendingDetailsButtons.forEach((btn) => {
      btn.addEventListener("click", async (event) => {this.handleCurrentPending(event)});
    })
    console.log("Setup Listeners");
  }

// Renderizar e atualizar elementos em tempo real
  async listRenderPendences() {
    this.getEmprestimoById(this.currentPending).then((json) => {
      this.renderPendingDetails(json.body);
    });
  }

  // Inicialização
  initialize() {
    this.getAllEmprestimos().then((json) => {
      this.renderPendingList(json.body);
      this.setUpProfileListeners();
    }); // render pending objects

    this.getEmprestimoById(this.currentPending).then((json) => {
      this.renderPendingDetails(json.body);
    });

    this.listRenderPendences();
  }
}

const clientProfile = new ClientProfile();