var Cofipa = Cofipa || {};

Cofipa.ComboDepartamento = (function() {
	
	      function ComboDepartamento() {
		      this.combo = $('#departamento'); // pego o id do campo departamento
		      this.emitter = $({});
		      this.on = this.emitter.on.bind(this.emitter);
	}
    
	     ComboDepartamento.prototype.iniciar = function(){
		 this.combo.on('change', onDepartamentoAlterado.bind(this));
	}
	
	     function onDepartamentoAlterado() {
		    console.log('departamento selecionado', this.combo.val());
		   this.emitter.trigger('alterado', this.combo.val()); // aqui pego o
															// codigo do departamento
															// alterado
	}
	
	  return ComboDepartamento;
	
}());

Cofipa.ComboDivisao = (function() {
	  
	     function ComboDivisao(comboDepartamento) {
	    	 this.comboDepartamento = comboDepartamento;
	    	  this.combo = $('#divisao');
	    	  this.imgLoading = $('.js-img-loading');
		      this.inputHiddenDivisaoSelecionada = $('#inputHiddenDivisaoSelecionada');
			  this.emitter = $({});
			  this.on = this.emitter.on.bind(this.emitter);
	       
		   
	        
	   
	}
	
	    ComboDivisao.prototype.iniciar = function() {
	    	 reset.call(this);
	    	 this.combo.on('change', onDivisaoAlterada.bind(this));
	         this.comboDepartamento.on('alterado', onDepartamentoAlterado.bind(this));
	         var codigoDepartamento = this.comboDepartamento.combo.val();
	 		 inicializarDivisoes.call(this, codigoDepartamento);
	}
	    
	
	 
	    function onDepartamentoAlterado(evento, codigoDepartamento) {
		      console.log('codigo do departamento na combo Divisao', codigoDepartamento);
	    	var codigoDepartamento = this.comboDepartamento.combo.val();
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
				console.log('divisoes', divisao)
				options.push('<option value="' + divisao.codigo + '">' + divisao.nome + ' </option>');
			});
			
			this.combo.html(options.join(''));
			this.combo.removeAttr('disabled');
			var codigoDivisaoSelecionada = this.inputHiddenDivisaoSelecionada.val();
			if (codigoDivisaoSelecionada) {
				this.combo.val(codigoDivisaoSelecionada);
			}
		}
	    
	    function reset() {
			this.combo.html('<option value="">Selecione a divisao</option>');
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
		
	    function onDivisaoAlterada() {
		    console.log('divisao selecionado', this.combo.val());
		   this.emitter.trigger('alterado', this.combo.val()); // aqui pego o
															// codigo do estado
															// alterado
	}
	    
		
		return ComboDivisao;
		
	}());

Cofipa.ComboSecao = (function() {
	
	  function ComboSecao(comboDivisao) {
		    this.comboDivisao = comboDivisao;
		    this.combo = $('#secao');
		    this.imgLoading = $('.js-img-loading');
		    this.inputHiddenSecaoSelecionada = $('#inputHiddenSecaoSelecionada');
		 //   this.emitter = $({});
		//    this.on = this.emitter.on.bind(this.emitter);
		    
	}
	
	    ComboSecao.prototype.iniciar = function() {
	    	reset.call(this);
	    //	this.combo.on('change', onSecaoAlterada.bind(this));
			this.comboDivisao.on('alterado', onDivisaoAlterada.bind(this));
			var codigoDivisao = this.comboDivisao.combo.val();
		    inicializarSecoes.call(this, codigoDivisao);
	}
	    
	    function onDivisaoAlterada(evento, codigoDivisao) {
	    	console.log('codigo da divisao no combo secao', codigoDivisao);
	    	this.inputHiddenSecaoSelecionada.val('');
			inicializarSecoes.call(this, codigoDivisao);
		}
	    
	 //   function onSecaoAlterada(evento, codigoSecao) {
	//		 console.log('codigo da seção no combo divisao', codigoSecao);
	//		this.inputHiddenSecaoSelecionada.val('');
	//		inicializarSecoes.call(this, codigoSecao);
		
	//	}
	    
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
				console.log('secoes', secao)
				options.push('<option value="' + secao.codigo + '">' + secao.nome + ' </option>');
			});
			
			this.combo.html(options.join(''));
			this.combo.removeAttr('disabled');
			var codigoSecaoSelecionada = this.inputHiddenSecaoSelecionada.val();
			if (codigoSecaoSelecionada) {
			this.combo.val(codigoSecaoSelecionada);
			
		 // $('#codigoSecaoSelecionada').val('');
		//	this.inputHiddenSecaoSelecionada = $('#inputHiddenSecaoSelecionada');
				
			}
		}
	
	    function reset() {
		//	this.combo.html('<option value="" th:text="*{secao} != null ? *{secao.nome}" >Selecione a secao</option>');
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
		
	//	function onSecaoAlterada() {
	//		console.log('secao selecionada', this.combo.val());
	//		this.emitter.trigger('alterada', this.combo.val());
			
	//	}
		
		return ComboSecao;
		
	}());	

$(function() {
	
	var comboDepartamento = new Cofipa.ComboDepartamento();
	comboDepartamento.iniciar();
	
	var comboDivisao = new Cofipa.ComboDivisao(comboDepartamento);
	comboDivisao.iniciar();
	
	var comboSecao = new Cofipa.ComboSecao(comboDivisao);
	comboSecao.iniciar();

});	

