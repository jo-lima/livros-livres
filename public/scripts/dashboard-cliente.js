import DashboardBase from "../scripts/dashboard-base.js";

class DashboardCliente extends DashboardBase {
  constructor() {
    this.clientsTableElement = document.querySelector(
      ".dashboard__table--clients"
    );
  }

  renderAllClients(clientData) {
    this.clientsTableElement.innerHTML = "";

    clientData.body.forEach((client) => {
      //
    });
  }
}

const dashboardLivro = new DashboardCliente();
