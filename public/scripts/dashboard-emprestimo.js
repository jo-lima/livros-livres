import Base from "./base.js";

class DashboardEmprestimo extends Base {
  constructor(){
    super();

    if(document.cookie.split('userToken=')[1]?.split(';')[0] == null || document.cookie.split('userId=')[1]?.split(';')[0] != null) {
      document.querySelector(".dashboard__container").innerHTML = "<h1>Você não tem permissão para acessar esta página!</h1>"
      return
    }

    // Elementos
    this.loansTableElement = document.querySelector(".dashboard__table--loans tbody");
    // Novo empréstimo
    this.newLoanButton = document.querySelector("#new-loan-button");
    this.booksListElement = document.querySelector("#dashboard-books-list");
    this.clientsListElement = document.querySelector("#dashboard-clients-list");
    this.newLoanForm = document.querySelector("#new-loan-form");
    // Adiar empréstimo
    this.delayLoanForm = document.querySelector("#delay-loan-form");

    // Filtros
    document.querySelector("#show-completed-loans").checked = false
    this.filters = {
      'showCompleted': false
    }

    // Execução
    this.initialize();
  }

  // Renderizando as listas
  renderBooksList(booksData) {
    if (booksData.message == 'Nenhum livro encontrado') return;

    this.booksListElement.innerHTML = "";

    booksData.body.forEach(book => {
      const bookHtml = `<option value="${book.idLivro}">${book.nome}</option>`;

      this.booksListElement.insertAdjacentHTML("beforeend", bookHtml);
    });
  }

  renderClientsList(clientsData) {
    if (clientsData.message == 'Nenhum cliente encontrado.') return;

    this.clientsListElement.innerHTML = "";

    clientsData.body.forEach(client => {
      const clientHtml = `<option value="${client.clienteId}">${client.nome} - ${client.cpf}</option>`;

      this.clientsListElement.insertAdjacentHTML("beforeend", clientHtml);
    });
  }

  // Renderizando empréstimos e atualizando os cards
  renderAllLoans(loansData){
    this.loansTableElement.innerHTML = '';

    loansData.body.forEach(loan => {
      if (!this.filters.showCompleted && (loan.status == 'FINALIZADO' || loan.status == 'FINALIZADO_ATRASADO' )) return
      const actionsHtml = ``

      const loanHtml = `
        <tr data-id="${loan.idEmprestimo}" class="">
          <td>${loan.livro.nome}</td>
          <td>${loan.cliente.nome}</td>
          <td>${loan.cliente.cpf}</td>
          <td>${loan.ativo == true ? "Ativo" : "Inativo"}</td>
          <td>${loan.dataSolicitacaoEmprestimo}</td>
          <td>${loan.dataColeta ? loan.dataColeta : '-'}</td>
          <td class="${loan.dataEstendidaDevolucao ? 'dashboard__loan-extended' : ''}">${loan.dataEstendidaDevolucao || loan.dataPrevistaDevolucao}</td>
          <td class="${loan.dataCancelamento == null ? 'text--center' : ''}">${loan.dataCancelamento ? loan.dataCancelamento : ''}</td>
          <td>${loan.status}</td>
          <td class="dashboard__loan-actions-cell">
            <button class="dashboard__action-button dashboard__action-open-dropdown">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
            </button>
            <div class="dashboard__actions-dropdown dashboard__actions-dropdown--hidden">
              <div class="dashboard__dropdown-action dashboard__dropdown-action--confirm-pickup">Confirmar coleta</div>
              <div class="dashboard__dropdown-action dashboard__dropdown-action--cancel-loan">Finalizar</div>
              <div class="dashboard__dropdown-action dashboard__dropdown-action--delay-loan">Adiar</div>
            </div>
          </td>
        </tr>
      `

      this.loansTableElement.insertAdjacentHTML("beforeend", loanHtml);
    });
  }

  updateCards(loansData){
    // Empréstimos totais
    document.querySelector(".dashboard__card--loans-amount").textContent = loansData.body.length

    // Empréstimos ativos
    document.querySelector(".dashboard__card--active-loans-amount").textContent = loansData.body.filter(loan => (loan.status == 'FINALIZADO' || loan.status == 'FINALIZADO_ATRASADO' )).length
  }

  // Renderizar tudo
  async renderPage(){
    await this.getAllEmprestimos().then(json => {
      if (json.message == 'Nenhum empréstimo encontrado.') {
        this.displayMessage(json);
        return;
      }
      this.renderAllLoans(json)
      this.updateCards(json)
    })
    await this.getAllBooks().then(json => this.renderBooksList(json))
    await this.getAllClients().then(json => this.renderClientsList(json))
  }

  // Novo empréstimo
  async submitNewLoanForm(event){
    event.preventDefault();

    const body = this.formDataObject(this.newLoanForm);
    const response = await this.createEmprestimo(body);

    this.displayMessage(response);
    this.cleanForm(this.newLoanForm);
    this.hidePopUp();
    this.renderPage();
  }

  // Adiar empréstimo
  async submitDelayLoanForm(event){
    event.preventDefault();

    const body = this.formDataObject(this.delayLoanForm);
    const response = await this.postAdiarEmprestimo(this.delayLoanForm.dataset.id, body.dataEstendidaDevolucao)

    this.displayMessage(response);
    this.cleanForm(this.delayLoanForm);
    this.hidePopUp();
    this.renderPage();
  }

  // Ações
  async handleLoanActions(event){
    const target = event.target.closest(".dashboard__dropdown-action") || event.target.closest(".dashboard__action-open-dropdown");
    if (target == null) return;
    const rowId = target.closest("tr").dataset.id;
    let json;

    // Abrir menu de ações
    if (target.classList.contains('dashboard__action-open-dropdown')) {
      document.querySelectorAll(".dashboard__actions-dropdown").forEach(menu => menu.classList.add("dashboard__actions-dropdown--hidden"));
      target.closest('td').querySelector(".dashboard__actions-dropdown").classList.remove("dashboard__actions-dropdown--hidden");
    }

    // Confirmar coleta
    if (target.classList.contains('dashboard__dropdown-action--confirm-pickup')) {
      json = await this.confirmEmprestimoColeta(rowId);

      this.displayMessage(json);
      this.renderPage();
    }

    // Finalizar empréstimo
    if (target.classList.contains('dashboard__dropdown-action--cancel-loan')){
      json = await this.finalizarEmprestimo(rowId);

      this.displayMessage(json);
      this.renderPage();
    }

    // Adiar empréstimo
    if (target.classList.contains('dashboard__dropdown-action--delay-loan')){
      const response = await this.getEmprestimoById(rowId);

      document.querySelector("#delay-loan-form__current-date").textContent = response.body.dataPrevistaDevolucao;

      this.delayLoanForm.dataset.id = rowId;

      this.showPopUp('#delay-loan-form-popup');
    }
  }

  async handleShowCompletedFilter(event){
    this.filters.showCompleted = event.target.checked ? true : false

    await this.renderPage()
  }

  // Execução
  setupLoansListeners(){
    this.loansTableElement.addEventListener("click", event => this.handleLoanActions(event))
    // Novo empréstimo
    this.newLoanButton.addEventListener("click", () => this.showPopUp("#new-loan-form-popup"))
    this.newLoanForm.addEventListener("submit", event => this.submitNewLoanForm(event))
    // Adiar empréstimo
    this.delayLoanForm.addEventListener("submit", event => this.submitDelayLoanForm(event))
    // Filtros
    document.querySelector("#show-completed-loans").addEventListener('change', event => this.handleShowCompletedFilter(event))
  }

  async initialize(){
    await this.renderPage();
    this.setupLoansListeners();
  }
}

const dashboardEmprestimo = new DashboardEmprestimo();
