package com.api.villagedevin.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.api.villagedevin.model.transport.AMQPMessageWrapper;
import com.api.villagedevin.service.EmailService;
import com.api.villagedevin.service.VillageReportPDFService;

@Component
public class VillageReportPDFConsumer {

	private VillageReportPDFService villageReportPDFService;
	private EmailService emailService;

	public VillageReportPDFConsumer(VillageReportPDFService villageReportPDFService, EmailService emailService) {
		this.villageReportPDFService = villageReportPDFService;
		this.emailService = emailService;
	}

	@RabbitListener(queues = "devin.report")
	public void readReportPDF(AMQPMessageWrapper amqpMessageWrapper) {

		try {
			System.out.println("Orçamento da Vila: " + amqpMessageWrapper.getVillageReportDTO().getBudget());
			System.out.println(
					"Recetia total dos cidadãos: " + amqpMessageWrapper.getVillageReportDTO().getTotalRevenue());
			System.out.println("Total receit da vila: "
					+ amqpMessageWrapper.getVillageReportDTO().getDifferenceRevenueAndExpense());
			System.out.println(
					"Cidadão com maior gasto: " + amqpMessageWrapper.getVillageReportDTO().getMostExpenseCitizen());
		} catch (Exception e) {
			villageReportPDFService.sendToDLX(amqpMessageWrapper);
		}

	}

	@RabbitListener(queues = "devin.report.dlx")
	public void readReportPDFDLX(AMQPMessageWrapper amqpMessageWrapper) {

		amqpMessageWrapper.addRetry();

		if (amqpMessageWrapper.getRetry() < 3) {
			System.out.println("Reenviando para fila");
			villageReportPDFService.sendToQueue(amqpMessageWrapper);
		} else {
			System.out.println("Máximos de 3 tentivas estourou.");
			emailService.send(amqpMessageWrapper.getEmail(), "Não foi possível gerar relatório solicitado.",
					"Falha na geração do pdf");
		}

	}

}
