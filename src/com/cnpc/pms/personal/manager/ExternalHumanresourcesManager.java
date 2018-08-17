package com.cnpc.pms.personal.manager;

import java.io.File;
import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.ExternalHumanresources;

public interface ExternalHumanresourcesManager extends IManager{
	
	
	 public String saveHumanresourceExt(List<File> lst_import_excel) throws Exception ;
	 
	 
	 public ExternalHumanresources queryExternalHumanresourcesById(Long id);
	 
	 
	 public ExternalHumanresources updateExternalHumanresources(ExternalHumanresources externalHumanresources);
}
