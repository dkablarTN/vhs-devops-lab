package truenorth.devops.lab.VHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import truenorth.devops.lab.VHS.model.VHS;
import truenorth.devops.lab.VHS.repository.VHSRepository;

@Service
public class VHSServiceImpl implements VHSService {

	private final VHSRepository iVHSRepository;
	
	public VHSServiceImpl(VHSRepository VHSRepository) {
		this.iVHSRepository = VHSRepository;
	}

	@Override
	public List<VHS> getAllVHS() {
		return iVHSRepository.getAllVHS();
	}

	@Override
	public void addVHS(VHS vhs) {
		iVHSRepository.save(vhs);
	}

	@Override
	public VHS getVHSById(long id) {
		return iVHSRepository.getVHSById(id);
	}

	@Override
	public List<VHS> getVHSByTitle(String title) {
		return iVHSRepository.getVHSByTitle(title);
	}

	@Override
	public long getNoOfVHS() {
		return iVHSRepository.count();
	}

}
