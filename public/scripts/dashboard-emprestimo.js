import Base from "./base.js";

class DashboardEmprestimo extends Base {
  constructor(){
    super()
    // Elementos
    this.loansTableElement = document.querySelector(".dashboard__table--loans tbody");

    this.initialize()
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
    document.querySelector(".dashboard__card--loans-amount").textContent = loansData.body.length
  }

  async initialize(){
    this.getAllEmprestimos().then(json => {
      this.renderAllLoans(json)
      this.updateCards(json)
    })
  }
}

const dashboardEmprestimo = new DashboardEmprestimo();
