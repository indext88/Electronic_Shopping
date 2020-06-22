/**
 * 主要处理goods.jsp(商品浏览界面)
 */
/**
 * 检查购买数量是否能够购买
 * @param id
 * @param value
 * @returns
 */
function checkBuyCount(id,buyCountStr){
	if(buyCountStr==null||buyCountStr==""){
		//判断购买数量是否为空
		$("div"+id).innerHTML="<font color='red'>请输入购买数量</font>";
		return;
	}
	if(isNaN(buyCountStr)){
		$("div"+id).innerHTML="<font color='red'>购买数量请输入数字</font>";
		return;
	}
	if(parseInt(buyCountStr)<=0){
		$("div"+id).innerHTML="<font color='red'>购买数量必须>0</font>";
	}
	//检查库存是否能购买
	var goodsCount=parseInt($("goodsCount"+id).value);//当前要购买这行商品的库存
	var buyCount=parseInt(buyCountStr);
	if(buyCount>goodsCount){
		$("div"+id).innerHTML="<font color='red'>购买数量>库存！请重新输入！</font>";
		$("chooseIds"+id).checked=false;//取消改行商品的复选框
		$("chooseIds"+id).disabled=true;//禁用当前商品复选框
	}else{
		$("div"+id).innerHTML="";
		$("chooseIds"+id).disabled=false;//当前商品复选框可用
	}
}

/**
 * 先检查至少购买了一件商品，再进行提交到购物车中
 */
function doSubmit(){
	
	//获得表单所有复选框
	var chooseIds=$$("chooseIds");
	
	//判断复选框是否被选中
	var result=false;
	for(var i=0;i<chooseIds.length;i++){
		result=result||chooseIds[i].checked;
	}
	
	//若一个都未选中
	if(!result){
		alert("请至少选择1中商品，才能进行购买！");
			
		return;
	}
	//提交表单数据
	document.forms[0].submit();
}