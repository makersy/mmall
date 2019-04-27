echo "============删除备份包============="
rm -rf /product/front/mmall_admin_fe/dist.bak

echo "============当前包备份=============" 
mv /product/front/mmall_admin_fe/dist /product/front/mmall_admin_fe/dist.bak

echo "=========移动此包到指定位置========="
mv ./dist/ /product/front/mmall_admin_fe/
