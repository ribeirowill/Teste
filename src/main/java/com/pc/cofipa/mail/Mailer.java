/*package com.pc.cofipa.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.pc.cofipa.model.ItemSaida;
import com.pc.cofipa.model.Produto;
import com.pc.cofipa.model.Saida;
import com.pc.cofipa.storage.FotoStorage;



@Component
public class Mailer {
	
	private static Logger logger = LoggerFactory.getLogger(Mailer.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Async
	public void enviar(Saida saida) {
		
		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("saida", saida);
		context.setVariable("logo", "logo");
		
		Map<String, String> fotos = new HashMap<>();
		boolean adicionarMockProduto = false;
		for (ItemSaida item : saida.getItens()) {
			Produto produto = item.getProduto();
			if (produto.temFoto()) {
				String cid = "foto-" + produto.getCodigo();
				context.setVariable(cid, cid);
				
				fotos.put(cid, produto.getFoto() + "|" + produto.getContentType());
			} else {
				adicionarMockProduto = true;
				context.setVariable("mockProduto", "mockProduto");
			}
		}
		
		try {
			String email = thymeleaf.process("mail/ResumoSaida", context);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("ribeiro3001@hotmail.com");
			helper.setTo(saida.getFuncionario().getEmail());
			helper.setSubject(String.format("Cofipa - Saida n° %d", saida.getCodigo()));
			helper.setText(email, true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			
			if (adicionarMockProduto) {
				helper.addInline("mockProduto", new ClassPathResource("static/images/produto-mock.png"));
			}
			
			for (String cid : fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				byte[] arrayFoto = fotoStorage.recuperarThumbnail(foto);
				helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
			}
		
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
	}
	
}
*/