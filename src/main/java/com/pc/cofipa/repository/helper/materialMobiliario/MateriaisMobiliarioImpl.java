package com.pc.cofipa.repository.helper.materialMobiliario;

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

import com.pc.cofipa.model.MaterialMobiliario;
import com.pc.cofipa.repository.filter.MaterialMobiliarioFilter;
import com.pc.cofipa.repository.paginacao.PaginacaoUtil;

public class MateriaisMobiliarioImpl implements MateriaisMobiliarioQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<MaterialMobiliario> filtrar(MaterialMobiliarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MaterialMobiliario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(MaterialMobiliarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MaterialMobiliario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(MaterialMobiliarioFilter filtro, Criteria criteria) {
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
			   
			   if(isItemMaterialMobiliarioPresent(filtro)) {
				   criteria.add(Restrictions.eq("itemMaterialMobiliario", filtro.getItemMaterialMobiliario()));
			   }
			   
			   if(filtro.getValorUnitario() != null) {
				   criteria.add(Restrictions.ge("valorUnitario", filtro.getValorUnitario()));
			   }
			   
			   if(filtro.getValorTotal() != null) {
				   criteria.add(Restrictions.le("valorTotal", filtro.getValorTotal()));
			   }
		}
	}

	private boolean isItemMaterialMobiliarioPresent(MaterialMobiliarioFilter filtro) {
	
		return filtro.getItemMaterialMobiliario() != null && filtro.getItemMaterialMobiliario().getCodigo() != null;
	}

	private boolean isFornecedorPresent(MaterialMobiliarioFilter filtro) {
		
		return filtro.getFornecedor() != null && filtro.getFornecedor().getCodigo() != null;
	}

	private boolean isUnidadePresent(MaterialMobiliarioFilter filtro) {
		
		return filtro.getUnidade() != null && filtro.getUnidade().getCodigo() != null;
	}

}
