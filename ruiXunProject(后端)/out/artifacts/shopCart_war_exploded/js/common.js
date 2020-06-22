/**
 * 本js主要用于表头样式
 */
function $(id){
	return document.getElementById(id);
}

function $$(name){
	return document.getElementsByName(name);
}

function doLogout() {
	if(confirm("真的要离开？")){
		location.href="user.do?op=doLogout";
	}
}