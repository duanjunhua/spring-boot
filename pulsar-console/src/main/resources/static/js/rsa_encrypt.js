let rsaPubKey,  encryptTool = new JSEncrypt()

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

// 执行加载全局RSA密钥
loadPublicKey()