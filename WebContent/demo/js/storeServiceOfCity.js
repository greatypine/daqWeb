var importCityjs = function(cityName){
	if(cityName == "北京"){
		document.write('<script language="javascript" src="js/bei_jing.js"></script>');
	}else if(cityName == "上海"){
		document.write('<script language="javascript" src="js/shang_hai.js"></script>');
	}else if(cityName == "天津"){
		document.write('<script language="javascript" src="js/tian_jin.js"></script>');
	}else if(cityName == "长沙"){
		document.write('<script language="javascript" src="js/chang_sha.js"></script>');
	}else if(cityName == "广州"){
		document.write('<script language="javascript" src="js/guang_zhou.js"></script>');
	}else if(cityName == "贵阳"){
		document.write('<script language="javascript" src="js/gui_yang.js"></script>');
	}else if(cityName == "昆明"){
		document.write('<script language="javascript" src="js/kun_ming.js"></script>');
	}else if(cityName == "沈阳"){
		document.write('<script language="javascript" src="js/shen_yang.js"></script>');
	}else if(cityName == "深圳"){
		document.write('<script language="javascript" src="js/shen_zhen.js"></script>');
	}else if(cityName == "乌兰察布"){
		document.write('<script language="javascript" src="js/wulanchabu.js"></script>');
	}else if(cityName == "黔东南苗族侗族自治州"){
		document.write('<script language="javascript" src="js/qian_dong_nan.js"></script>');
	}else if(cityName == "武汉"){
		document.write('<script language="javascript" src="js/wu_han.js"></script>');
	}
}

var getcityservice = function(cityName){
	var serviceArea;
	if(cityName == "北京"){
		serviceArea = beijing_storeServiceDataOfCity();
	}else if(cityName == "上海"){
		serviceArea = shanghai_storeServiceDataOfCity();
	}else if(cityName == "天津"){
		serviceArea = tianjin_storeServiceDataOfCity();
	}else if(cityName == "长沙"){
		serviceArea = changsha_storeServiceDataOfCity();
	}else if(cityName == "广州"){
		serviceArea = guangzhou_storeServiceDataOfCity();
	}else if(cityName == "贵阳"){
		serviceArea = guiyang_storeServiceDataOfCity();
	}else if(cityName == "昆明"){
		serviceArea = kunming_storeServiceDataOfCity();
	}else if(cityName == "沈阳"){
		serviceArea = shenyang_storeServiceDataOfCity();
	}else if(cityName == "深圳"){
		serviceArea = shenzhen_storeServiceDataOfCity();
	}else if(cityName == "乌兰察布"){
		serviceArea = wulanchabu_storeServiceDataOfCity();
	}else if(cityName == "黔东南苗族侗族自治州"){
		serviceArea = qiandongnan_storeServiceDataOfCity();
	}else if(cityName == "武汉"){
		serviceArea = wuhan_storeServiceDataOfCity();
	}
	return serviceArea;
}

var getcitycoordinates = function(cityName){
	var coordinates;
	if(cityName == "北京"){
		coordinates = beijing_storeCoordinatesDataOfCity();
	}else if(cityName == "上海"){
		coordinates = shanghai_storeCoordinatesDataOfCity();
	}else if(cityName == "天津"){
		coordinates = tianjin_storeCoordinatesDataOfCity();
	}else if(cityName == "长沙"){
		coordinates = changsha_storeCoordinatesDataOfCity();
	}else if(cityName == "广州"){
		coordinates = guangzhou_storeCoordinatesDataOfCity();
	}else if(cityName == "贵阳"){
		coordinates = guiyang_storeCoordinatesDataOfCity();
	}else if(cityName == "昆明"){
		coordinates = kunming_storeCoordinatesDataOfCity();
	}else if(cityName == "沈阳"){
		coordinates = shenyang_storeCoordinatesDataOfCity();
	}else if(cityName == "深圳"){
		coordinates = shenzhen_storeCoordinatesDataOfCity();
	}else if(cityName == "乌兰察布"){
		coordinates = wulanchabu_storeCoordinatesDataOfCity();
	}else if(cityName == "黔东南苗族侗族自治州"){
		coordinates = qiandongnan_storeCoordinatesDataOfCity();
	}else if(cityName == "武汉"){
		coordinates = wuhan_storeCoordinatesDataOfCity();
	}
	return coordinates;
}

var pianquInfo = function(){
	var pianquxinxi;
	if(cityName == "北京"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "上海"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "天津"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "长沙"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "广州"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "贵阳"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "昆明"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "沈阳"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "深圳"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "乌兰察布"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "黔东南苗族侗族自治州"){
		pianquxinxi = qiandongnan_pianqu();
	}else if(cityName == "武汉"){
		pianquxinxi = qiandongnan_pianqu();
	}else{
		crm_alert(0,"门店服务范围不存在！");
	}
	return pianquxinxi;
}


