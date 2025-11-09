// Pop-up
const popUpOverlay = document.querySelector(".dashboard__popup-overlay");

function hidePopUp() {
  const popUps = document.querySelectorAll(".dashboard__popup");
  popUps.forEach((popUp) => popUp.classList.add("hidden"));
  popUpOverlay.classList.add("hidden");
}
function showPopUp(popUpClass) {
  document.querySelector(popUpClass).classList.remove("hidden");
  popUpOverlay.classList.remove("hidden");
}

// Fechar pop-up no botão 'X'
document
  .querySelectorAll(".dashboard__popup-close")
  .forEach((button) => button.addEventListener("click", hidePopUp));

// Fechar pop-up no espaço de fora
popUpOverlay.addEventListener("click", function (event) {
  if (!event.target.classList.contains("dashboard__popup-overlay")) return;
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
