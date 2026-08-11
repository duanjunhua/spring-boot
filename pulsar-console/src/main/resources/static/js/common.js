let permissions = []

/**
 * 分页处理
 */
/**
 * 通用分页事件
 * @param {Object} vm vue实例this
 * @param {Function} loadFunc 加载列表方法
 */
const PaginationUtil = {
    // 每页条数改变
    handleSizeChange(vm, loadFunc) {
        return function (val) {
            vm.pageSize = val;
            vm.pageNum = 1;
            loadFunc.call(vm);
        }
    },
    // 页码改变
    handleCurrentChange(vm, loadFunc) {
        return function (val) {
            vm.pageNum = val;
            loadFunc.call(vm);
        }
    }
}

/**
 * 加载全部租户信息
 */
window.loadTenant = function(){
    return new Promise((resolve) => {
        $.get('/pulsar-tenant/all', res => {
            if (res.code === 200) {
                resolve(res.data);
            } else {
                resolve([]);
            }
        }).fail(() => resolve([]));
    })
}

/**
 * 加载全部集群
 */
window.loadCluster = function(){
    return new Promise((resolve) => {
        $.get('/pulsar-clus/all', res => {
            if (res.code === 200) {
                resolve(res.data);
            } else {
                resolve([]);
            }
        }).fail(() => resolve([]));
    })
}

/**
 * 加载当前已登陆用户权限集合
 */
window.loadCurrentUserPermissions = function(){
    $.get({
        url: '/oop-user/logged-in/permissions',
        async: false,
        success: (res) => {
            if(res.success) {
                permissions = res.data;
            } else {
                console.log("获取当前登录用户权限异常");
            }
        }
    }).fail(()=>{
        console.log("获取当前登录用户权限集合失败");
    });
}

/**
 * 判断是否拥有权限，如：hasPermission('user:add')
 */
window.hasPermission = function(perm){
    if (!perm) return true;

    if(!permissions) return false;

    return permissions.map(p => p.resourceCode).includes(perm);
}

// 执行当前用户角色
window.loadCurrentUserPermissions()