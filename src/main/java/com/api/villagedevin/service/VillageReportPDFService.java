package com.api.villagedevin.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.villagedevin.model.transport.VillageReportDTO;

@Service
public class VillageReportPDFService {
	
	private final Logger LOG = LogManager.getLogger(VillageReportPDFService.class);

	private CitizenService citizenService;

	private RabbitTemplate rabbitTemplate;

	public VillageReportPDFService(RabbitTemplate rabbitTemplate, CitizenService citizenService) {
		this.rabbitTemplate = rabbitTemplate;
		this.citizenService = citizenService;
	}

	public VillageReportDTO create() throws Exception {
		this.LOG.info("Gerando relatório da vila...");
		try {
			VillageReportDTO report = citizenService.getReport();
			this.LOG.info("Relatório da vila gerado com sucesso!");
			rabbitTemplate.convertAndSend("devin.report", report);
			return report;
		} catch (Exception e) {
			this.LOG.info("Erro ao gerar relatório");
			System.out.println(e);
			e.printStackTrace();
			throw e ;
		}

		

	}

}
