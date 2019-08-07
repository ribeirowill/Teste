Cofipa = Cofipa || {};

Cofipa.Autocomplete = (function() {
	
	function Autocomplete() {
		this.descricaoOuCodigoInput = $('.js-descricao-produto-input');
		var htmlTemplateAutocomplete = $('#template-autocomplete-produto').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(descricaoOuCodigo) {
				return this.descricaoOuCodigoInput.data('url') + '?descricaoOuCodigo=' + descricaoOuCodigo;
			}.bind(this),
			getValue: 'descricao',
			minCharNumber: 2,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: template.bind(this)
			},
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		};
		
		this.descricaoOuCodigoInput.easyAutocomplete(options);
	}
	
	function onItemSelecionado() {
		this.emitter.trigger('item-selecionado',this.descricaoOuCodigoInput.getSelectedItemData());
		this.descricaoOuCodigoInput.val('');
		this.descricaoOuCodigoInput.focus();
	}
	
	function template(descricao, produto) {
		produto.valorFormatado = Cofipa.formatarMoeda(produto.valorUnitario);
		return this.template(produto);
	}
	
	return Autocomplete
	
}());



