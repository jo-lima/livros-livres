import Base from "./base.js";
import DashboardBase from "./dashboard-base.js";

// probably will change, using dashboardbase for now.
class ClientProfile extends DashboardBase {
  constructor() {
    super();

    // Elementos HTML
    this.pendingList = document.querySelector(".pendencias-list__pendencias");
    this.loanDetailRight = document.querySelector(".pendencia-atual__info-descritiva");
    this.loanDetailLeft = document.querySelector(".pendencia-atual__info-visual");

    this.delayLoanForm = document.querySelector("#delay-loan-form");
    this.cancelPendingForm = document.querySelector("#cancel-loan-form");

    this.delayButton = document.querySelector(".acoes__prorrogar");
    this.delayButton = document.querySelector(".acoes__cancelar");

    // Variaveis global
    this.clientId = 1; // valor mocado
    this.currentLoanId = 0; // ID da pendencia atual, nos detalhes
    this.currentLoanValue; // Objeto da pendencia atual

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

  // -- FUNCOES GERAIS --
  async setCurrentPending(loanId) {
    this.currentLoanId = loanId;
    document.querySelectorAll(".pendencias__pendencia").forEach(pendencia => {
      pendencia.classList = "pendencias__pendencia";
    });
    document.querySelector(`#pendencia-${this.currentLoanId}`).classList.add("selecionada");
  }

  // -- RENDERS --
  // Renderiza lista de pendencias na esquerda
  renderPendencyList(loanData) {
    this.pendingList.innerHTML = "";

    const semPendencias = `
      <div class="pendencias__fim-pendencias">
        <p class="fim-pendencias__aviso">Sem mais pendencias :^)</p>
      </div>
    `

    if(loanData!=undefined && loanData.length > 0){
      loanData.forEach(loan => {
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

  generateLoanDescriptionItem(title, info){
    const item = `
      <div class="data__item">
        <span>${title}</span>
        <span class="item__info">${info}</span>
      </div>
    `;
    return item;
  }

  // Renderiza o detalhamento do empréstimo selecionado.
  generateLoanDetailsActions(loanData){
    const canDelay = loanData.dataEstendidaDevolucao == null && loanData.dataColeta != null;
    const canCancel = loanData.dataColeta == null;

    if (!canDelay && !canCancel) {
      return '</div></div>';
    }

    let actions = '</div><div class="info-descritiva__acoes">';

    if (canDelay) {
      actions += '<button class="acoes__prorrogar">Prorrogar</button>';
    }

    if (canCancel) {
      actions += '<button class="acoes__cancelar">Cancelar Solicitação</button>';
    }

    return actions + '</div></div>';
  }

  generateLoanDetailsItens(loanData){
    let loanDetailsItens = "";

    // Append infos only of value returned is not null
    const infoItems = [
      { title: "Data Solicitação:", value: loanData.dataSolicitacaoEmprestimo },
      { title: "Data Coleta:", value: loanData.dataColeta },
      { title: `Data ${loanData.dataEstendidaDevolucao != null ? "Inicial" : ""} de Devolução:`, value: loanData.dataPrevistaDevolucao },
      { title: "Data de Devolução Estendida:", value: loanData.dataEstendidaDevolucao },
      { title: "Data Devolução:", value: loanData.dataDevolucao }
    ];
    infoItems.forEach(item => {
      if (item.value != null) {
      loanDetailsItens += this.generateLoanDescriptionItem(item.title, item.value);
      }
    });

    loanDetailsItens += `<p class="data__item">Descrição: ${loanData.livro.descricao}</p>`

    return loanDetailsItens;
  }

  generateLoanDetailVisual(loanData){
    let loanDetailVisual = "";
    loanDetailVisual += `
      <div class="pendencia-atual__info-visual">
        <div class="info-visual__livro-capa">
          <img src="${loanData.livro.imagem}" alt="Foto Livro">
        </div>
        <div class="info-visual__livro_identity">
          <p class="livro_identity__titulo">${loanData.livro.nome}</p>
          <p class="livro_identity__autor">${loanData.livro.autor.nome}</p>
        </div>
      </div>
    `;
    return loanDetailVisual;
  }

  renderLoanDetails(loanData){
    // Oculta a parte de "livro atual" caso nao tenha nenhuma pendencia.
    if(loanData == null){
      document.querySelector(".titles__livro-atual-title").style.display = "none"
      document.querySelector(".content__pendencia-atual").style.display = "none"
      return
    }

    // start generating code
    let detailsElmnts = `
      <div class="pendencia-atual__info-descritiva">
        <div class="info-descritiva__data">

          <div class="data__status-emprestimo data__item">
            <span class="status-emprestimo_title">Status do Empréstimo:</span>
            <span class="status-emprestimo__info item__info">${loanData.status}</span>
          </div>

          <hr>
      `;

    // generate info lists of info
    detailsElmnts += this.generateLoanDetailsItens(loanData);
    // generate bottom buttons if needed.
    detailsElmnts += this.generateLoanDetailsActions(loanData);
    this.loanDetailRight.innerHTML = detailsElmnts;

    const loanVisualDetails = this.generateLoanDetailVisual(loanData);
    this.loanDetailLeft.innerHTML = loanVisualDetails;

  }

  // -- FUNÇÕES DOS BOTÕES --

  // 'Detalhes'
  async handleCurrentPending(event){
    const target = event.target.closest(".detalhar-pendencia__button");
    if (target == null) return;

    this.setCurrentPending(target.dataset.emprestimoId);
    this.listRenderPendences();
  }

  // 'Cancelar'
  async handleCancelLoan(event){
    const target = event.target.closest(".acoes__cancelar");
    if (target == null) return;

    this.showPopUp(".dashboard__popup--cancelar-pendencia")
  }

  // 'Prorrogar'
  async handleDelayLoan(event){
    const target = event.target.closest(".acoes__prorrogar");
    if (target == null) return;

    document.getElementById("popUpDataDevolucao").value=this.currentLoanValue.body.dataPrevistaDevolucao
    this.showPopUp(".dashboard__popup--prorrogar-pendencia")
  }

  // Submit 'Cancelar'
  async submitCancelLoan(event){
    event.preventDefault();

    const body = this.formDataObject(this.cancelPendingForm);
    const response = await this.postCancelarEmprestimo(this.currentLoanId);

    this.displayMessage(response);

    this.cleanForm(this.cancelPendingForm);
    this.hidePopUp();
    this.listRenderPendences();
  }

  // Submit 'Prorogar'
  async submitDeleyLoan(event){
    event.preventDefault();

    const body = this.formDataObject(this.delayLoanForm);
    const response = await this.postAdiarEmprestimo(this.currentLoanId, body.dataEstendidaDevolucao);

    this.displayMessage(response);

    this.cleanForm(this.delayLoanForm);
    this.hidePopUp();
    this.listRenderPendences();
  }

// Aplicando os EventListeners
  async setUpProfileListeners() {
    const pendingDetailsButtons = document.querySelectorAll(".detalhar-pendencia__button");
    const deleyLoanButton = document.querySelector("#submit-delay-loan");
    const cancelLoanButton = document.querySelector("#submit-cancel-loan");

    pendingDetailsButtons.forEach((btn) => {
      btn.addEventListener("click", async (event) => {this.handleCurrentPending(event)});
    })
    deleyLoanButton.addEventListener("click", async (event) => {this.submitDeleyLoan(event)});
    cancelLoanButton.addEventListener("click", async (event) => {this.submitCancelLoan(event)});
  }

// Renderizar e atualizar elementos em tempo real
  async listRenderPendences() {
    this.getEmprestimoById(this.currentLoanId).then((json) => {
      this.currentLoanValue = json;
      this.renderLoanDetails(this.currentLoanValue.body);

      this.delayButton = document.querySelector(".acoes__prorrogar");
      if(this.delayButton != null) {
        this.delayButton.addEventListener("click", async (event) => {this.handleDelayLoan(event)});
      }
      this.cancelButton = document.querySelector(".acoes__cancelar");
      if(this.cancelButton != null) {
        this.cancelButton.addEventListener("click", async (event) => {this.handleCancelLoan(event)});
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
        return historyStatus.includes(i.status);
      });

      this.renderPendencyList(this.pendenciesList);
      this.setUpProfileListeners();

      if(this.pendenciesList.length > 0) {
        this.setCurrentPending(this.pendenciesList[0].idEmprestimo);
      }

      this.getEmprestimoById(this.currentLoanId).then((json) => {
        this.renderLoanDetails(json.body);
      });

      this.listRenderPendences();
    });

  }
}

const clientProfile = new ClientProfile();