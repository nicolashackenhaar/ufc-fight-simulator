// web/script.js
function escolher(nome) {
  const div = document.getElementById('resultado');
  div.innerHTML = `<h2>Você escolheu: ${nome}</h2>`;
}
