package com.cnpc.pms.personal.manager;

import java.io.File;
import java.util.List;

import com.cnpc.pms.base.manager.IManager;

public interface ExternalHumanresourcesManager extends IManager{
	
	
	 public String saveHumanresourceExt(List<File> lst_import_excel) throws Exception ;
}
