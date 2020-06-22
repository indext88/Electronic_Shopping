/**
 * 购物车的条件约束
 */

/**
 * 删除购物车中的商品
 * @param choose--单项删除/全部删除
 * @param url--提交的路径
 * @returns
 */
function doRemove(choose,url){
	if(confirm(choose=="removeAll"?"确认清空购物车？":"确认取消购物改商品？")){
		location.href=url;
	}
}