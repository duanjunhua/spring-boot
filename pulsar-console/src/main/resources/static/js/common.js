let rsaPubKey,  encryptTool = new JSEncrypt()

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
 * 加载系统全局前端密码RSA加密public key
 */
function loadPublicKey(){
    $.get("/rsa/get-pubkey", res => {
        if(res.success) {
            rsaPubKey = res.data.replaceAll("\\\\n", "\n");
            return rsaPubKey
        } else {
            console.log("获取加密公钥失败！");
        }
    }).fail(()=>{
        console.log("请求公钥接口异常");
    })
}

/**
 * 对明文进行RSA加密
 * @param rawText 待加密明文
 * @param publicKey 公钥
 */
function encryptRsa (rawText, publicKey) {
    encryptTool.setPublicKey(publicKey || rsaPubKey);
    return encryptTool.encrypt(rawText);
}

/**
 * 加载全部租户信息
 */
function loadTenant(){
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
function loadCluster(lst){
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

// 执行加载全局RSA密钥
loadPublicKey()