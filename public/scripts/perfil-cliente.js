import Base from "./base.js";
import DashboardBase from "./dashboard-base.js";

// probably will change, using dashboardbase for now.
class ClientProfile extends DashboardBase {
  constructor() {
    super();

    // Elementos HTML
    this.pendingList = document.querySelector(".pendencias-list__pendencias");
    this.pendingDetailDescritive = document.querySelector(".pendencia-atual__info-descritiva");
    this.pendingDetailVisual = document.querySelector(".pendencia-atual__info-visual");

    this.delayPendingForm = document.querySelector("#deley-pendencia");

    this.delayButton = document.querySelector(".acoes__prorrogar");

    // Variaveis global
    this.clientId = 1; // valor mocado
    this.currentPendingId = 0; // ID da pendencia atual, nos detalhes
    this.currentPendingValue; // Objeto da pendencia atual

    this.emprestimoStatus = Object.freeze({
      PEDIDO:              "PEDIDO",
      ACEITO:              "ACEITO",
      CRIADO:              "CRIADO",
      ADIADO:              "ADIADO",
      CANCELADO:           "CANCELADO",
      FINALIZADO:          "FINALIZADO",
      FINALIZADO_ATRASADO: "FINALIZADO_ATRASADO"
    });

    this.historyStatus = [
      this.emprestimoStatus.CANCELADO,
      this.emprestimoStatus.FINALIZADO,
      this.emprestimoStatus.FINALIZADO_ATRASADO,
    ];

    this.pendenciesList; // Lista de emprestimos pendentes, nao finalizados
    this.historyList; // Lista de empresitmos finalizados, no historico

    // Execução
    this.initialize();
  }

  // Renderiza lista de pendencias na esquerda
  renderPendingList(pendingData) {
    this.pendingList.innerHTML = "";

    const semPendencias = `
      <div class="pendencias__fim-pendencias">
        <p class="fim-pendencias__aviso">Sem mais pendencias :^)</p>
      </div>
    `

    if(pendingData!=undefined && pendingData.length > 0){
      pendingData.forEach(loan => {
        const pendingLoanHtml = `
          <div class="pendencias__pendencia" id="pendencia-${loan.idEmprestimo}">
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
    }

    this.pendingList.insertAdjacentHTML("beforeend", semPendencias);
    this.pendingList.insertAdjacentHTML("beforeend", '<hr style="border: none; margin:15px;">');
  }

  generatePendingDescriptionItem(title, info){
    const item = `
      <div class="data__item">
        <span>${title}</span>
        <span class="item__info">${info}</span>
      </div>
    `;
    return item;
  }

  renderPendingDetails(pendingData){
    if(pendingData == null){
      // Oculta a parte de "livro atual"
      document.querySelector(".titles__livro-atual-title").style.display = "none"
      document.querySelector(".content__pendencia-atual").style.display = "none"
      return
    }

    let detailsElmnts = "";

    detailsElmnts += `
      <div class="pendencia-atual__info-descritiva">
        <div class="info-descritiva__data">

          <div class="data__status-emprestimo data__item">
            <span class="status-emprestimo_title">Status do Empréstimo:</span>
            <span class="status-emprestimo__info item__info">${pendingData.status}</span>
          </div>

          <hr>
      `;

    const infoItems = [
      { title: "Data Solicitação:", value: pendingData.dataSolicitacaoEmprestimo },
      { title: "Data Coleta:", value: pendingData.dataColeta },
      { title: `Data ${pendingData.dataEstendidaDevolucao != null ? "Inicial" : ""} de Devolução:`, value: pendingData.dataPrevistaDevolucao },
      { title: "Data de Devolução Estendida:", value: pendingData.dataEstendidaDevolucao },
      { title: "Data Devolução:", value: pendingData.dataDevolucao }
    ];

    infoItems.forEach(item => {
      if (item.value != null) {
      detailsElmnts += this.generatePendingDescriptionItem(item.title, item.value);
      }
    });

      detailsElmnts += `<p class="data__item">Descrição: ${pendingData.livro.descricao}</p>`

      // end of details div start of acoes div
      detailsElmnts += `
        </div>

        <div class="info-descritiva__acoes">
      `
      // pode prorrogar
      if(pendingData.dataEstendidaDevolucao == null && pendingData.dataColeta != null) {
        detailsElmnts += `<button class="acoes__prorrogar">Prorrogar</button>`
      }
      // pode cancelar
      if(pendingData.dataColeta == null) {
        detailsElmnts += `<button class="acoes__cancelar">Cancelar Solicitação</button>`

      }
      // ending acoes div
      detailsElmnts+= `
        </div>

      </div>
    `;

    this.pendingDetailDescritive.innerHTML = detailsElmnts;

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

  }

  async setCurrentPending(loanId) {
    this.currentPendingId = loanId;
    document.querySelectorAll(".pendencias__pendencia").forEach(pendencia => {
      pendencia.classList = "pendencias__pendencia";
    });
    document.querySelector(`#pendencia-${this.currentPendingId}`).classList.add("selecionada");
  }

  async handleCurrentPending(event){
    const target = event.target.closest(".detalhar-pendencia__button");
    if (target == null) return;

    this.setCurrentPending(target.dataset.emprestimoId);
    this.listRenderPendences();
  }

  async handleDelayPending(event){
    const target = event.target.closest(".acoes__prorrogar");
    if (target == null) return;

    document.getElementById("popUpDataDevolucao").value=this.currentPendingValue.body.dataPrevistaDevolucao
    this.showPopUp(".dashboard__popup--prorrogar-pendencia")
  }

  async submitDeleyPending(event){
    event.preventDefault();

    const body = this.formDataObject(this.delayPendingForm);
    const response = await this.postAdiarEmprestimo(this.currentPendingId, body.dataEstendidaDevolucao);

    this.displayMessage(response);

    this.cleanForm(this.delayPendingForm);
    this.hidePopUp();
    this.listRenderPendences();
  }

// Aplicando os EventListeners
  async setUpProfileListeners() {
    const pendingDetailsButtons = document.querySelectorAll(".detalhar-pendencia__button");
    const deleyLoanButton = document.querySelector("#deley-loan");

    pendingDetailsButtons.forEach((btn) => {
      btn.addEventListener("click", async (event) => {this.handleCurrentPending(event)});
    })
    deleyLoanButton.addEventListener("click", async (event) => {this.submitDeleyPending(event)});
  }

// Renderizar e atualizar elementos em tempo real
  async listRenderPendences() {
    this.getEmprestimoById(this.currentPendingId).then((json) => {
      this.currentPendingValue = json;
      this.renderPendingDetails(this.currentPendingValue.body);

      this.delayButton = document.querySelector(".acoes__prorrogar");
      if(this.delayButton != null) {
        this.delayButton.addEventListener("click", async (event) => {this.handleDelayPending(event)});
      }
    });

  }

  // Inicialização
  initialize() {
    const historyStatus = this.historyStatus;

    this.getAllEmprestimosByClientId(this.clientId, "").then((json) => {
      let allEmprestimos = json.body;

      this.pendenciesList = allEmprestimos.filter(function (i,n){
        return !historyStatus.includes(i.status);
      });

      this.historyList = allEmprestimos.filter(function (i,n){
        return i.status.includes(historyStatus);
      });

      this.renderPendingList(this.pendenciesList);
      this.setUpProfileListeners();

      if(this.pendenciesList.length > 0) {
        this.setCurrentPending(this.pendenciesList[0].idEmprestimo);
      }

      this.getEmprestimoById(this.currentPendingId).then((json) => {
        this.renderPendingDetails(json.body);
      });

      this.listRenderPendences();
    });

  }
}

const clientProfile = new ClientProfile();