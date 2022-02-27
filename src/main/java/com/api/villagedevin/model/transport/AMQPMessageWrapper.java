package com.api.villagedevin.model.transport;

public class AMQPMessageWrapper {

	private Integer retry;
	private VillageReportDTO villageReportDTO;
	private String email;

	public AMQPMessageWrapper() {
		this.retry = 0;
	}

	public AMQPMessageWrapper(VillageReportDTO villageReportDTO) {
		this();
		this.villageReportDTO = villageReportDTO;
	}

	public void addRetry() {
		this.retry += 1;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public VillageReportDTO getVillageReportDTO() {
		return villageReportDTO;
	}

	public void setVillageReportDTO(VillageReportDTO villageReportDTO) {
		this.villageReportDTO = villageReportDTO;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
