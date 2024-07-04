$(document).ready(function() {
	//identificazione di tutti i button + 

	const plusButtons = document.querySelectorAll(".plus");
	plusButtons.forEach(function(button) {
		button.addEventListener("click", function() {
			const idLibro = button.value; //recupero l'id del libro legato al valore del button
			const cellQuantita = document.getElementById(`quantity${idLibro}`);
			let quantitaAttuale = cellQuantita.innerText; //recupero la quantità attualmente presente
			modificaQuantità(idLibro, ++quantitaAttuale, cellQuantita);
		});
	});
	
	//identificazione di tutti i button -
	const minusButtons = document.querySelectorAll(".minus");
	minusButtons.forEach(function(button) {
		button.addEventListener("click", function() {
			const idLibro = button.value; //recupero l'id del libro legato al valore del button
			const cellQuantita = document.getElementById(`quantity${idLibro}`);
			let quantitaAttuale = cellQuantita.innerText; //recupero la quantità attualmente presente
			modificaQuantità(idLibro, --quantitaAttuale, cellQuantita);
		});
	});

	//funzione per inviare richiesta di modifica quantità
	function modificaQuantità(idLibro, quantitaRichiesta, cella) {
		$.post(
			"/riservatautente/modifica",
			{
				id:idLibro,
				quantita:quantitaRichiesta
			},
			function(response){
				if(response != "no"){
					cella.innerText = quantitaRichiesta;
					const totaleCarrello = document.getElementById("totaleCarrello");
					totaleCarrello.innerText = `Totale Carrello Euro ${response}`;
				}
			}
		)
	}
})