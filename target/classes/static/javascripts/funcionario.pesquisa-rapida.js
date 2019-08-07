Cofipa = Cofipa || {};

Cofipa.PesquisaRapidaFuncionario = (function() {
	
	function PesquisaRapidaFuncionario() {
		this.pesquisaRapidaFuncionariosModal = $('#pesquisaRapidaFuncionarios');
		this.nomeOuRgInput = $('#nomeFuncionarioModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-funcionarios-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaFuncionarios');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-funcionario').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaFuncionario.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaFuncionariosModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		this.nomeOuRgInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaFuncionariosModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nomeOuRg: this.nomeOuRgInput.val()
				 
			}, 
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaFuncionarioPesquisaRapida = new Cofipa.TabelaFuncionarioPesquisaRapida(this.pesquisaRapidaFuncionariosModal);
		tabelaFuncionarioPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaFuncionario;
	
}());

Cofipa.TabelaFuncionarioPesquisaRapida = (function() {
	
	function TabelaFuncionarioPesquisaRapida(modal) {
		this.modalFuncionario = modal;
		this.funcionario = $('.js-funcionario-pesquisa-rapida');
	}
	
	TabelaFuncionarioPesquisaRapida.prototype.iniciar = function() {
		this.funcionario.on('click', onFuncionarioSelecionado.bind(this));
	}
	
	function onFuncionarioSelecionado(evento) {
		this.modalFuncionario.modal('hide');
		
		var funcionarioSelecionado = $(evento.currentTarget);
		$('#nomeFuncionario').val(funcionarioSelecionado.data('nome'));
		$('#codigoFuncionario').val(funcionarioSelecionado.data('codigo'));
	}
	
	return TabelaFuncionarioPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaFuncionario = new Cofipa.PesquisaRapidaFuncionario();
	pesquisaRapidaFuncionario.iniciar();
});