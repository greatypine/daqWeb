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
function checkStore(callback,messageId){
	win = new StoreCheckWin(callback,messageId);
	win.show();
}

/**
  * 
 * @param {Object} callback 回调函数
 * @param {Object} messageId 活动id
 * @param {Object} storeName 用户名称
 * callback
 * 返回值json 
 */
var StoreCheckWin = function(callback,messageId){
	this.win = $("<div></div>");
		var _this = this;
		this.contentUrl = baseUrl + "/personal/personConditionWin.html?messageId="+messageId;
		var initWin = function(){
		_this.win.html('<iframe name="storeSelectName" frameborder="0" width="100%" height="100%" src="'+_this.contentUrl+'"></iframe>');
		_this.win.dialog({
			bgiframe: true,
			title:"按条件筛选推送用户",
			autoOpen:false,
			width:_this.width,
			height:_this.height,
			buttons : {
				/*"确定": function(){
					var activeId = $("#id").val();
					window.frames["storeSelectName"].doSubmit(activeId);
					window.location.href = "HealthLifeInfoRelatePerson.html?id="+ activeId;
					_this.hide();
					_this.win.remove();
				},*/
				"取消":function(){
					_this.hide();
					_this.win.remove();
				}
			},
			modal:true
		});	
	}
	this.width = 850;
	this.height = 600;
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

