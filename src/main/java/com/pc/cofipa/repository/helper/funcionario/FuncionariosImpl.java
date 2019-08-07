package com.pc.cofipa.repository.helper.funcionario;

import java.util.List;

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

import com.pc.cofipa.dto.FuncionarioDTO;

import com.pc.cofipa.model.Funcionario;

import com.pc.cofipa.repository.filter.FuncionarioFilter;
import com.pc.cofipa.repository.paginacao.PaginacaoUtil;

public class FuncionariosImpl implements FuncionariosQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Funcionario> filtrar(FuncionarioFilter filtro, Pageable pageable) {
          Criteria criteria = manager.unwrap(Session.class).createCriteria(Funcionario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(FuncionarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Funcionario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(FuncionarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
				criteria.addOrder(Order.desc("nome"));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail()));
			}
			
			if (!StringUtils.isEmpty(filtro.getRg())) {
				criteria.add(Restrictions.ilike("rg", filtro.getRg()));
			}
			
			if (!StringUtils.isEmpty(filtro.getCarreira())) {
				criteria.add(Restrictions.ilike("carreira", filtro.getCarreira()));
			}
		
		}
	}

	@Override
	public List<FuncionarioDTO> porNomeOuRg(String nomeOuRg) {
		String jpql = "select new com.pc.cofipa.dto.FuncionarioDTO(codigo, nome, rg) "
				+ "from Funcionario where lower(nome) like lower(:nomeOuRg) or lower(rg) like lower(:nomeOuRg)";
		List<FuncionarioDTO> funcionariosFiltrados = manager.createQuery(jpql, FuncionarioDTO.class)
				.setParameter("nomeOuRg", "%" + nomeOuRg + "%")
				.getResultList();
		return funcionariosFiltrados;
	}

}
