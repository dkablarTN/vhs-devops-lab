package truenorth.devops.lab.VHS.service;

import java.util.List;

import truenorth.devops.lab.VHS.model.VHS;

public interface VHSService {

	public List<VHS> getAllVHS();
	
	public long getNoOfVHS();
	
	public void addVHS(VHS vhs);
	
	public VHS getVHSById(long id);
	
	public List<VHS> getVHSByTitle(String title);
}