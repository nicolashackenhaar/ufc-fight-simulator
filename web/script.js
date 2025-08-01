function simular() {
  const nome1 = document.getElementById('lutador1').value;
  const nome2 = document.getElementById('lutador2').value;

  if (nome1 === nome2) {
    document.getElementById('resultado').innerHTML = `<p style="color:yellow;">Escolha dois lutadores diferentes!</p>`;
    return;
  }

  const vencedor = Math.random() > 0.5 ? nome1 : nome2;

  document.getElementById('resultado').innerHTML = `
    <h2>Lutadores:</h2>
    <p><strong>${nome1}</strong> vs <strong>${nome2}</strong></p>
    <h2>Vencedor:</h2>
    <p style="font-size: 24px;">🏆 ${vencedor}</p>
  `;
}
