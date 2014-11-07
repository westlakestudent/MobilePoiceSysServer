var path;
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "qtip";

	var top = new Ext.BoxComponent({
				region : 'north',
				width : 500,
				height : 100,
				autoEl : {
					tag : 'img',
					src : path + '/images/login_topbar.png'
				}
			});

	var center = new Ext.form.FormPanel({
		region : 'center',
		id : 'loginPanel',
		width : 500,
		bodyStyle : 'padding:5px 5px 0 0;background-color:#dfe8f6',
		border : false,
		url : 'actions/login_execute.shtml',
		method : 'post',
		labelSeparator : ":",
		defaults : {
			width : 300,
			height : 30,
			xtype : 'textfield'
		},
		items : [{
			fieldLabel : "用户名",
			labelStyle : "margin-top:10px;font-size:20px;text-align:center;font-family:微软雅黑",
			style : "margin-top:10px",
			name : 'username',
			allowBlank : false,
			blankText : '用户名不能为空'
		}, {
			fieldLabel : "密码",
			labelStyle : "margin-top:10px;font-size:20px;text-align:center;font-family:微软雅黑",
			style : "margin-top:10px",
			name : 'password',
			allowBlank : false,
			inputType : 'password',
			blankText : '密码不能为空'
		}],
		buttons : [{
					text : '提 交',
					width : 80,
					height : 40,
					style : "font-size:20px",
					handler : function() {
						userlogin();
					}
				}, {
					text : '重置',
					handler : function() {
						center.form.reset();
					},
					width : 80,
					height : 40
				}],
		keys : {
			key : 13,
			fn : function() {
				userlogin();
			}
		},
		buttonAlign : 'center',
		listeners : {
			'render' : function() {
				this.findByType('textfield')[0].focus(true, true); // 第一个textfield获得焦点
			}
		}
	});

	var loginPanel = new Ext.Panel({
				renderTo : 'container',
				width : 500,
				height : 300,
				collapsible : true,
				bodyStyle : 'padding:5px 5px 0 0',
				layout : 'border',
				items : [top, center]
			});

});

var userlogin = function() {
	Ext.getCmp("loginPanel").getForm().submit({
		success : function(form, action) {
			if (action.result.success == 'true') {
				window.location = path + "/index.jsp";
			} else {
				Ext.getCmp("loginPanel").form.findField('password').getEl().dom.value = "";
				Ext.Msg.alert('错误', '用户名或密码错误');
			}

		},
		failure : function() {
			Ext.getCmp("loginPanel").form.findField('password').getEl().dom.value = "";
			Ext.Msg.alert('错误', '请重新登陆');
		}
	});
}


var setPath = function(p) {
	path = p;
}
