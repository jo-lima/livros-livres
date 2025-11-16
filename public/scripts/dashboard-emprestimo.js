import Base from "./base.js";

class DashboardEmprestimo extends Base {
  constructor(){
    super()
    // Elementos
    this.loansTableElement = document.querySelector(".dashboard__table--loans tbody");
    // Novo empréstimo
    this.newLoanButton = document.querySelector("#new-loan-button")
    this.booksListElement = document.querySelector("#dashboard-books-list")
    this.clientsListElement = document.querySelector("#dashboard-clients-list")
    this.newLoanForm = document.querySelector("#new-loan-form")

    // Execução
    this.initialize()
  }

  // Renderizando as listas
  renderBooksList(booksData) {
    if (booksData.statusCode != 200) return;

    this.booksListElement.innerHTML = "";

    booksData.body.forEach(book => {
      const bookHtml = `<option value="${book.idLivro}">${book.nome}</option>`;

      this.booksListElement.insertAdjacentHTML("beforeend", bookHtml);
    });
  }

  renderClientsList(clientsData) {
    if (clientsData.statusCode != 200) return;

    this.clientsListElement.innerHTML = "";

    clientsData.body.forEach(client => {
      const clientHtml = `<option value="${client.clienteId}">${client.nome} - ${client.cpf}</option>`;

      this.clientsListElement.insertAdjacentHTML("beforeend", clientHtml);
    });
  }

  renderAllLoans(loansData){
    this.loansTableElement.innerHTML = '';

    loansData.body.forEach(loan => {
      const actionsHtml = ``

      const loanHtml = `
        <tr data-id="${loan.idEmprestimo}" class="${loan.status == 'FINALIZADO' ? 'dashboard__loan--inactive' : ''}">
          <td>${loan.livro.nome}</td>
          <td>${loan.cliente.nome}</td>
          <td>${loan.cliente.cpf}</td>
          <td>${loan.ativo == true ? "Ativo" : "Inativo"}</td>
          <td>${loan.dataSolicitacaoEmprestimo}</td>
          <td>${loan.dataColeta}</td>
          <td>${loan.dataPrevistaDevolucao}</td>
          <td class="${loan.dataCancelamento == null ? 'text--center' : ''}">${loan.dataCancelamento == null ? "-" : loan.dataCancelamento}</td>
          <td>${loan.status}</td>
          <td class="dashboard__loan-actions-cell">
            <button class="dashboard__action-button">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
            </button>
            <div class="dashboard__actions-dropdown dashboard__actions-dropdown--hidden">
              <div class="dashboard__action dashboard__action--confirm-pickup">Confirmar coleta</div>
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
  }

  // Ações
  async submitNewLoanForm(event){
    event.preventDefault();

    const body = this.formDataObject(this.newLoanForm);
    const response = await this.createEmprestimo(body);

    this.displayMessage(response);
    this.cleanForm(this.newLoanForm);
    this.hidePopUp();
    this.lintRenderLoans();
  }

  async lintRenderLoans(){
    this.getAllEmprestimos().then(json => {
      this.renderAllLoans(json)
      this.updateCards(json)
    })
  }

  handleLoanActions(event){
    const target = event.target.closest(".dashboard__action-button");
    if (target == null) return;
    const rowId = target.closest("tr").dataset.id;
    let json;

    // Abrir menu de ações
    const parentCell = target.closest('td')
    document.querySelectorAll(".dashboard__actions-dropdown").forEach(menu => menu.classList.add("dashboard__actions-dropdown--hidden"))
    parentCell.querySelector(".dashboard__actions-dropdown").classList.remove("dashboard__actions-dropdown--hidden")
  }

  setupLoansListeners(){
    this.loansTableElement.addEventListener("click", event => this.handleLoanActions(event))
    // Novo empréstimo
    this.newLoanButton.addEventListener("click", () => this.showPopUp("#new-loan-form-popup"))
    this.newLoanForm.addEventListener("submit", event => this.submitNewLoanForm(event))
  }

  async initialize(){
    await this.lintRenderLoans();
    await this.getAllBooks().then(json => this.renderBooksList(json))
    await this.getAllClients().then(json => this.renderClientsList(json))
    this.setupLoansListeners();
  }
}

const dashboardEmprestimo = new DashboardEmprestimo();
