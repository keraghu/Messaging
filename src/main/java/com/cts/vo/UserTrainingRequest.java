package com.cts.vo;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "UserTrainingRequest")
public class UserTrainingRequest implements java.io.Serializable{
	
	 public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	private String reqId;
	 
	 /*	
	@Column(name = "req_status")
	 private String reqStatus="Pending";
	
	@Column(name = "approved_dt")
	 private Date approvedDate;
	
	@Column(name = "training_status")
	 private String trainingStatus="Not Completed";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_ID", nullable = false)
	private Training training;
	
	
	public User getUser() {
		return this.user;
	}
	
	
	public Training getTraining(){
		return this.training;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	
	public void setUser(User user) {
		this.user = user;
	}

	public void setTraining(Training training) {
		this.training = training;
	}*/
	 public String getReqId() {
			return reqId;
		}

}
