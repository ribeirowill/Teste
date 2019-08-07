var Cofipa = Cofipa || {};

Cofipa.ComboDivisao2 = (function() {
	
	function ComboDivisao2() {
		this.combo = $('#divisao');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboDivisao2.prototype.iniciar = function() {
		this.combo.on('change', onDivisaoAlterada.bind(this));
	}
	
	function onDivisaoAlterada() {
		console.log('divisao', this.combo.val());
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboDivisao2;
	
}());

Cofipa.ComboSecao = (function() {
	
	function ComboSecao(comboDivisao2) {
		this.comboDivisao2 = comboDivisao2;
		this.combo = $('#secao');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenSecaoSelecionado = $('#inputHiddenSecaoSelecionado');
	}
	
	ComboSecao.prototype.iniciar = function() {
		reset.call(this);
		this.comboDivisao2.on('alterado', onDivisaoAlterada.bind(this));
		var codigoDivisao = this.comboDivisao2.combo.val();
		inicializarSecoes.call(this, codigoDivisao);
	}
	
	function onDivisaoAlterada(evento, codigoDivisao) {
	   console.log('codigo do divisao no combo secao', codigoDivisao);
		this.inputHiddenSecaoSelecionado.val('');
		inicializarSecoes.call(this, codigoDivisao);
	}
	
	function inicializarSecoes(codigoDivisao) {
		if (codigoDivisao) {
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'divisao': codigoDivisao }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarSecoesFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarSecoesFinalizado(secoes) {
		var options = [];
		secoes.forEach(function(secao) {
			console.log('secao', secao)
			options.push('<option value="' + secao.codigo + '">' + secao.nome + '</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoSecaoSelecionado = this.inputHiddenSecaoSelecionado.val();
		if (codigoSecaoSelecionado) {
			this.combo.val(codigoSecaoSelecionado);
		}
	}
	
	function reset() {
		this.combo.html('<option value="">Selecione uma secao</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboSecao;
	
}());

$(function() {
	
	var comboDivisao2 = new Cofipa.ComboDivisao2();
	comboDivisao2.iniciar();
	
	var comboSecao = new Cofipa.ComboSecao(comboDivisao2);
	comboSecao.iniciar();
	
});