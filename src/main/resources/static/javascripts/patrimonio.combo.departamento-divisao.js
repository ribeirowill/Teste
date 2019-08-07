var Cofipa = Cofipa || {};

Cofipa.ComboDepartamento = (function() {
	
	function ComboDepartamento() {
		this.combo = $('#departamento');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboDepartamento.prototype.iniciar = function() {
		this.combo.on('change', onDepartamentoAlterado.bind(this));
	}
	
	function onDepartamentoAlterado() {
		console.log('departamento', this.combo.val());
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboDepartamento;
	
}());

Cofipa.ComboDivisao = (function() {
	
	function ComboDivisao(comboDepartamento) {
		this.comboDepartamento = comboDepartamento;
		this.combo = $('#divisao');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenDivisaoSelecionado = $('#inputHiddenDivisaoSelecionado');
	}
	
	ComboDivisao.prototype.iniciar = function() {
		reset.call(this);
		this.comboDepartamento.on('alterado', onDepartamentoAlterado.bind(this));
		var codigoDepartamento = this.comboDepartamento.combo.val();
		inicializarDivisoes.call(this, codigoDepartamento);
	}
	
	function onDepartamentoAlterado(evento, codigoDepartamento) {
	   console.log('codigo do departamento no combo divisao', codigoDepartamento);
		this.inputHiddenDivisaoSelecionado.val('');
		inicializarDivisoes.call(this, codigoDepartamento);
	}
	
	function inicializarDivisoes(codigoDepartamento) {
		if (codigoDepartamento) {
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'departamento': codigoDepartamento }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarDivisoesFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarDivisoesFinalizado(divisoes) {
		var options = [];
		divisoes.forEach(function(divisao) {
			console.log('divisao', divisao)
			options.push('<option value="' + divisao.codigo + '">' + divisao.nome + '</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoDivisaoSelecionado = this.inputHiddenDivisaoSelecionado.val();
		if (codigoDivisaoSelecionado) {
			this.combo.val(codigoDivisaoSelecionado);
		}
	}
	
	function reset() {
		this.combo.html('<option value="">Selecione uma divisao</option>');
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
	
	return ComboDivisao;
	
}());
		

$(function() {
	
	var comboDepartamento = new Cofipa.ComboDepartamento();
	comboDepartamento.iniciar();
	
	var comboDivisao = new Cofipa.ComboDivisao(comboDepartamento);
	comboDivisao.iniciar();


});	

