package run.dampharm.app.service;

import run.dampharm.app.model.AnalyisCounts;

public interface IAnalysisService {

//	public List<TopProduct> getTopSellingProducts(String userID);
	public AnalyisCounts getCounts(long createdBy);
}
