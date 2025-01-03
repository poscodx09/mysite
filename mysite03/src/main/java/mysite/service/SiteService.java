package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.findOne();
	}
	
	public void updateSite(SiteVo siteVo) {
		System.out.println("####" + siteVo);
		siteRepository.update(siteVo);
	}
}
