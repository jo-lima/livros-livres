import Base from "./base.js";
import DashboardBase from "./dashboard-base.js";

// probably will change, using dashboardbase for now.
class ClientProfile extends DashboardBase {
  constructor() {
    super();

    // Elementos HTML
    // Info Perfil // Lista de pendencia // histórico
    this.profileInfo = document.querySelector(".profile-info-tab__perfil-data")
    this.pendingList = document.querySelector(".pendencias-list__pendencias");
    this.historyGrid = document.querySelector(".hitorico-tab__livros-grid");

    // Detalhes da pendencia
    this.loanDetailRight = document.querySelector(".pendencia-atual__info-descritiva");
    this.loanDetailLeft = document.querySelector(".pendencia-atual__info-visual");

    // Formularios do popup
    this.editClientForm = document.querySelector("#edit-client-form");
    this.delayLoanForm = document.querySelector("#delay-loan-form");
    this.cancelPendingForm = document.querySelector("#cancel-loan-form");

    // Botões de ação estáticos
    this.delayButton = document.querySelector(".acoes__prorrogar");
    this.cancelButton = document.querySelector(".acoes__cancelar");
    this.editProfileButton = document.querySelector(".editar-infos__button");

    // Variaveis global
    this.clientId = 1; // valor mocado
    this.clientValue;
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
  // Renderiza o "Informações do Perfil"
  renderProfileInfo(){
    const clientInfo = this.clientValue;
    let pfpImage = "/public/images/perfil-cliente/porra-cara-nao-sei-velho.jpg";

    this.profileInfo.innerHTML = `<h2 class="perfil-data__title">Informações do Perfil:</h1>`;

    const infoList = [
      {title: "Nome", value: clientInfo.nome},
      {title: "Email", value: clientInfo.email},
      {title: "CPF", value: clientInfo.cpf},
      {title: "Telefone", value: clientInfo.telefone},
    ]
    const editBtn = `
      <div class="perfil-data__editar-infos">
        <button class="editar-infos__button">Editar</button>
      </div>
    `;

    if(clientInfo.imagem != null){
      pfpImage = clientInfo.imagem;
    }

    document.querySelector("#profile-info-pfp").src = pfpImage;

    infoList.forEach(info => {
      const finalP = `<p>${info.title}: ${info.value}</p>`
      if(info.value != null){
        this.profileInfo.insertAdjacentHTML("beforeend", finalP)
      }
    });

    this.profileInfo.insertAdjacentHTML("beforeend", editBtn)
  }

  // Renderiza lista de pendencias na esquerda
  renderPendencyList() {
    this.pendingList.innerHTML = "";

    const semPendencias = `
      <div class="pendencias__fim-pendencias">
        <p class="fim-pendencias__aviso">Sem mais pendencias :^)</p>
      </div>
    `

    if(this.pendenciesList!=undefined && this.pendenciesList.length > 0){
      this.pendenciesList.forEach(loan => {
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

  // Renderiza o Histórico
  renderHistory(){
    this.historyGrid.innerHTML = "";

    this.historyList.forEach(loan => {
    let historyEntry = `
          <div class="livros-grid__livro">
            <div class="livro__info-visual">
              <img src="${loan.livro.imagem}" alt="#">
            </div>
            <div class="livro__info-descritiva">
              <p class="info-descritiva__titulo">${loan.livro.nome}</p>
              <p class="info-descritiva__autor">${loan.livro.autor.nome}</p>

              ${this.generateHistoryEntryInfo(loan)}

              </div>
            </div>
      `;

      this.historyGrid.insertAdjacentHTML("beforeend", historyEntry);
    });
  }

  generateHistoryEntryInfo(loanData){

    let historyEntryInfo = "";

    let infoItems = [
      { title: "Status do Empréstimo", value: loanData.status }
    ];

    if(loanData.status == this.emprestimoStatus.CANCELADO){
      infoItems.push(
        { title: "Data Solicitação", value: loanData.dataSolicitacaoEmprestimo },
        { title: "Data Cancelamento", value: loanData.dataCancelamento }
      );
    }
    else if(
      loanData.status == this.emprestimoStatus.FINALIZADO ||
      loanData.status == this.emprestimoStatus.FINALIZADO_ATRASADO
    ){
      infoItems.push(
        { title: "Data de Coleta", value: loanData.dataColeta },
        { title: "Data de Devolução", value: loanData.dataDevolucao },
        { title: "Data Prevista de Devolução", value: loanData.dataPrevistaDevolucao },
        { title: "Data Prevista Estendida de Devolução", value: loanData.dataEstendidaDevolucao }
      );
    }

    infoItems.forEach(item => {
      if (item.value != null) {
        historyEntryInfo += this.generateHistoryEntryInfoItem(item.title, item.value);
      }
    });

    return historyEntryInfo;
  }

  generateHistoryEntryInfoItem(title, value){

    let infoItem = `
      <div class="info-descritiva__emprestado-em info-descritiva__data">
        <p class="emprestado-em__title">${title}:</p>
        <p class="emprestado-em__info info-descritiva__info">${value}</p>
      </div>
    `
    return infoItem;
  }

  // -- FUNÇÕES DOS BOTÕES --

  // 'Detalhes'
  async handleCurrentPending(event){
    const target = event.target.closest(".detalhar-pendencia__button");
    if (target == null) return;
    console.log(target.dataset.emprestimoId);

    this.setCurrentPending(target.dataset.emprestimoId);
    this.listRenderPendences();
  }

  // 'Editar Cliente'
  async handleEditClient(event){
    const target = event.target.closest(".editar-infos__button");
    if (target == null) return;

    this.editClientForm.querySelectorAll('input').forEach(field => field.value = this.clientValue[field.name]);

    this.showPopUp("#edit-client-form-popup")
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

  // Submit 'Editar Cliente'
  async submitEditClientForm(event) {
    event.preventDefault();

    const body = this.formDataObject(this.editClientForm);
    const response = await this.editClient(this.editClientForm.dataset.idClient, body);

    this.displayMessage(response);
    this.cleanForm(this.editClientForm);
    this.hidePopUp();
    this.listRenderPendences();
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

    this.editClientForm.addEventListener("submit", async (event) => {this.submitEditClientForm(event)})
  }

// Renderizar e atualizar elementos em tempo real
  async listRenderPendences() {

    this.getClienteById(this.clientId).then((json) => {
      this.clientValue = json.body;
      this.renderProfileInfo();
    });

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

      this.editProfileButton = document.querySelector(".editar-infos__button");
      if(this.editProfileButton != null) {
        this.editProfileButton.addEventListener("click", async (event) => {this.handleEditClient(event)});
      }
    });

  }

  // Inicialização
  initialize() {
    const historyStatus = this.historyStatus;

    this.editClientForm.dataset.idClient = this.clientId;

    this.getAllEmprestimosByClientId(this.clientId, "").then((json) => {
      let allEmprestimos = json.body;

      this.pendenciesList = allEmprestimos.filter(function (i,n){
        return !historyStatus.includes(i.status);
      });

      this.historyList = allEmprestimos.filter(function (i,n){
        return historyStatus.includes(i.status);
      });

      this.getClienteById(this.clientId).then((json) => {
        this.clientValue = json.body;
        this.renderProfileInfo();
      });

      this.renderPendencyList();
      this.renderHistory();
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