package com.api.villagedevin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.villagedevin.model.transport.VillageReportDTO;
import com.api.villagedevin.service.VillageReportPDFService;

@RestController
@RequestMapping("pdf")
public class VillageReportRest {

	private VillageReportPDFService pdfService;

	public VillageReportRest(VillageReportPDFService pdfService) {
		this.pdfService = pdfService;
	}

	@PostMapping
	public VillageReportDTO GeneratePdf() throws Exception {
		VillageReportDTO reportDTO = pdfService.create();
		
		return reportDTO;

	}

}
