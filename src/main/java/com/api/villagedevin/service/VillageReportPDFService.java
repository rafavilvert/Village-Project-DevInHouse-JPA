package com.api.villagedevin.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.villagedevin.model.transport.AMQPMessageWrapper;
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

	private void send(AMQPMessageWrapper amqpMessageWrapper, String routingKey) {
		rabbitTemplate.convertAndSend(routingKey, amqpMessageWrapper);
	}
	
	public void sendToQueue(AMQPMessageWrapper amqpMessageWrapper) {
		this.send(amqpMessageWrapper, "devin.report");;
	}

	public void sendToDLX(AMQPMessageWrapper amqpMessageWrapper) {
		this.send(amqpMessageWrapper, "devin.report.dlx");

	}

	public VillageReportDTO create() throws Exception {
		this.LOG.info("Gerando relatório da vila...");
		try {
			VillageReportDTO report = citizenService.getReport();
			this.LOG.info("Relatório da vila gerado com sucesso!");
			AMQPMessageWrapper villageReport = new AMQPMessageWrapper(report);
			String email = UserService.authenticated().getUsername();
			villageReport.setEmail(email);
			this.sendToQueue(villageReport);
			return report;
		} catch (Exception e) {
			this.LOG.info("Erro ao gerar relatório");
			System.out.println(e);
			e.printStackTrace();
			throw e;
		}

	}

}
