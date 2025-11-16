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
      const loanHtml = `
        <tr data-id="${loan.idEmprestimo}">
          <td>${loan.livro.nome}</td>
          <td>${loan.cliente.nome}</td>
          <td>${loan.cliente.cpf}</td>
          <td>${loan.ativo == true ? "Ativo" : "Inativo"}</td>
          <td>${loan.dataSolicitacaoEmprestimo}</td>
          <td>${loan.dataColeta}</td>
          <td>${loan.dataPrevistaDevolucao }</td>
          <td class="${loan.dataCancelamento == null ? 'text--center' : ''}">${loan.dataCancelamento == null ? "-" : loan.dataCancelamento}</td>
          <td>${loan.status}</td>
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

  setupLoansListeners(){
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
