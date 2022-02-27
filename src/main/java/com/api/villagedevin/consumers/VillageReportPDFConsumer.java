package com.api.villagedevin.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.api.villagedevin.model.transport.VillageReportDTO;

@Component
public class VillageReportPDFConsumer {
	
	@RabbitListener(queues = "devin.report")
	public void readReportPDF(VillageReportDTO villageReportDTO) {
		System.out.println("Orçamento da Vila: " + villageReportDTO.getBudget());
		System.out.println("Recetia total dos cidadãos: " + villageReportDTO.getTotalRevenue());
		System.out.println("Total receit da vila: " + villageReportDTO.getDifferenceRevenueAndExpense());
		System.out.println("Cidadão com maior gasto: " + villageReportDTO.getMostExpenseCitizen());
	}

}
