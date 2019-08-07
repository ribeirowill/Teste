package com.pc.cofipa.repository.helper.materialInformatica;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.pc.cofipa.model.MaterialInformatica;

import com.pc.cofipa.repository.filter.MaterialInformaticaFilter;
import com.pc.cofipa.repository.paginacao.PaginacaoUtil;

public class MateriaisInformaticaImpl implements MateriaisInformaticaQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<MaterialInformatica> filtrar(MaterialInformaticaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MaterialInformatica.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(MaterialInformaticaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MaterialInformatica.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(MaterialInformaticaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getDescricao())) {
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
				criteria.addOrder(Order.asc("descricao"));
			}
			
			if(isUnidadePresent(filtro)){
				   criteria.add(Restrictions.eq("unidade", filtro.getUnidade()));
			   }
			   
			   if(isFornecedorPresent(filtro)){
				   criteria.add(Restrictions.eq("fornecedor", filtro.getFornecedor()));
			   }
			   
			   if(isItemMaterialInformaticaPresent(filtro)) {
				   criteria.add(Restrictions.eq("itemMaterialInformatica", filtro.getItemMaterialInformatica()));
			   }
			   
			   if(filtro.getValorUnitario() != null) {
				   criteria.add(Restrictions.ge("valorUnitario", filtro.getValorUnitario()));
			   }
			   
			   if(filtro.getValorTotal() != null) {
				   criteria.add(Restrictions.le("valorTotal", filtro.getValorTotal()));
			   }
		}
	}

	private boolean isItemMaterialInformaticaPresent(MaterialInformaticaFilter filtro) {
	
		return filtro.getItemMaterialInformatica() != null && filtro.getItemMaterialInformatica().getCodigo() != null;
	}

	private boolean isFornecedorPresent(MaterialInformaticaFilter filtro) {
		
		return filtro.getFornecedor() != null && filtro.getFornecedor().getCodigo() != null;
	}

	private boolean isUnidadePresent(MaterialInformaticaFilter filtro) {
		
		return filtro.getUnidade() != null && filtro.getUnidade().getCodigo() != null;
	}

}
