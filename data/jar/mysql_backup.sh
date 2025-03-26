# 设置mysql的登录用户名和密码(根据实际情况填写)
mysql_user="root"
mysql_password="@0eab4f19e8178d9d!"
mysql_host="127.0.0.1"
mysql_port="3306"
mysql_charset="utf8mb4"
# 备份文件存放地址(根据实际情况填写)
backup_location=/home/backup/mysql

backup_db="charer_jiufu"
 
# 是否删除过期数据
expire_backup_delete="ON"
expire_days=7
backup_time=`date +%Y%m%d%H%M`
backup_dir=$backup_location
welcome_msg="Welcome to use MySQL backup tools!"
# 备份指定数据库中数据(此处假设数据库是ruoyi )mysql容器里面找到mysqldump我的路径是/usr/bin/mysqldump
/usr/bin/mysqldump -h$mysql_host -P$mysql_port -u$mysql_user -p$mysql_password -B $backup_db > $backup_dir/mysql-$backup_time.sql
# 删除过期数据
if [ "$expire_backup_delete" == "ON" -a  "$backup_location" != "" ];then
        `find $backup_location/ -type f -mtime +$expire_days | xargs rm -rf`
        echo "Expired backup data delete complete!"
fi

