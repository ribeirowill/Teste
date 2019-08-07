Cofipa.Saida = (function() {
	
	function Saida(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');

	}
	
	Saida.prototype.iniciar = function() {
	   this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		
	
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		var v = valorTotalItens == null ? 0 : valorTotalItens;
		this.valorTotalBox.html(Cofipa.formatarMoeda(v));
	}
	
	return Saida;
	
}());

$(function() {
	
	var autocomplete = new Cofipa.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Cofipa.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var saida = new Cofipa.Saida(tabelaItens);
	saida.iniciar();
	
});