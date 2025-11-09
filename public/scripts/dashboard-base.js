// Pop-up
const popUpElement = document.querySelector(".dashboard__popup-container");

const hidePopUp = () => popUpElement.classList.add("hidden");
const showPopUp = () => popUpElement.classList.remove("hidden");

// Fechar pop-up no botão 'X'
document
  .querySelector(".dashboard__popup-close")
  .addEventListener("click", hidePopUp);

// Fechar pop-up no espaço de fora
popUpElement.addEventListener("click", function (event) {
  if (!event.target.classList.contains("dashboard__popup-container")) return;
  hidePopUp();
});

// Mensagem de status
const messageElement = document.querySelector(".dashboard__message");
let messageTimer;

function displayMessage(json) {
  messageElement.textContent = json.message;
  messageElement.style.backgroundColor =
    json.statusCode == 200 ? "var(--text-color--green--regular)" : "red";

  if (messageElement.classList.contains("dashboard__message--hidden")) {
    messageElement.classList.remove("dashboard__message--hidden");
  }

  // Para o timeout anterior
  messageTimer && clearTimeout(messageTimer);

  messageTimer = setTimeout(() => {
    messageElement.classList.add("dashboard__message--hidden");
  }, 4000);
}
