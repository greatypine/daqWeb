var win;
var baseUrl=getRootPath();
function getRootPath(){
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	var localhostPaht=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return(localhostPaht+projectName);
}
/**
 * 
 * @param {Object} callback
 */
function checkUser(callback,personal_id,personal_name){
	win = new UserCheckWin(callback,personal_id,personal_name);
	win.show();
}
/**
 * 
 * @param {Object} callback 回调函数
 * @param {Object} personal_id 用户编码
 * @param {Object} personal_name 用户名称
 * 人员选择回调函数callback
 * 返回值json
 */
var UserCheckWin = function(callback,personal_id,personal_name){
	this.win = $("<div></div>");
		var _this = this;
		this.contentUrl = baseUrl + "/personal/selectUserWin.html?personal_id="+personal_id+"&personal_name="+encodeURI(personal_name)+"";
		var initWin = function(){
		_this.win.html('<iframe name="userCheckName" frameborder="0" width="100%" height="100%" src="'+_this.contentUrl+'"></iframe>');
		_this.win.dialog({
			bgiframe: true,
			title:"人员选择列表",
			autoOpen:false,
			width:_this.width,
			height:_this.height,
			buttons : {
				"确定": function(){
					window.frames["userCheckName"].doSubmit();
					_this.win.remove();
				},
				"取消":function(){
					_this.hide();
					_this.win.remove();
				}
			},
			modal:true
		});	
	}
	this.width = 700;
	this.height = 550;
	this.callBack = function(json){
		if (callback && typeof(callback) == 'function') {
                        callback(json);
                    }
	};
	
	this.show = function(){
		_this.win.dialog("open");
	}
	
	this.hide = function(){
		if(_this.onSubmitHandler){
			_this.onSubmitHandler();	
		}
		_this.win.dialog("close");
	}
	initWin();
}
/**
 * 
 * @param {Object} callback
 */
function checkStore(callback,storeid,storeName){
	win = new StoreCheckWin(callback,storeid,storeName);
	win.show();
}

/**
  * 
 * @param {Object} callback 回调函数
 * @param {Object} storeid 用户编码
 * @param {Object} storeName 用户名称
 * 健康屋门店callback
 * 返回值json 
 */
var StoreCheckWin = function(callback,storeid,storeName){
	this.win = $("<div></div>");
		var _this = this;
		this.contentUrl = baseUrl + "/healthArchives/selectRoomWin.html?storeNo="+storeid+"&storeName="+encodeURI(storeName)+"";
		var initWin = function(){
		_this.win.html('<iframe name="storeSelectName" frameborder="0" width="100%" height="100%" src="'+_this.contentUrl+'"></iframe>');
		_this.win.dialog({
			bgiframe: true,
			title:"门店选择列表",
			autoOpen:false,
			width:_this.width,
			height:_this.height,
			buttons : {
				"确定": function(){
					window.frames["storeSelectName"].doSubmit();
					_this.win.remove();
				},
				"取消":function(){
					_this.hide();
					_this.win.remove();
				}
			},
			modal:true
		});	
	}
	this.width = 700;
	this.height = 550;
	this.callBack = function(json){
		if (callback && typeof(callback) == 'function') {
                        callback(json);
                    }
	};
	
	this.show = function(){
		_this.win.dialog("open");
	}
	
	this.hide = function(){
		if(_this.onSubmitHandler){
			_this.onSubmitHandler();	
		}
		_this.win.dialog("close");
	}
	initWin();
}


/**
 * 
 * @param {Object} callback
 */
function checkEmployee(callback,id,name){
	win = new EmployeeCheckWin(callback,id,name);
	win.show();
}
/**
 * 
* @param {Object} callback 回调函数
* @param {Object} id 用户编码
* @param {Object} name 用户名称
* 健康屋门店callback
* 返回值json 
*/
var EmployeeCheckWin = function(callback,id,name){
	this.win = $("<div></div>");
		var _this = this;
		this.contentUrl = baseUrl + "/healthArchives/selectEmployeeInfoWin.html?id="+id+"&name="+encodeURI(name)+"";
		var initWin = function(){
		_this.win.html('<iframe name="employeeSelectName" frameborder="0" width="100%" height="100%" src="'+_this.contentUrl+'"></iframe>');
		_this.win.dialog({
			bgiframe: true,
			title:"员工选择列表",
			autoOpen:false,
			width:_this.width,
			height:_this.height,
			buttons : {
				"确定": function(){
					window.frames["employeeSelectName"].doSubmit();
					_this.win.remove();
				},
				"取消":function(){
					_this.hide();
					_this.win.remove();
				}
			},
			modal:true
		});	
	}
	this.width = 700;
	this.height = 550;
	this.callBack = function(json){
		if (callback && typeof(callback) == 'function') {
                       callback(json);
                   }
	};
	
	this.show = function(){
		_this.win.dialog("open");
	}
	
	this.hide = function(){
		if(_this.onSubmitHandler){
			_this.onSubmitHandler();	
		}
		_this.win.dialog("close");
	}
	initWin();
}
